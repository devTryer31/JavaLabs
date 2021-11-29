import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {

    private final GameFieldComponent _gameField;
    private final Container _innerContainer;

    MainWindow(String title, int w, int h){
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(w,h);
        _innerContainer = getContentPane();
        _gameField = new GameFieldComponent(this.getWidth()-50, this.getHeight()-50);

        _innerContainer.add(_gameField);

        setVisible(true);
    }



    @Override
    protected void processKeyEvent(KeyEvent e) {
//        System.out.println("Pressed" + e.getKeyChar());
        _gameField.processKeyEvent(e);
    }
}
