package Views;

import javax.swing.*;
import java.io.File;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Consumer;

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

    private final JFileChooser _file_chooser;

    private String _config_file_path;

    //start_f - delegate wats will be executed on start button clicked.
    public MainWindow(Consumer<Map.Entry<String, String>> start_f) {
        super("Multi loader");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainField);
        this.setResizable(false);

        _file_chooser = new JFileChooser();
        UIManager.put("FileChooser.readOnly", Boolean.TRUE);
        _file_chooser.setMultiSelectionEnabled(false);

        StartButton.addActionListener(e ->
                new Thread(() ->//Cause STA Threading fill lock and progressBars will not update while runtime.
                        start_f.accept(new AbstractMap.SimpleEntry<>(GetUrlInput(), GetPathInput()))).start());

        browseButton.addActionListener(e ->{
            _file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            var fc_res = _file_chooser.showDialog(this, "Choose directory to save the file");
            if(fc_res == JFileChooser.APPROVE_OPTION)
                FilePathInputTF.setText(_file_chooser.getSelectedFile().getAbsolutePath());
        });

        LoadConfigButton.addActionListener(e ->{
            _file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(_config_file_path != null && !_config_file_path.isBlank())
                _file_chooser.setCurrentDirectory(new File(_config_file_path));
            var fc_res = _file_chooser.showDialog(this, "Select configuration file");
            if(fc_res == JFileChooser.APPROVE_OPTION)
                _config_file_path = _file_chooser.getSelectedFile().getAbsolutePath();
        });

        this.pack();
    }

    public String GetConfigPath(){
        return _config_file_path;
    }

    public String GetUrlInput() {
        return URLInputTF.getText().trim();
    }

    public String GetPathInput() {
        return FilePathInputTF.getText().trim();
    }

    public void DisplayProgressById(int id, int progress) {
        JProgressBar bar;
        switch (id){
            case 0: bar = progressBar1; break;
            case 1: bar = progressBar2; break;
            case 2: bar = progressBar3; break;
            case 3: bar = progressBar4; break;
            case 4: bar = progressBar5; break;
            default: bar = progressBar6;
        }
        bar.setValue(progress);
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
