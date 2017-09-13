/** Powered By 北京甜园科技, Since 2016 - 2020 */
package com.zsTrade.web.monitor.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.AbstractView;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.LabelLine;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.SelectedMode;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.data.ScatterData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.EMap;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Heatmap;
import com.github.abel533.echarts.series.MapLocation;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.series.gauge.Detail;
import com.github.abel533.echarts.style.LineStyle;
import com.github.abel533.echarts.style.TextStyle;
import com.zsTrade.common.base.PigResult;
/**
 * 
 * @author 沈专 2016-12-7 19:18:02
 * @Email: [email]
 * @version [version]
 *	购买汇总管理
 */
@Controller
@RequestMapping("chart")
public class ChartController extends AbstractView {

	
	@RequestMapping
	public String toBuyTotal(Model model){
		return "modules/cart";
	}
	
	
	@RequestMapping(value="chart")
	public @ResponseBody PigResult chart(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin","*");
		   Option option = this.selectRemoveCauses();   
		    return new PigResult().build(0, "sucess", option);
	}
	@RequestMapping(value="chart1")
	public @ResponseBody PigResult chart1(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin","*");
		   Option option = this.selectRemoveCauses1();   
		    return new PigResult().build(0, "sucess", option);
	}
	@RequestMapping(value="chart2")
	public @ResponseBody PigResult chart2(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin","*");
		   Option option = this.selectRemoveCauses2();   
		    return new PigResult().build(0, "sucess", option);
	}
	@RequestMapping(value="chart3")
	public @ResponseBody PigResult chart3(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin","*");
		   Option option = this.selectRemoveCauses3();   
		    return new PigResult().build(0, "sucess", option);
	}
	@RequestMapping(value="chart4")
	public @ResponseBody PigResult chart4(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin","*");
		   Option option = this.selectRemoveCauses4();   
		    return new PigResult().build(0, "sucess", option);
	}
	@RequestMapping(value="chart5")
	public @ResponseBody PigResult chart5(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin","*");
		   Option option = this.selectRemoveCauses5();   
		    return new PigResult().build(0, "sucess", option);
	}
	public Option selectRemoveCauses()  {  
	 
	    //创建Option  
	    Option option = new Option();  
	    option.title().text("某地区蒸发量和降水量").subtext("纯属虚构");
        option.tooltip().trigger(Trigger.axis);
        option.legend("蒸发量", "降水量");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"));
        option.yAxis(new ValueAxis());

        Bar bar = new Bar("蒸发量");
        bar.data(2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3);
        bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        Bar bar2 = new Bar("降水量");
        List<Double> list = Arrays.asList(2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3);
        bar2.setData(list);
        bar2.markPoint().data(new PointData("年最高", 182.2).xAxis(7).yAxis(183).symbolSize(18), new PointData("年最低", 2.3).xAxis(11).yAxis(3));
        bar2.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        option.series(bar, bar2);
	    return option;  
	}
	public Option selectRemoveCauses1()  {  
		 
	    //创建Option  
	    Option option = new Option();  
	    option.color("rgba(255, 69, 0, 0.5)",
                "rgba(255, 150, 0, 0.5)",
                "rgba(255, 200, 0, 0.5)",
                "rgba(155, 200, 50, 0.5)",
                "rgba(55, 200, 100, 0.5)");
        option.title().text("漏斗图").subtext("纯属虚构");
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}%");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.legend("展现", "点击", "访问", "咨询", "订单");
        option.calculable(true);

        Funnel funnel = new Funnel("预期");
        funnel.x("10%").y(60).width("80%");
        funnel.itemStyle().normal().label(new Label().formatter("{b}预期")).
                labelLine(new LabelLine().show(false));
        funnel.itemStyle().emphasis().label(new Label().formatter("{b}预期 : {c}%").position(Position.inside)).
                labelLine(new LabelLine().show(true));

        funnel.data(new Data().value(60).name("访问"),
                new Data().value(40).name("咨询"),
                new Data().value(20).name("订单"),
                new Data().value(80).name("点击"),
                new Data().value(100).name("展现")
        );

