$(document).ready(function(){
    var addLoanFormId = "addLoanForm";
    initAddLoanForm(addLoanFormId);
    initPreCalResult(getOperationType());
});

function initPreCalResult(operationType){
    if (operationType == "show") {
        $("#preCalBtn").click();
    }
}

function initAddLoanForm(formId){
    // 配置字段显示
    changeRepaymentConfig($("#repaymentMode").val());
    $("#repaymentMode").change(function(){
        var selectedVal = $(this).val();
        changeRepaymentConfig(selectedVal);
    });
    // 表单提交按钮
    $("#addLoanBtn").click(function(){
        if (!verifyForm(formId)) {
            return false;
        }
        else {
            if (verifyAddLoanForm(formId)) {
                $("#" + formId).submit();
            }
            else {
                return false;
            }
        }
    });
    // 分段配置
    var onlyInterestMonths = $("input#onlyInterestMonths");
    var leftMonths = $("input#leftMonths");
    var term = $("input#term");
    onlyInterestMonths.blur(function(){
        leftMonths.val(term.val() - $(this).val());
    });
    leftMonths.blur(function(){
        onlyInterestMonths.val(term.val() - $(this).val());
    });
    
}

function changeRepaymentConfig(mode){
    if (mode == "FRAGMENT") {
        $("tr.fragment").show();
    }
    else {
        $("tr.fragment").hide();
    }
}

function preCal(){
    var url = "calLoan.json";
    var data = buildPreCalData();
    $.ajax({
        url: url,
        data: data,
        type: 'post',
        dataType: 'json',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        success: function(backData){
            var resultDiv = $("div#preCalResult");
            resultDiv.empty();
            var table = $("<table class='table table-bordered'> style='width:500px;'");
            resultDiv.append(table);
            var titleTr = $("<tr>");
            titleTr.append("<th>年份</th>");
            titleTr.append("<th>月份</th>");
            titleTr.append("<th>还款金额(单位: 元)</th>");
            titleTr.append("<th>还款利息(单位: 元)</th>");
            titleTr.append("<th>还款本金(单位: 元)</th>");
            table.append(titleTr);
            $.each(backData, function(index, data){
                var tr = $("<tr>");
                tr.append("<td>" + data.year + "</td>");
                tr.append("<td>" + data.month + "</td>");
                tr.append("<td>" + data.repaymentMoneyStr + "</td>");
                tr.append("<td>" + data.repaymentInterestStr + "</td>");
                tr.append("<td>" + data.repaymentCapitalStr + "</td>");
                table.append(tr);
            });
        }
    });
    return false;
}

function buildPreCalData(){
    var data = {};
    data.loaner = $("#loaner").val();
    data.executor = $("#executor").val();
    data.amount = $("#amount").val();
    data.term = $("#term").val();
    data.annualRate = $("#annualRate").val();
    data.repaymentMode = $("#repaymentMode").val();
    data.firstRepaymentDate = $("#firstRepaymentDate").val();
    data.repaymentMode = $("#repaymentMode").val();
    data.onlyInterestMonths = $("#onlyInterestMonths").val();
    data.leftMonths = $("#leftMonths").val();
	data.releaseDate = $("#releaseDate").val();
    return data;
}

function addLoan(formId){
    $("#" + formId).submit();
}

/**
 * 校验增加贷款表单
 * 1. 若还款方式为“分段”，则（利息段 + 等额本息段 = 贷款时间）必须成立
 *
 * @param {Object} formId
 */
function verifyAddLoanForm(formId){
    var repaymentMode = $("#repaymentMode").val();
    if (repaymentMode == "FRAGMENT") {
        var onlyInterestMonths = $("input#onlyInterestMonths");
        var leftMonths = $("input#leftMonths");
        var term = $("input#term");
        if (parseInt(term.val()) == parseInt(onlyInterestMonths.val()) + parseInt(leftMonths.val())) {
            return true;
        }
        else {
            return false;
        }
    }
    return true;
}

function downloanLoan(){
    var url = "downloadLoan.htm";
    buildDownloanCalData();
    $("#downloanForm").submit();
}

function buildDownloanCalData(){
    $("#d_loaner").val($("#loaner").val());
    $("#d_executor").val($("#executor").val());
    $("#d_amount").val($("#amount").val());
    $("#d_term").val($("#term").val());
    $("#d_annualRate").val($("#annualRate").val());
    $("#d_repaymentMode").val($("#repaymentMode").val());
    $("#d_firstRepaymentDate").val($("#firstRepaymentDate").val());
    $("#d_repaymentMode").val($("#repaymentMode").val());
    $("#d_onlyInterestMonths").val($("#onlyInterestMonths").val());
    $("#d_leftMonths").val($("#leftMonths").val());
    $("#d_releaseDate").val($("#releaseDate").val());
}
