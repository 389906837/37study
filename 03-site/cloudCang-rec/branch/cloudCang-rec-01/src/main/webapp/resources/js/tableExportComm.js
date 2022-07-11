/**
 * Created by zhouhong on 2018/4/2.
 */
//数据table 导出
var exportFileName = "";
var fileType = "";
var exportRange = "";
function addExportBtn(fileName) {
    exportFileName = fileName;
    $("#gridTable").jqGrid('navButtonAdd', '#gridPager', {
        position: 'last', title: '导出表格数据', buttonicon: "glyphicon-download-alt", caption: '', onClickButton: exportExcel
    });
}

function addExportBtn1(fileName) {
    exportFileName = fileName;
    $("#gridTable").jqGrid('navButtonAdd', '#gridPager', {
        position: 'last',
        title: '导出表格数据',
        buttonicon: "glyphicon-download-alt",
        caption: '',
        onClickButton: exportExcel1
    });
}

function addExportBtnByUrl(url) {
    $("#gridTable").jqGrid('navButtonAdd', '#gridPager', {
        position: 'last',
        title: '导出表格数据',
        buttonicon: "glyphicon-download-alt",
        caption: '',
        onClickButton: function () {
            var params = getFormValue();
            window.open(url + "?t=" + new Date() + params);
        }
    });
}
function addExportBtnByUrlz(url, sid) {
   /* $.ajax({
        type: "post",
        url: url,
        data: {"checkboxId": sid},
        dataType: "json",
        success: function (data) {
            dealwithSuccess(data);
        }
    });*/
    window.open(url+"?checkboxId="+sid);
}

function exportExcel() {
    showLayer("导出配置", ctx + '/common/exportConfig');
}


function exportExcel1() {
    showLayer("导出配置", ctx + '/common/exportConfigs');
}

/**
 * 确认导出 文件
 * @param types 文件类型
 * @param ranges 数据范围
 */
function confirmExportFile(types, ranges) {
    fileType = types;
    exportRange = ranges;
    if (exportRange == 2) {//全部数据
        currRownum = $("#gridTable").jqGrid('getGridParam', 'rowNum');
        var total = $("#gridTable").jqGrid('getGridParam', 'records'); //获取查询得到的总记录数量
        isExport = true;
        reloadGrid(true, total);
    } else {
        exportFileCallback();
    }
}


function exportFileCallback() {
    $(document.body).append("<table id='exportTable'></table>");//创建一个临时表格

    var idStr = $("#gridTable").attr('id');
    var dd = $("#gbox_" + idStr + ' .ui-jqgrid-htable thead').clone();
    $('#exportTable').append(dd);
    var tbody = $('#' + idStr).find('tbody').clone();//合并表头和表数据
    $('#exportTable').append(tbody);

    //去掉不需要的数据
    $('#exportTable').find('.jqgfirstrow').remove();
    $('#exportTable').find('tr.ui-search-toolbar').remove();
    $('#exportTable tr').find('td:last').remove();
    $('#exportTable thead').find('th:last').remove();
    $('#exportTable tr').find('td:eq(1)').remove();
    $('#exportTable thead').find('th:eq(1)').remove();
    $('#exportTable tr').find('td:eq(1)').remove();
    $('#exportTable thead').find('th:eq(1)').remove();

    $('#exportTable').tableExport({
        type: fileType,
        escape: 'false',
        fileName: exportFileName
    });
    $("#exportTable").remove();

    if (exportRange == 2) {
        reloadGrid(true, currRownum);
        currRownum = rownum;
        isExport = false;
    }

}


/**
 * 确认导出 文件
 * @param types 文件类型
 * @param ranges 数据范围
 */
function confirmExportFile1(types, ranges) {
    fileType = types;
    exportRange = ranges;
    if (exportRange == 2) {//全部数据
        currRownum = $("#gridTable").jqGrid('getGridParam', 'rowNum');
        var total = $("#gridTable").jqGrid('getGridParam', 'records'); //获取查询得到的总记录数量
        isExport = true;
        reloadGrid(true, total);
    } else {
        exportFileCallback1();
    }
}


function exportFileCallback1() {
    $(document.body).append("<table id='exportTable'></table>");//创建一个临时表格

    var idStr = $("#gridTable").attr('id');
    var dd = $("#gbox_" + idStr + ' .ui-jqgrid-htable thead').clone();
    $('#exportTable').append(dd);
    var tbody = $('#' + idStr).find('tbody').clone();//合并表头和表数据
    $('#exportTable').append(tbody);

    //去掉不需要的数据
    $('#exportTable').find('.jqgfirstrow').remove();
    $('#exportTable').find('tr.ui-search-toolbar').remove();
    $('#exportTable tr').find('td:eq(1)').remove();
    $('#exportTable thead').find('th:eq(1)').remove();
    // $('#exportTable tr').find('td:eq(1)').remove();
    // $('#exportTable thead').find('th:eq(1)').remove();

    $('#exportTable').tableExport({
        type: fileType,
        escape: 'false',
        fileName: exportFileName
    });
    $("#exportTable").remove();

    if (exportRange == 2) {
        reloadGrid(true, currRownum);
        currRownum = rownum;
        isExport = false;
    }

}