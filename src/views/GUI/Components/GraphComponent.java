package views.GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class GraphComponent extends JComponent {

    private final HashMap<Integer, Long> _xMaps;
    private final HashMap<Integer, Long> _yMaps;
    private final GraphComponentParams _parameters;
    private int _topYPos = 0;
    private float _graphPointsDiam;

    private int _inner_margin;
    private int _width;
    private int _height;

    public GraphComponent(HashMap<Integer, Long> xMaps, HashMap<Integer, Long> yMaps, GraphComponentParams parameters) {
        this._xMaps = xMaps;
        this._yMaps = yMaps;
        _parameters = parameters;
        _inner_margin = 25;
        _graphPointsDiam = 5;
    }

    public int get_width() {
        return _width;
    }

    public void set_width(int _width) {
        this._width = _width;
    }

    public int get_height() {
        return _height;
    }

    public void set_height(int _height) {
        this._height = _height;
    }

    public static class GraphComponentParams {
        public String title;
        public String xAxisName;
        public String yAxisName;
        public Color graphColor;
    }

    @Override
    public void paintComponent(Graphics gh) {
        Graphics2D field2d = (Graphics2D) gh;

        field2d.draw(new Rectangle2D.Double(0, 0, _width, _height));

        //making graphic title
        Font FTitle = new Font("Times new roman", Font.PLAIN, 14);
        field2d.drawString(_parameters.title, _width / 2, _topYPos);
        _topYPos += 15;

        //making border(and axis)
        var innerRectWidth = _width - _inner_margin * 2;
        var innerRectHeight = _height - _inner_margin - _topYPos;
        Rectangle2D rect = new Rectangle2D.Double(_inner_margin, _topYPos, innerRectWidth, innerRectHeight);
        var axisLineY = _height - _inner_margin;

        field2d.draw(rect);

        //making the axis names
        field2d.drawString(_parameters.xAxisName, _width - _inner_margin + 5, axisLineY);
        field2d.drawString(_parameters.yAxisName, _inner_margin, _topYPos - 5);

        var xValues = _xMaps.values().stream().map(x->x+_inner_margin).toList();
        var xMax = xValues.stream().max(Long::compare).get();
        var xMin = xValues.stream().min(Long::compare).get();
        var XtoLenCoef = innerRectWidth / (xMax - xMin);

        var yValues = _yMaps.values().stream().map(y->y + _width-_inner_margin-(long)_graphPointsDiam-1).toList();
        var yMax = yValues.stream().max(Long::compare).get();
        var yMin = yValues.stream().min(Long::compare).get();
        var YtoLenCoef = innerRectWidth / (yMax - yMin);

        java.awt.geom.Ellipse2D firstPoint = new Ellipse2D.Float(xValues.get(0), yValues.get(0), _graphPointsDiam, _graphPointsDiam);

        field2d.draw(firstPoint);

        for (int i = 1; i < xValues.size(); ++i) {

        }

        //field2d.drawString(_parameters.title, _inner_margin+_width/2, _topYPos);

    }

}
