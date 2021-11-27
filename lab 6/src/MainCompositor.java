import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainCompositor extends JFrame {
    private GraphComponent _graphComponent;
    private JComboBox<Color> _colorsComboBox;
    private JCheckBox _logCheckBox;
    //    private JColorChooser _colorChooser;
    private JTextField _fileNameTextField;
    private JButton _addPlotButton;
    private JButton _plotButton;
    
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
        var innerContainer = getContentPane();

        var northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        var filePathLabel = new JTextField("File name:");
        filePathLabel.setEditable(false);
        filePathLabel.setBorder(null);
        northPanel.add(filePathLabel);
        _fileNameTextField = new JTextField(10);
        northPanel.add(_fileNameTextField);
        _addPlotButton = new JButton("Separated plotting");
        northPanel.add(_addPlotButton);
        _plotButton = new JButton("Add to graph");
        northPanel.add(_plotButton);

        innerContainer.add(northPanel, BorderLayout.NORTH);

        var southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

//        var colorPanels = new JPanel[__colors.length];
//        for(int i = 0; i < __colors.length; ++i){
//            colorPanels[i] = new JPanel();
//            colorPanels[i].setBackground(__colors[i]);
//        }

        _colorsComboBox = new JComboBox<>(__colors);
        southPanel.add(_colorsComboBox);
        _logCheckBox = new JCheckBox("Is logarithmic scale");
        southPanel.add(_logCheckBox);

        innerContainer.add(southPanel, BorderLayout.SOUTH);

        var xs = new ArrayList<Double>();
        xs.add(12D);
        xs.add(23.3D);
        xs.add(543D);

        var ys = new ArrayList<Double>();
        ys.add(26D);
        ys.add(159D);
        ys.add(1509.6D);

        var xs2 = new ArrayList<Double>();
        xs2.add(122D);
        xs2.add(232.3D);
        xs2.add(5423D);

        var ys2 = new ArrayList<Double>();
        ys2.add(226D);
        ys2.add(2159D);
        ys2.add(1509.6D);

        ArrayList<ArrayList<Double>> xsArr = new ArrayList<>();
        xsArr.add(xs);
        xsArr.add(xs2);

        ArrayList<ArrayList<Double>> ysArr = new ArrayList<>();
        ysArr.add(ys);
        ysArr.add(ys2);

        _graphComponent = new GraphComponent(xsArr,ysArr,"Xs", "Ys", __colors, _colorsComboBox.getSelectedIndex());
        _graphComponent.set_height(getHeight()-100);
        _graphComponent.set_width(getWidth()-100);
        innerContainer.add(_graphComponent);


        //innerContainer.setBackground(Color.RED);
        setVisible(true);
    }

//    @Override
//    public void itemStateChanged(ItemEvent e) {
//
//    }
}
