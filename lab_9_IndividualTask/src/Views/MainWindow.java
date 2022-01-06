package Views;

import javax.swing.*;

public class MainWindow extends JFrame{
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

    public MainWindow(){
        super("Multi loader");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainField);
        this.pack();
    }
}
