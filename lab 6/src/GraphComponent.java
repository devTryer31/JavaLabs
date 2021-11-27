import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;

public class GraphComponent extends JComponent {

    private final ArrayList<ArrayList<Double>> _xs_values;
    private final ArrayList<ArrayList<Double>> _ys_values;
    private final Color[] _graph_colors;
    private final int _start_graph_color_idx;
    private final String _x_axis_name;
    private final String _y_axis_name;
    private int _topYPos = 0;
    private float _graphPointsDiam;

    private int _inner_margin;
    private int _width;
    private int _height;

    public GraphComponent(ArrayList<ArrayList<Double>> xs_values, ArrayList<ArrayList<Double>> ys_values,
                          String x_axis_name, String y_axis_name, Color[] graph_colors, int start_graph_color_idx) {
        _xs_values = xs_values;
        _ys_values = ys_values;
        _graph_colors = graph_colors;
        _start_graph_color_idx = start_graph_color_idx;
        _x_axis_name = x_axis_name;
        _y_axis_name = y_axis_name;
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

    @Override
    public void paintComponent(Graphics gh) {
        Graphics2D field2d = (Graphics2D) gh;

        //making graphic title
        field2d.drawString("", _width / 2, _topYPos);
        _topYPos += 15;

        //making border(and axis)
        var innerRectWidth = _width - _inner_margin * 2;
        var innerRectHeight = _height - _inner_margin - _topYPos;
        Rectangle2D rect = new Rectangle2D.Double(_inner_margin, _topYPos, innerRectWidth + _graphPointsDiam, innerRectHeight + _graphPointsDiam);
        var axisLineY = _height - _inner_margin;

        field2d.draw(rect);

        //making the axis names
        field2d.drawString(_x_axis_name, _width - _inner_margin + 5, axisLineY);
        field2d.drawString(_y_axis_name, _inner_margin, _topYPos - 5);

        for (int o = 0; o < _xs_values.size(); ++o) {
            var _x_values = _xs_values.get(o);
            var _y_values = _ys_values.get(o);
            Color graphColor = _graph_colors[(_start_graph_color_idx + o) % _graph_colors.length];

            var xMax = _x_values.stream().max(Double::compare).get();
            var xMin = _x_values.stream().min(Double::compare).get();
            double XtoLenCoef = (double) innerRectWidth / (xMax - xMin);
            var scaledXValues = _x_values.stream().map(x -> _inner_margin + (x - xMin) * XtoLenCoef).toList();


            var yMax = _y_values.stream().max(Double::compare).get();
            var yMin = _y_values.stream().min(Double::compare).get();
            double YtoLenCoef = (double) innerRectHeight / (yMax - yMin);
            var scaledYValues = _y_values.stream().map(y -> axisLineY - _graphPointsDiam - (y - yMin) * YtoLenCoef).toList();

            //drawing first point
            Ellipse2D point = new Ellipse2D.Double(scaledXValues.get(0), scaledYValues.get(0), _graphPointsDiam, _graphPointsDiam);
            field2d.draw(point);
            field2d.drawString(_x_values.get(0).toString(), scaledXValues.get(0).floatValue(), (float) axisLineY + _graphPointsDiam * 3);
            field2d.drawString(_y_values.get(0).toString(), (float) _inner_margin - _graphPointsDiam * 3, scaledYValues.get(0).floatValue());

            for (int i = 1; i < scaledXValues.size(); ++i) {
                int prev_i = i - 1;
                double prev_x = scaledXValues.get(prev_i), prev_y = scaledYValues.get(prev_i);
                double curr_x = scaledXValues.get(i), curr_y = scaledYValues.get(i);
                Float pointRad = _graphPointsDiam / 2;

                field2d.setColor(graphColor);
                //drawing n-th point and line between i and i-1 points
                field2d.draw(new Ellipse2D.Double(curr_x, curr_y + _graphPointsDiam, _graphPointsDiam, _graphPointsDiam));

                var baseStoke = field2d.getStroke();
                field2d.setStroke(new BasicStroke(2));
                field2d.draw(new Line2D.Double(prev_x + pointRad, prev_y + pointRad + _graphPointsDiam,
                        curr_x + pointRad, curr_y + pointRad + _graphPointsDiam));
                field2d.setStroke(baseStoke);
                field2d.setColor(Color.BLACK);

                //drawing axis stokes with values
                float stoke_x = (float) curr_x + pointRad;
                field2d.draw(new Line2D.Double(stoke_x, axisLineY + _graphPointsDiam, stoke_x, axisLineY - _graphPointsDiam));
                field2d.drawString(_x_values.get(i).toString(), stoke_x, axisLineY + _graphPointsDiam * 3);


                float stoke_y = (float) curr_y + pointRad;
                field2d.draw(new Line2D.Float(_inner_margin - _graphPointsDiam, stoke_y, _inner_margin + _graphPointsDiam, stoke_y));
                field2d.drawString(_y_values.get(i).toString(), _inner_margin - _graphPointsDiam * 3, stoke_y + 10);
            }
        }

    }

}