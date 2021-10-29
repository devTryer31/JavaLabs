package Services.Interfaces;

import javax.naming.OperationNotSupportedException;

public interface ISpeedTestLogger {

    void LogStartSpeedTest(String header);

    void LogEndSpeedTest(String header);

}
