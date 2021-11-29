import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class GameFieldComponent extends JComponent {

    private float _weight;
    private float _height;
    private float _inner_padding = 10;
    private float _rects_width = 10;
    private float _rects_height = 30;

    private final Rectangle2D _leftRect;
    private final Rectangle2D _rightRect;

    GameFieldComponent(float w, float h) {
        _weight = w;
        _height = h;
        _leftRect = new Rectangle2D.Float(_inner_padding, h / 2, _rects_width, _rects_height);
        _rightRect = new Rectangle2D.Float(w - 2*_inner_padding, h / 2, _rects_width, _rects_height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paint(g);
        Graphics2D field2g = (Graphics2D) g;
        field2g.draw(new Rectangle2D.Float(0, 0, _weight, _height));
        field2g.draw(_rightRect);
        field2g.draw(_leftRect);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {

        switch (e.getKeyChar()) {
            case 'o':
                if (_rightRect.getY() - 5 > 0)
                    _rightRect.setRect(_rightRect.getX(), _rightRect.getY() - 5, _rects_width, _rects_height);
                break;
            case 'l':
                if (_rightRect.getY() + 5 < _height - _rects_height)
                    _rightRect.setRect(_rightRect.getX(), _rightRect.getY() + 5, _rects_width, _rects_height);
                break;

            case 'w':
                if (_leftRect.getY() - 5 > 0)
                    _leftRect.setRect(_leftRect.getX(), _leftRect.getY() - 5, _rects_width, _rects_height);
                break;
            case 's':
                if (_leftRect.getY() + 5 < _height - _rects_height)
                    _leftRect.setRect(_leftRect.getX(), _leftRect.getY() + 5, _rects_width, _rects_height);
                break;

            default:
                break;
        }

//        if(e.getKeyChar() == 'l'){
//            _rightRect.setRect(_weight-_inner_padding, _rightRect.getY()+5, _rects_width, _rects_height);
//            paintComponent(this.getGraphics());
//        }
        repaint();
//        paintComponent(this.getGraphics());
    }
}
