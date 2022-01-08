package Services.Interfaces;

import java.io.IOException;

public interface IConfigurationService {

    String getHashMethodName();

    String getExpectedHashSum();

    String[] getDownloadServerPorts();

    void setConfigPath(String path) throws IOException;
}
