

function DrawPie(data,id) {

    var option = ECharts.ChartOptionTemplates.Pie(data);
    var container = document.getElementById(id);
    opt = ECharts.ChartConfig(container, option);
    ECharts.Charts.RenderChart(opt);

}
 

function DrawBar(data, id) {

    var option = ECharts.ChartOptionTemplates.Bars(data);
    var container = document.getElementById(id);
    opt = ECharts.ChartConfig(container, option);
    ECharts.Charts.RenderChart(opt);

}
function DrawLines(data, id, seriesTemp) {

    var option = ECharts.ChartOptionTemplates.Lines(data, seriesTemp, false);

    var container = document.getElementById(id);

    opt = ECharts.ChartConfig(container, option);

    ECharts.Charts.RenderChart(opt);

}