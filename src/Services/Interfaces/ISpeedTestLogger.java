package Services.Interfaces;

public interface ISpeedTestLogger {

    void LogStartSpeedTest(String header, String timeType);

    long LogEndSpeedTest(String header, String timeType);

}