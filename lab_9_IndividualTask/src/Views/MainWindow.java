package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class MainWindow extends JFrame {
    private JPanel MainField;
    private JMenuItem LoadConfigButton;
    private JTextField URLInputTF;
    private JTextField FilePathInputTF;
    private JButton browseButton;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JProgressBar progressBar3;
    private JProgressBar progressBar4;
    private JProgressBar progressBar5;
    private JProgressBar progressBar6;
    private JButton StartButton;

    public MainWindow() {
        super("Multi loader");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainField);

        this.pack();
    }

    public String GetUrlInput() {
        return URLInputTF.getText();
    }

    public String GetPathInput() {
        return FilePathInputTF.getText();
    }

    public void DisplayProgressById(int id, int progress){
        JProgressBar bar = null;
        switch (id){
            case 0: bar = progressBar1; break;
            case 1: bar = progressBar2; break;
            case 2: bar = progressBar3; break;
            case 3: bar = progressBar4; break;
            case 4: bar = progressBar5; break;
            case 5: bar = progressBar6; break;
        }
        bar.setValue(progress);
        bar.repaint();
    }

    public void SetStartButtonClickHandler(Consumer<Map.Entry<String, String>> f) {
        StartButton.addActionListener((e) -> f.accept(new AbstractMap.SimpleEntry<>(GetUrlInput(), GetPathInput())));
    }
}
