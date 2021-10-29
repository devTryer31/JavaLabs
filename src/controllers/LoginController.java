package controllers;

import Services.Interfaces.ILogger;
import views.LoginView;

import java.util.Properties;

public class LoginController {

    private final Properties _properties;
    private ILogger _logger;

    public LoginController(Properties properties, ILogger logger) {
        _properties = properties;
        _logger = logger;
    }

    public LoginController(Properties properties) {
        _properties = properties;
    }

    public String Execute() {
        String login = _properties.getProperty("LOGIN"), group = _properties.getProperty("GROUP");
        LoginView.DisplayGreeting(login, group);
        if (_logger != null)
            _logger.LogInfo("Try to login in account " + login + '(' + group + "):");
        int cnt = 3;
        while (cnt-- != 0) {
            var password = LoginView.GetPassword();
            if (_logger != null)
                _logger.LogInfo("Attempt #" + (3 - cnt) + " pass: " + password);
            if (password.equals(_properties.getProperty("PASSWORD")))
                break;
        }
        if (cnt == -1) {
            if (_logger != null)
                _logger.LogInfo("Unsuccessful login. Program termination");
            LoginView.PrintLoginError("Access denied. Termination...");
            System.exit(-3);
        }
        if (_logger != null)
            _logger.LogInfo("Successful login");
        return _properties.getProperty("GROUP");
    }
}
