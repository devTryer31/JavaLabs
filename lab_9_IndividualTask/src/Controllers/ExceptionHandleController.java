package Controllers;

import Controllers.Interfaces.IExceptionHandleController;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

/**
 * This controller handle all apps exceptions.
 */
public class ExceptionHandleController implements IExceptionHandleController {
    private final String _ErrorTitle = "Error!";


    private final JFrame _window;

    /**
     * @param window Window that will be the root for output exceptions views.
     */
    public ExceptionHandleController(JFrame window) {
        _window = window;
    }

    /**
     * Handling exceptions by showing dialog error windows. This algorithm compare input exception by instanceof word.
     * @param ex Exception handled by the implemented algorithm.
     */
    @Override
    public void HandleException(Exception ex) {
        if (ex instanceof IllegalArgumentException)
            JOptionPane.showMessageDialog(_window, "Invalid input error: " + ex.getMessage(), _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        else if (ex instanceof URISyntaxException)
            JOptionPane.showMessageDialog(_window, "Invalid URL input.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        else if (ex instanceof IOException)
            JOptionPane.showMessageDialog(_window, "Invalid URL or path.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        else if (ex instanceof InterruptedException)
            JOptionPane.showMessageDialog(_window, "One of the operation was interrupted.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        else if (ex instanceof RuntimeException)
            JOptionPane.showMessageDialog(_window, "Partial download interrupted. Error: "
                    + ex.getMessage(), _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        else if (ex instanceof NoSuchAlgorithmException)
            JOptionPane.showMessageDialog(_window, "Hash name algorithm fail.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
    }

}
