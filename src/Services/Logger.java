package Services;

import Services.Interfaces.ILogger;
import Services.Interfaces.ISpeedTestLogger;
import views.MainView;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class Logger implements ILogger, ISpeedTestLogger {

    private final OutputStream _stream;

    private long _startTimeNS = -1;

    public Logger(OutputStream stream) {
        _stream = stream;
        LogInfo("\n\nStart new logging session!");
    }

    public static String LoggerPrefix = "logger -> ";
    public static String ErrorPrefix = "Error: ";
    public static String InfoPrefix = "Info: ";
    public static String StartSpeedTestPrefix = "Starting speed test: ";
    public static String EndSpeedTestPrefix = "End speed test: ";

    @Override
    public void LogError(String msg) {
        String s = _GetTimePrefix() + ' ' + LoggerPrefix + ErrorPrefix + msg;
        _LogToStream(s);
    }

    @Override
    public void LogInfo(String msg) {
        String s = _GetTimePrefix() + ' ' + LoggerPrefix + InfoPrefix + msg;
        _LogToStream(s);
    }

    private void _LogToStream(String message){
        try {
            _stream.write(message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            MainView.PrintError("Logger down: " + e.getMessage());
        }
    }

    private String _GetTimePrefix() {
        return "\ntime: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @Override
    public void LogStartSpeedTest(String header){
        String s = "\n" + StartSpeedTestPrefix + " " + header;
        _LogToStream(s);
        if(_startTimeNS != -1) {
            //throw new OperationNotSupportedException("Can not run new speed test while the previous one is not completed.");
            LogError("Can not run new speed test while the previous one is not completed.");
        }
        _startTimeNS = System.nanoTime();
    }

    @Override
    public void LogEndSpeedTest(String header){
        if(_startTimeNS == -1){
            //throw new OperationNotSupportedException("Can not stop speed test while it not started.");
            LogError("Can not stop speed test while it not started.");
        }
        long t = System.nanoTime();

        String s = "\n" + EndSpeedTestPrefix + " " + header + "Elapsed: " + (t - _startTimeNS) + "ns.";
        _LogToStream(s);
        _startTimeNS = -1;
    }
}
