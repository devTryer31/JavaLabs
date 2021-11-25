package views.GUI;

import views.GUI.Components.GraphComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class WindowView {
    private GraphComponent _graphComponent;

    public WindowView(HashMap<Integer, Long> xMaps, HashMap<Integer, Long> yMaps, GraphComponent.GraphComponentParams parameters){
        _graphComponent = new GraphComponent(xMaps, yMaps, parameters);
        _graphComponent.set_height(400);
        _graphComponent.set_width(400);
    }

    public void ShowWindowView(){
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("DrawTest");
            frame.add(_graphComponent);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500,500);
            frame.setVisible(true);
        });
    }

}
