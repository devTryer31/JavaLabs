package Services.Interfaces;

public interface IConfigurationService {

    String getHashMethodName();

    String getExpectedHashSum();

    String[] getDownloadServerPorts();

}
