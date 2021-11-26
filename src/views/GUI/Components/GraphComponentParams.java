package views.GUI.Components;

import java.awt.*;

public class GraphComponentParams {

    public String title;
    public String xAxisName;
    public String yAxisName;
    public Color graphColor;

    public GraphComponentParams Clone(){
        var p = new GraphComponentParams();
        p.title = title;
        p.graphColor = graphColor;
        p.xAxisName = xAxisName;
        p.yAxisName = yAxisName;
        return p;
    }
}
