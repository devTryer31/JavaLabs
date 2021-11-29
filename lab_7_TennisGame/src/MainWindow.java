import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements GameFieldComponent.Callback {

    private final GameFieldComponent _gameField;
    private final Container _innerContainer;

    private final JTextField _scoreLabel= new JTextField();

    MainWindow(String title, int w, int h) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(w, h);
        _innerContainer = getContentPane();

        _scoreLabel.setEditable(false);
        _scoreLabel.setFocusable(false);
        _innerContainer.add(_scoreLabel, BorderLayout.NORTH);

        _gameField = new GameFieldComponent(this.getWidth() - 20, this.getHeight() - 50);
        _gameField.CallBackFunc = this;
        _innerContainer.add(_gameField);

        addKeyListener(_gameField.getKeyListeners()[0]);

        setVisible(true);
    }

    @Override
    public void setScoreTitleCallback(int f, int s) {
        _scoreLabel.setText("Score: |"+ f + "-" + s + "|");
        _scoreLabel.repaint();
    }
}
