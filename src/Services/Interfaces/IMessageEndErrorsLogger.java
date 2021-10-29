package Services.Interfaces;

public interface IMessageEndErrorsLogger {
    void LogError(String msg);

    void LogInfo(String msg);

    void LogTotalErrorsCount();
}
