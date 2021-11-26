package views.GUI;

import views.GUI.Components.GraphComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class WindowView {
    private final String _title;
    private GraphComponent _graphComponent;

    public WindowView(String title, HashMap<Integer, Long> maps, GraphComponent.GraphComponentParams parameters){
        _title = title;
        _graphComponent = new GraphComponent(maps, parameters);
        _graphComponent.set_height(800);
        _graphComponent.set_width(800);
    }

    public void ShowWindowView(){
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame(_title);
            frame.add(_graphComponent);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500,500);
            frame.setVisible(true);
        });
    }

}
