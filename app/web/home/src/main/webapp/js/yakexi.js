/**
 * Yakexi
 * 
 * 基于Arale、jQuery、FancyBox封装的一套适合支付宝中小型后台管理系统的UI交互解决方案。
 * 
 * @author weidong.shen
 * @author hui.shih
 */
// 隐藏等待信息
var ajaxPostBack = function(){
	$("#ajaxInProgress").remove();
};

var removeWarn = function(){
	$(".highlight").removeClass("highlight")
	$("span.warn").remove()
	$("form.boxForm").each(function() {this.reset()})
}
// 显示等待信息
var ajaxInProgress = function(){
	var w = Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px"; //获取宽
	var h = Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px"; //获取高
	//定义一个透明背景层
	var gb = $("<div id='ajaxInProgress'></div>")
	       .css({width:w,height:h})
	$("body").append(gb);
};

// 显示提示条
var showMessage = function(message, normal){
	if(normal == false){
		$("body").append("<div id='result' class='result'><span class='redbox'></span></div>");
	} else{
		$("body").append("<div id='result' class='result'><span class='bluebox'></span></div>");
	}
	$("#result span").html(message);
	$("#result span").fadeIn(600).delay(4000).fadeOut(600);
};

// 绑定通用事件
var commonEvents = function(){
	// 开启各种弹窗特效
	$("a.boxLink").fancybox({
				'hideOnOverlayClick' : false,
				'width'          : 1240,
				'height'         : 800,
				'transitionIn'	 : 'elastic',
				'transitionOut'	 : 'elastic',
				'scrolling'      : 'no',
				'onClosed'       : removeWarn
			});	
};

// Ajax调用成功的统一处理函数
var commonHandleSuccess = function(o){
	ajaxPostBack();	
	// 如果不存在响应
    if(o.responseText === undefined){
	 	return;
	}
    
    var o = L.JSON.parse(o.responseText);
    //如果后台处理发生错误（异常抛至框架）
    if(o.messageCode){
    	showMessage("后台运行错误："+o.messageCode, false);
    	return;
    }
	// 处理业务结果
	var result = o.result;
	handleResult(result.isSuccess, result.resultMsg, result.detailInfo, result.targetUrl);
};

// 处理业务结果
var handleResult = function(isSuccess, resultMsg, detailInfo, targetUrl){
	// 如果业务执行成功,关闭fancybox，给出提示条，局部更新页面
	if(isSuccess=="true" || isSuccess==true){
		var successMsg = resultMsg;
		if(detailInfo && detailInfo.length>0){
			successMsg = successMsg + " : " + detailInfo;
		}
		if(targetUrl!==undefined && targetUrl!=""){
			//如果是IE浏览器，直接重新载入页面。
			if(!-[1,]){//传说中最短的判断IE浏览器的方法
				window.location.href = targetUrl;
				return;
			}else{
				successMsg += "   将于3秒之后跳转到指定页面...";
				setTimeout(function(){window.location.href=targetUrl;},3000);
			}			
		}
		showMessage(successMsg, true);// 提示信息
		jQuery.fancybox.close();
		//页面内容刷新
		reload();
	}else{// 业务出错，给出错误提示
		showMessage(resultMsg+" : "+detailInfo, false);
	}
}

//  Ajax调用失败的通用处理函数，给出提示条
var commonHandleFailure = function(o){ 
	ajaxPostBack();	
	showMessage("Ajax调用失败", false);
};

// 通用回调函数
var commonCallback = {
    success: commonHandleSuccess,
    failure: commonHandleFailure,
    argument:{ foo:"foo", bar:"bar" }
}

// 发起AJAX的POST请求
var doAjaxPost = function(form,jsonUrl,callbackFunction){
	AP.ajax.setForm(form);
	ajaxInProgress();
    var request = AP.ajax.asyncRequest('POST', jsonUrl, callbackFunction);
};

// 发起AJAX的GET请求
var doAjaxGet = function(url, callbackFunction){
	ajaxInProgress();
	var request = AP.ajax.asyncRequest('GET', url, callbackFunction);
};
//局部刷新页面
var refreshPartly = function(sourceUrl,targetId){
	//如果是IE浏览器，放弃局部刷新，直接重新载入页面。
	if(!-[1,]){//传说中最短的判断IE浏览器的方法
		window.location.href = sourceUrl;
	}else{
		jQuery.ajax({ 
			url     : sourceUrl, 
			cache   : false, 
			success : function(html){
				$("#"+targetId).html($(html).find("#"+targetId).html());
				load();// 重新绑定事件
			}
		});
	}	
};

var posIntRegex = /^[0-9]*[1-9][0-9]*$/;//匹配正整数的正则表达式
var decimalRegex = /^[0-9]+\.?[0-9]*$/;//匹配正数的正则表达式

//表单校验/////////////////////////////////////////////////////
function verifyForm(formId){
	var flag = true;
	$("#"+ formId + " [alt]").each(function(){
		var val = $(this).val().trim();
		var errMsg = "";
		try {
			var validation = $.parseJSON($(this).attr("alt"));
			if(validation.notBlank && validation.notBlank == true && val.length == 0){
				errMsg += " 不能为空";
			}
			else {//如果填写为空就不再做其他校验
				if(validation.posInt){
					if(!posIntRegex.test(val)){
						errMsg += " 必须是正整数";
					}
				}
				if(validation.decimal){
					if(!decimalRegex.test(val)){
						errMsg += " 必须是正数";
					}
				}
				if(validation.maxValue && validation.maxValue>0 && parseInt(val) > validation.maxValue){
					errMsg += " 数值不能大于"+validation.maxValue;
				}
				if(validation.minLen && validation.minLen > 0 && val.length < validation.minLen){
					errMsg += " 长度不能小于"+validation.minLen;
				}
				if(validation.maxLen && validation.maxLen > 0 && val.length > validation.maxLen){
					errMsg += " 长度不能大于"+validation.maxLen;
				}
				if(validation.regex && validation.regex.trim().length > 0){				
					var pat = new RegExp(validation.regex.trim());
					if(!pat.test(val)){
						errMsg += " 必须符合正则" + validation.regex;
					}
				}
			}
			if(errMsg != ""){
				flag = false;
				$(this).addClass("highlight");
				$(this).parent().find(".warn").remove();
				$(this).after("<span class='warn'>" + errMsg + "</span>");
//				jQuery.fancybox.resize();
				$(this).focus(function(){
					$(this).removeClass("highlight");
					$(this).parent().find(".warn").remove();
				});	
			}
		} catch(e) {}
	});
	return flag;
};