import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class GameFieldComponent extends JComponent {

    private float _weight;
    private float _height;
    private float _inner_padding = 10;
    private float _rects_width = 10;
    private float _rects_height = 30;

    private final Rectangle2D _leftRect;
    private final Rectangle2D _rightRect;

    private final Thread _leftRectUpdater;
    private final Thread _rightRectUpdater;

    private final HashMap<Character, Boolean> _keysPool = new HashMap<>();

    GameFieldComponent(float w, float h) {
        _weight = w;
        _height = h;
        _leftRect = new Rectangle2D.Float(_inner_padding, h / 2 - _rects_height / 2, _rects_width, _rects_height);
        _rightRect = new Rectangle2D.Float(w - 2 * _inner_padding, h / 2 - _rects_height / 2, _rects_width, _rects_height);
        _keysPool.put('o', false);
        _keysPool.put('l', false);
        _keysPool.put('w', false);
        _keysPool.put('s', false);

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

        int delay_ms = 50;

        _rightRectUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (_keysPool.get('o'))
                        if (_rightRect.getY() - 5 > 0) {
                            _rightRect.setRect(_rightRect.getX(), _rightRect.getY() - 5, _rects_width, _rects_height);
                            repaint();
                        }
                    if (_keysPool.get('l'))
                        if (_rightRect.getY() + 5 < _height - _rects_height) {
                            _rightRect.setRect(_rightRect.getX(), _rightRect.getY() + 5, _rects_width, _rects_height);
                            repaint();
                        }
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
                    if (_keysPool.get('w'))
                        if (_leftRect.getY() - 5 > 0) {
                            _leftRect.setRect(_leftRect.getX(), _leftRect.getY() - 5, _rects_width, _rects_height);
                            repaint();
                        }
                    if (_keysPool.get('s'))
                        if (_leftRect.getY() + 5 < _height - _rects_height) {
                            _leftRect.setRect(_leftRect.getX(), _leftRect.getY() + 5, _rects_width, _rects_height);
                            repaint();
                        }
                    try {
                        Thread.sleep(delay_ms);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        _leftRectUpdater.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paint(g);
        Graphics2D field2g = (Graphics2D) g;
        field2g.draw(new Rectangle2D.Float(0, 0, _weight, _height));
        field2g.draw(_rightRect);
        field2g.draw(_leftRect);
    }
}
