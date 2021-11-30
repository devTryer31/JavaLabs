import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private final String _title = "email@sender";
    private int _width;
    private int _height;

    private final JButton _sendButton = new JButton("send");
    private final JTextArea _msgText = new JTextArea();
    private final JTextField _msgSubject = new JTextField();
    private final JTextField _emailFromField = new JTextField();
    private final JPasswordField _emailFromPasswordField = new JPasswordField();
    private final JTextField _emailToField = new JTextField();

    MainWindow(int width, int height) {
        _width = width;
        _height = height;
        setTitle(_title);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //does not work :(
        Dimension txtFieldsSize = new Dimension(200, 20);
        _emailFromField.setPreferredSize(txtFieldsSize);
        _emailFromPasswordField.setPreferredSize(txtFieldsSize);
        _emailToField.setPreferredSize(txtFieldsSize);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());
        rightPanel.setPreferredSize(txtFieldsSize);
        rightPanel.add(new JLabel("From:"));
        rightPanel.add(_emailFromField);
        rightPanel.add(new JLabel("Password:"));
        rightPanel.add(_emailFromPasswordField);
        rightPanel.add(new JLabel("To:"));
        rightPanel.add(_emailToField);
        _sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MailSender.sendMessage(_emailFromField.getText(),
                            String.valueOf(_emailFromPasswordField.getPassword()),
                            _emailToField.getText(),
                            _msgSubject.getText(),
                            _msgText.getText());
                } catch (MessagingException ex) {
                    JOptionPane.showMessageDialog(rootPane,
                            "Message sending problem!\nCheck for entered values.",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        rightPanel.add(_sendButton);

        this.add(rightPanel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(new JLabel("Subject:"));
        centerPanel.add(_msgSubject);
        centerPanel.add(new JLabel("Message:"));
        _msgText.setLineWrap(true);
        centerPanel.add(_msgText);

        this.add(centerPanel);

        setVisible(true);
    }

}
