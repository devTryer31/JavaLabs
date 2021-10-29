package Services;

import Services.Interfaces.ILogger;
import views.MainView;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class Logger implements ILogger {

    private final OutputStream _stream;

    public Logger(OutputStream stream) {
        _stream = stream;
        LogInfo("\n\nStart new logging session!");
    }

    public static String LoggerPrefix = "logger -> ";
    public static String ErrorPrefix = "Error: ";
    public static String InfoPrefix = "Info: ";

    @Override
    public void LogError(String msg) {
        String s = _GetTimePrefix() + ' ' + LoggerPrefix + ErrorPrefix + msg;
        try {
            _stream.write(s.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            MainView.PrintError("Logger down: " + e.getMessage());
        }
    }

    @Override
    public void LogInfo(String msg) {
        String s = _GetTimePrefix() + ' ' + LoggerPrefix + InfoPrefix + msg;
        try {
            _stream.write(s.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            MainView.PrintError("Logger down: " + e.getMessage());
        }
    }

    private String _GetTimePrefix() {
        return "\ntime: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
