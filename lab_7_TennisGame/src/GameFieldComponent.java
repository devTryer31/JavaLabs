import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Random;

public class GameFieldComponent extends JComponent {

    private float _width;
    private float _height;
    private float _inner_padding = 10;
    private float _rects_width = 10;
    private float _rects_height = 30;
    private float _ball_r = 5;
    private double _ball_x;
    private double _ball_y;
    private boolean _ball_dx;
    private boolean _ball_dy;
    private float _ball_speed = 3;

    private final Rectangle2D _leftRect;
    private final Rectangle2D _rightRect;
    private final Ellipse2D _ball;

    private final Thread _leftRectUpdater;
    private final Thread _rightRectUpdater;
    private final Thread _ballUpdater;

    private final HashMap<Character, Boolean> _keysPool = new HashMap<>();

    GameFieldComponent(float w, float h) {
        _width = w;
        _height = h;
        _leftRect = new Rectangle2D.Float(_inner_padding, h / 2 - _rects_height / 2, _rects_width, _rects_height);
        _rightRect = new Rectangle2D.Float(w - 2 * _inner_padding, h / 2 - _rects_height / 2, _rects_width, _rects_height);
        _ball = new Ellipse2D.Float(w / 2, h / 2, _ball_r * 2, _ball_r * 2);

        _keysPool.put('o', false);//move right rect up
        _keysPool.put('l', false);//move right rect down
        _keysPool.put('w', false);//move left rect up
        _keysPool.put('s', false);//move left rect down

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                _keysPool.put(e.getKeyChar(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                _keysPool.put(e.getKeyChar(), false);
            }
        });


        //Threading

        int delay_ms = 50;
        _rightRectUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    moveRectByKey('o', 'l', _rightRect);
                    try {
                        Thread.sleep(delay_ms);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        _rightRectUpdater.start();
        _leftRectUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    moveRectByKey('w', 's', _leftRect);
                    try {
                        Thread.sleep(delay_ms);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

        _leftRectUpdater.start();
        _ballUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                ballRestart();
                while (true) {
                    moveBall();
                    try {
                        Thread.sleep(delay_ms);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        _ballUpdater.start();

        ////
    }

    private void moveRectByKey(char w, char s, Rectangle2D rect) {
        if (_keysPool.get(w))
            if (rect.getY() - 5 > 0) {
                rect.setRect(rect.getX(), rect.getY() - 5, _rects_width, _rects_height);
                repaint();
            }
        if (_keysPool.get(s))
            if (rect.getY() + 5 < _height - _rects_height) {
                rect.setRect(rect.getX(), rect.getY() + 5, _rects_width, _rects_height);
                repaint();
            }
    }

    private void moveBall() {
        ballAndBoundCollisionExec(_ball_x, _ball_y);
        ballAndRectCollisionExec(_ball_x, _ball_y, _leftRect);
        ballAndRectCollisionExec(_ball_x, _ball_y, _rightRect);

        double ball_diam = _ball_r * 2;
        double next_x = (float) (_ball_x + (_ball_dx ? _ball_speed : -_ball_speed));
        double next_y = (float) (_ball_y + (_ball_dy ? _ball_speed : -_ball_speed));


        _ball_x = next_x;
        _ball_y = next_y;
        _ball.setFrame(_ball_x - _ball_r, _ball_y - _ball_r, ball_diam, ball_diam);
        repaint();
    }

    private void ballAndRectCollisionExec(double x, double y, Rectangle2D rect) {
        if (y >= rect.getY() && y <= rect.getY() + _rects_height
                && x >= rect.getX() && x <= rect.getX() + _rects_width) {
            _ball_dx = !_ball_dx;
        }
    }

    private void ballAndBoundCollisionExec(double x, double y) {
        if (y <= _inner_padding || y >= _height - _inner_padding) {
            _ball_dy = !_ball_dy;
            return;
        }
        if (x < _inner_padding || x > _inner_padding + _width) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            ballRestart();
        }

    }

    private void ballRestart() {
        _ball_x = _width / 2 + _inner_padding;
        _ball_y = _height / 2 + _inner_padding;
        Random rd = new Random();
        _ball_dx = rd.nextBoolean();
        _ball_dy = rd.nextBoolean();
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paint(g);
        Graphics2D field2g = (Graphics2D) g;
        field2g.draw(new Rectangle2D.Float(0, 0, _width, _height));
        field2g.draw(_rightRect);
        field2g.draw(_leftRect);
        field2g.draw(_ball);
    }
}