        Funnel funnel2 = new Funnel("实际");
        funnel2.x("10%").y(60).width("80%").maxSize("80%");
        funnel2.itemStyle().normal().label(new Label().formatter("{c}%").position(Position.inside).textStyle(new TextStyle().color("#fff"))).
                borderColor("#fff").borderWidth(2);
        funnel2.itemStyle().emphasis().label(new Label().formatter("{b}实际 : {c}%").position(Position.inside)).
                labelLine(new LabelLine().show(true));

        funnel2.data(new Data().value(30).name("访问"),
                new Data().value(10).name("咨询"),
                new Data().value(5).name("订单"),
                new Data().value(50).name("点击"),
                new Data().value(80).name("展现")
        );

        option.series(funnel,funnel2);
	    return option;  
	}

	public Option selectRemoveCauses2()  {  
		 
	    //创建Option  
	    Option option = new Option();  
	    option.tooltip().formatter("{a} <br/>{b} : {c}%");
        option.toolbox().show(true).feature(Tool.mark, Tool.restore, Tool.saveAsImage);
        option.series(new Gauge("业务指标").detail(new Detail().formatter("{value}%")).data(new Data("完成率", 75)));
	    return option;  
	}
	
	public Option selectRemoveCauses3()  {  
		 
	    //创建Option  
	    Option option = new Option();  
	    option.tooltip().position(Position.top);
        option.animation(false);

        CategoryAxis hoursC = new CategoryAxis();
        hoursC.data("12a", "1a", "2a", "3a", "4a", "5a", "6a",
                "7a", "8a", "9a", "10a", "11a",
                "12p", "1p", "2p", "3p", "4p", "5p",
                "6p", "7p", "8p", "9p", "10p", "11p");

        CategoryAxis daysC = new CategoryAxis();
        daysC.data("Saturday", "Friday", "Thursday",
                "Wednesday", "Tuesday", "Monday", "Sunday");

        option.xAxis(hoursC).yAxis(daysC);
        option.grid().height("50%").y("10%");
        option.visualMapNew().min(1).max(10).calculable(true).orient(Orient.horizontal).left(X.center).bottom("15%");

        Object[] data = new Object[]{new Integer[]{0, 0, 5}, new Integer[]{0, 1, 1}, new Integer[]{0, 2, 0}, new Integer[]{0, 3, 0}, new Integer[]{0, 4, 0},
                new Integer[]{0, 5, 0}, new Integer[]{0, 6, 0}, new Integer[]{0, 7, 0}, new Integer[]{0, 8, 0}, new Integer[]{0, 9, 0},
                new Integer[]{0, 10, 0}, new Integer[]{0, 11, 2}, new Integer[]{0, 12, 4}, new Integer[]{0, 13, 1}, new Integer[]{0, 14, 1},
                new Integer[]{0, 15, 3}, new Integer[]{0, 16, 4}, new Integer[]{0, 17, 6}, new Integer[]{0, 18, 4}, new Integer[]{0, 19, 4},
                new Integer[]{0, 20, 3}, new Integer[]{0, 21, 3}, new Integer[]{0, 22, 2}, new Integer[]{0, 23, 5}, new Integer[]{1, 0, 7},
                new Integer[]{1, 1, 0}, new Integer[]{1, 2, 0}, new Integer[]{1, 3, 0}, new Integer[]{1, 4, 0}, new Integer[]{1, 5, 0},
                new Integer[]{1, 6, 0}, new Integer[]{1, 7, 0}, new Integer[]{1, 8, 0}, new Integer[]{1, 9, 0}, new Integer[]{1, 10, 5},
                new Integer[]{1, 11, 2}, new Integer[]{1, 12, 2}, new Integer[]{1, 13, 6}, new Integer[]{1, 14, 9}, new Integer[]{1, 15, 11},
                new Integer[]{1, 16, 6}, new Integer[]{1, 17, 7}, new Integer[]{1, 18, 8}, new Integer[]{1, 19, 12}, new Integer[]{1, 20, 5},
                new Integer[]{1, 21, 5}, new Integer[]{1, 22, 7}, new Integer[]{1, 23, 2}, new Integer[]{2, 0, 1}, new Integer[]{2, 1, 1},
                new Integer[]{2, 2, 0}, new Integer[]{2, 3, 0}, new Integer[]{2, 4, 0}, new Integer[]{2, 5, 0}, new Integer[]{2, 6, 0},
                new Integer[]{2, 7, 0}, new Integer[]{2, 8, 0}, new Integer[]{2, 9, 0}, new Integer[]{2, 10, 3}, new Integer[]{2, 11, 2},
                new Integer[]{2, 12, 1}, new Integer[]{2, 13, 9}, new Integer[]{2, 14, 8}, new Integer[]{2, 15, 10}, new Integer[]{2, 16, 6},
                new Integer[]{2, 17, 5}, new Integer[]{2, 18, 5}, new Integer[]{2, 19, 5}, new Integer[]{2, 20, 7}, new Integer[]{2, 21, 4},
                new Integer[]{2, 22, 2}, new Integer[]{2, 23, 4}, new Integer[]{3, 0, 7}, new Integer[]{3, 1, 3}, new Integer[]{3, 2, 0},
                new Integer[]{3, 3, 0}, new Integer[]{3, 4, 0}, new Integer[]{3, 5, 0}, new Integer[]{3, 6, 0}, new Integer[]{3, 7, 0},
                new Integer[]{3, 8, 1}, new Integer[]{3, 9, 0}, new Integer[]{3, 10, 5}, new Integer[]{3, 11, 4}, new Integer[]{3, 12, 7},
                new Integer[]{3, 13, 14}, new Integer[]{3, 14, 13}, new Integer[]{3, 15, 12}, new Integer[]{3, 16, 9}, new Integer[]{3, 17, 5},
                new Integer[]{3, 18, 5}, new Integer[]{3, 19, 10}, new Integer[]{3, 20, 6}, new Integer[]{3, 21, 4}, new Integer[]{3, 22, 4},
                new Integer[]{3, 23, 1}, new Integer[]{4, 0, 1}, new Integer[]{4, 1, 3}, new Integer[]{4, 2, 0}, new Integer[]{4, 3, 0},
                new Integer[]{4, 4, 0}, new Integer[]{4, 5, 1}, new Integer[]{4, 6, 0}, new Integer[]{4, 7, 0}, new Integer[]{4, 8, 0},
                new Integer[]{4, 9, 2}, new Integer[]{4, 10, 4}, new Integer[]{4, 11, 4}, new Integer[]{4, 12, 2}, new Integer[]{4, 13, 4},
                new Integer[]{4, 14, 4}, new Integer[]{4, 15, 14}, new Integer[]{4, 16, 12}, new Integer[]{4, 17, 1}, new Integer[]{4, 18, 8},
                new Integer[]{4, 19, 5}, new Integer[]{4, 20, 3}, new Integer[]{4, 21, 7}, new Integer[]{4, 22, 3}, new Integer[]{4, 23, 0},
                new Integer[]{5, 0, 2}, new Integer[]{5, 1, 1}, new Integer[]{5, 2, 0}, new Integer[]{5, 3, 3}, new Integer[]{5, 4, 0},
                new Integer[]{5, 5, 0}, new Integer[]{5, 6, 0}, new Integer[]{5, 7, 0}, new Integer[]{5, 8, 2}, new Integer[]{5, 9, 0},
                new Integer[]{5, 10, 4}, new Integer[]{5, 11, 1}, new Integer[]{5, 12, 5}, new Integer[]{5, 13, 10}, new Integer[]{5, 14, 5},
                new Integer[]{5, 15, 7}, new Integer[]{5, 16, 11}, new Integer[]{5, 17, 6}, new Integer[]{5, 18, 0}, new Integer[]{5, 19, 5},
                new Integer[]{5, 20, 3}, new Integer[]{5, 21, 4}, new Integer[]{5, 22, 2}, new Integer[]{5, 23, 0}, new Integer[]{6, 0, 1},
                new Integer[]{6, 1, 0}, new Integer[]{6, 2, 0}, new Integer[]{6, 3, 0}, new Integer[]{6, 4, 0}, new Integer[]{6, 5, 0},
                new Integer[]{6, 6, 0}, new Integer[]{6, 7, 0}, new Integer[]{6, 8, 0}, new Integer[]{6, 9, 0}, new Integer[]{6, 10, 1},
                new Integer[]{6, 11, 0}, new Integer[]{6, 12, 2}, new Integer[]{6, 13, 1}, new Integer[]{6, 14, 3}, new Integer[]{6, 15, 4},
                new Integer[]{6, 16, 0}, new Integer[]{6, 17, 0}, new Integer[]{6, 18, 0}, new Integer[]{6, 19, 0}, new Integer[]{6, 20, 1},
                new Integer[]{6, 21, 2}, new Integer[]{6, 22, 2}, new Integer[]{6, 23, 6}};

        Object[] datas = new Object[data.length];
        for (int i = 0; i < data.length; i++) {
            Integer[] is = (Integer[]) data[i];
            datas[i] = new Integer[]{is[1], is[0], is[2]};
        }

        Heatmap heatmap = new Heatmap("Punch Card");
        heatmap.data(datas);
        heatmap.label().normal().show(true);
        heatmap.itemStyle().emphasis().shadowBlur(10).shadowColor("rgba(0, 0, 0, 0.5)");

        option.series(heatmap);
	    return option;  
	}
	public Option selectRemoveCauses4()  {  
		 
	    //创建Option  
	    Option option = new Option();  
	    EMap map = new EMap("Map");
        map.mapLocation(new MapLocation(X.left, Y.top, 500));
        map.selectedMode(SelectedMode.multiple);
        map.itemStyle().normal().borderWidth(2)
                .borderColor("lightgreen").color("orange")
                .label().show(true);

        map.itemStyle().emphasis()
                .borderWidth(2).borderColor("#fff").color("#32cd32")
                .label().show(true)
                .textStyle().color("#fff");
        Data data = new Data("广东");
        data.value(Math.round(Math.random() * 1000));
        data.itemStyle().normal().color("#32cd32")
                .label().show(true).textStyle().color("#fff").fontSize(15);
        data.itemStyle().emphasis().borderColor("yellow").color("#cd5c5c")
                .label().show(false).textStyle().color("blue");

        map.data(data);
        map.markPoint().itemStyle().normal().color("skyblue");
        map.markPoint().data(new Data("天津", 350), new Data("上海", 103));

        map.geoCoord("上海", "121.4648", "31.2891").geoCoord("天津", "117.4219", "39.4189");

        option.series(map);
	    return option;  
	}
	public Option selectRemoveCauses5()  {  
		 
	    //创建Option  
	    Option option = new Option();  
	    option.tooltip(new Tooltip()
        .trigger(Trigger.axis)
        .showDelay(0)
        .axisPointer(new AxisPointer().type(PointerType.cross)
                .lineStyle(new LineStyle()
                        .type(LineType.dashed).width(1))));
option.legend("scatter1", "scatter2");
option.toolbox().show(true).feature(Tool.mark, Tool.dataZoom, Tool.dataView, Tool.restore, Tool.saveAsImage);
ValueAxis valueAxis = new ValueAxis().power(1).splitNumber(4).scale(true);
option.xAxis(valueAxis);
option.yAxis(valueAxis);
//注：这里的结果是一种圆形一种方形，是因为默认不设置形状时，会循环形状数组
option.series(
        new Scatter("scatter1").symbolSize("function (value){" +
                "                return Math.round(value[2] / 5);" +
                "            }").data(randomDataArray())
        , new Scatter("scatter2").symbolSize("function (value){" +
                "                return Math.round(value[2] / 5);" +
                "            }").data(randomDataArray()));
	    return option;  
	}
	 private ScatterData[] randomDataArray() {
	        ScatterData[] scatters = new ScatterData[100];
	        for (int i = 0; i < scatters.length; i++) {
	            scatters[i] = new ScatterData(random(), random(), Math.abs(random()));
	        }
	        return scatters;
	    }

	    private int random() {
	        int i = (int) Math.round(Math.random() * 100);
	        return (i * (i % 2 == 0 ? 1 : -1));
	    }
	@Override
	protected void renderMergedOutputModel(Map<String, Object> arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}  
}
