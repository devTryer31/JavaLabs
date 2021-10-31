package Services;

import Services.Interfaces.ILogger;
import Services.Interfaces.ISpeedTestLogger;
import views.MainView;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public final class Logger implements ILogger, ISpeedTestLogger {

    private final OutputStream _stream;

    private HashMap<String, Long> _startTimes = new HashMap<String, Long>();
    private long _errorCount = 0;

    public Logger(OutputStream stream) {
        _stream = stream;
        LogInfo("=>\n\nStart new logging session! \n[" + _GetTimePrefix() + "\n]");
    }

    public static String LoggerPrefix = "logger -> ";
    public static String ErrorPrefix = "Error: ";
    public static String InfoPrefix = "Info: ";
    public static String StartSpeedTestPrefix = "Starting speed test: ";
    public static String EndSpeedTestPrefix = "End speed test: ";

    private void _LogToStream(String message) {
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
    public void LogError(String msg) {
        ++_errorCount;
        String s = _GetTimePrefix() + ' ' + LoggerPrefix + ErrorPrefix + msg;
        _LogToStream(s);
    }

    @Override
    public void LogInfo(String msg) {
        String s = _GetTimePrefix() + ' ' + LoggerPrefix + InfoPrefix + msg;
        _LogToStream(s);
    }

    @Override
    public void LogTotalErrorsCount() {
        _LogToStream("\nTotal errors count: " + _errorCount);
    }

    @Override
    public void LogStartSpeedTest(String header, String timeType) {
        String s = "\n" + StartSpeedTestPrefix + " " + header;
        _LogToStream(s);
        if (timeType.toLowerCase(Locale.ROOT).equals("ns"))
            _startTimes.put(header, System.nanoTime());
        else if (timeType.toLowerCase(Locale.ROOT).equals("ms"))
            _startTimes.put(header, System.currentTimeMillis());
        else
            LogError("Invalid timeType got in LogStartSpeedTest(String header, String timeType) where timeType=" + timeType);
    }

    @Override
    public long LogEndSpeedTest(String header, String timeType) {
        var prevTime = _startTimes.get(header);
        if (prevTime == null) {
            //throw new OperationNotSupportedException("Can not stop speed test while it not started.");
            LogError("Can not stop speed test with header [" + header + "] while it not started.");
            return -1;
        }
        long t = 0;
        if (timeType.toLowerCase(Locale.ROOT).equals("ns"))
            t = System.nanoTime();
        else if (timeType.toLowerCase(Locale.ROOT).equals("ms"))
            t = System.currentTimeMillis();
        else {
            LogError("Invalid timeType got in LogEndSpeedTest(String header, String timeType) where timeType=" + timeType);
            return -1;
        }
        long res = t - prevTime;
        String s = "\n" + EndSpeedTestPrefix + " " + header + " Elapsed: " + res + timeType.toLowerCase(Locale.ROOT);
        _LogToStream(s);
        _startTimes.remove(header);
        return res;
    }
}
