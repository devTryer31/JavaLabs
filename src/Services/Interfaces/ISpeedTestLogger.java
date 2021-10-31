package Services.Interfaces;

import javax.naming.OperationNotSupportedException;

public interface ISpeedTestLogger {

    void LogStartSpeedTest(String header, String timeType);

    long LogEndSpeedTest(String header, String timeType);

}
