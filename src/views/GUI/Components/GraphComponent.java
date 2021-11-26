package views.GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
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
        Rectangle2D rect = new Rectangle2D.Double(_inner_margin, _topYPos, innerRectWidth + _graphPointsDiam, innerRectHeight);
        var axisLineY = _height - _inner_margin;

        field2d.draw(rect);

        //making the axis names
        field2d.drawString(_parameters.xAxisName, _width - _inner_margin + 5, axisLineY);
        field2d.drawString(_parameters.yAxisName, _inner_margin, _topYPos - 5);

        var xValues = _xMaps.values().stream().toList();
        var xMax = xValues.stream().max(Long::compare).get();
        var xMin = xValues.stream().min(Long::compare).get();
        var XtoLenCoef = innerRectWidth / (xMax - xMin);
        var scaledXValues = xValues.stream().map(x -> _inner_margin + (x - xMin) * XtoLenCoef).toList();


        var yValues = _yMaps.values().stream().toList();
        var yMax = yValues.stream().max(Long::compare).get();
        var yMin = yValues.stream().min(Long::compare).get();
        var YtoLenCoef = innerRectHeight / (yMax - yMin);
        var scaledYValues = yValues.stream().map(y -> axisLineY - (long) _graphPointsDiam - (y - yMin) * YtoLenCoef).toList();

        //drawing first point
        java.awt.geom.Ellipse2D point = new Ellipse2D.Float(scaledXValues.get(0), scaledYValues.get(0), _graphPointsDiam, _graphPointsDiam);
        field2d.draw(point);
        field2d.drawString(xValues.get(0).toString(), scaledXValues.get(0), axisLineY + _graphPointsDiam*3);
        field2d.drawString(yValues.get(0).toString(), _inner_margin - _graphPointsDiam*3, scaledYValues.get(0));


        for (int i = 1; i < scaledXValues.size(); ++i) {
            int prev_i = i - 1;
            long prev_x = scaledXValues.get(prev_i), prev_y = scaledYValues.get(prev_i);
            long curr_x = scaledXValues.get(i), curr_y = scaledYValues.get(i);
            Float pointRad = _graphPointsDiam / 2;

            //drawing n-th point and line between i and i-1 points
            field2d.draw(new Ellipse2D.Float(curr_x, curr_y, _graphPointsDiam, _graphPointsDiam));
            field2d.draw(new Line2D.Float(prev_x + pointRad, prev_y + pointRad,
                    curr_x + pointRad, curr_y + pointRad));

            //drawing axis tokes with values
            Float stoke_x = curr_x + pointRad;
            field2d.draw(new Line2D.Float(stoke_x, axisLineY + _graphPointsDiam, stoke_x, axisLineY - _graphPointsDiam));
            field2d.drawString(xValues.get(i).toString(), stoke_x, axisLineY + _graphPointsDiam*3);


            Float stoke_y = curr_y + pointRad;
            field2d.draw(new Line2D.Float(_inner_margin - _graphPointsDiam, stoke_y, _inner_margin + _graphPointsDiam, stoke_y));
            field2d.drawString(yValues.get(i).toString(), _inner_margin - _graphPointsDiam*3, stoke_y);
        }



    }

}
