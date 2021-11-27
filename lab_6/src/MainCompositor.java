import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MainCompositor extends JFrame {
    private Container _innerContainer;
    private GraphComponent _graphComponent;
    private JComboBox<Color> _colorsComboBox;
    private JCheckBox _logCheckBox;
    //    private JColorChooser _colorChooser;
    private JTextField _fileNameTextField;
    private JButton _addPlotButton;
    private JButton _plotButton;

    private GraphComponentParams _graphComponentParams;
    
    private int _curr_arrs_ids = 0;

    private final Color[] __colors = new Color[]{
            Color.BLACK,
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.MAGENTA,
    };

    MainCompositor(String title) {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        _innerContainer = getContentPane();

        var northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        var filePathLabel = new JTextField("File name:");
        filePathLabel.setEditable(false);
        filePathLabel.setBorder(null);
        northPanel.add(filePathLabel);
        _fileNameTextField = new JTextField(10);
        northPanel.add(_fileNameTextField);
        _plotButton = new JButton("Separated plotting");
        northPanel.add(_plotButton);
        _addPlotButton = new JButton("Add to graph");
        northPanel.add(_addPlotButton);


        _innerContainer.add(northPanel, BorderLayout.NORTH);

        var southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        _colorsComboBox = new JComboBox<>(__colors);
        southPanel.add(_colorsComboBox);
        _logCheckBox = new JCheckBox("Is logarithmic scale");
        southPanel.add(_logCheckBox);

        _innerContainer.add(southPanel, BorderLayout.SOUTH);

        _graphComponentParams = new GraphComponentParams();
        _graphComponentParams.x_axis_name = "";
        _graphComponentParams.y_axis_name = "";
        _graphComponentParams.graph_colors = __colors;
        _graphComponentParams.start_graph_color_idx = 0;
        _graphComponentParams.xs_values = new ArrayList<>();
        _graphComponentParams.xs_values.add(new ArrayList<>());
        _graphComponentParams.ys_values = new ArrayList<>();
        _graphComponentParams.ys_values.add(new ArrayList<>());
        
        _graphComponent = new GraphComponent(_graphComponentParams);
        _graphComponent.set_height(getHeight() - 100);
        _graphComponent.set_width(getWidth() - 100);
        _innerContainer.add(_graphComponent);
        buttonsConfigure();

        //_innerContainer.setBackground(Color.RED);
        setVisible(true);
    }

    private void buttonsConfigure() {

        _plotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetData();
                repaintGraphComponent();
            }
        });

        _addPlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintGraphComponent();
            }
        });
    }

    private void repaintGraphComponent(){
        handleData(_fileNameTextField.getText());
        _graphComponentParams.start_graph_color_idx = _colorsComboBox.getSelectedIndex();
        _graphComponentParams.logarithmicMode = _logCheckBox.isSelected();
        _graphComponent.repaint();
    }

    private void handleData(String file_name) {
        try {
            var scanner = new Scanner(new File(file_name));
            while (scanner.hasNextDouble()) {
                _graphComponentParams.xs_values.get(_curr_arrs_ids).add(scanner.nextDouble());
                if (!scanner.hasNextDouble()) {
                    JOptionPane.showMessageDialog(rootPane, "Data corrupted: x value without y value found.\n Reading stopped.");
                    break;
                }
                _graphComponentParams.ys_values.get(_curr_arrs_ids).add(scanner.nextDouble());
            }
            ++_curr_arrs_ids;
            _graphComponentParams.xs_values.add(new ArrayList<>());
            _graphComponentParams.ys_values.add(new ArrayList<>());
            scanner.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "File read error");
        }
    }

    //to safe references
    private void resetData() {
        _curr_arrs_ids = 0;
        var oldXs = _graphComponentParams.xs_values.get(0);
        var oldYs = _graphComponentParams.ys_values.get(0);
        oldXs.clear();
        oldYs.clear();

        _graphComponentParams.xs_values.clear();
        _graphComponentParams.ys_values.clear();

        _graphComponentParams.xs_values.add(oldXs);
        _graphComponentParams.ys_values.add(oldYs);
    }
}
