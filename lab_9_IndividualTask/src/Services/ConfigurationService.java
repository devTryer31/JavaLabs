package Services;

import Services.Interfaces.IConfigurationService;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Properties;

public class ConfigurationService implements IConfigurationService {

    private final String _hashMethodNameIndex = "hash_method";
    private final String _expectedHashSumIndex = "expected_hash";
    private final String _downloadServerPortsIndex = "server_ports";

    private final Properties _props;

    public ConfigurationService(String config_file_path) throws IOException {
        _props = new Properties();
        setConfigPath(config_file_path);
    }

    public ConfigurationService(){
        _props = new Properties();
    }

    @Override
    public String getHashMethodName() {
        return _props.getProperty(_hashMethodNameIndex);
    }

    @Override
    public String getExpectedHashSum() {
        return _props.getProperty(_expectedHashSumIndex);
    }

    @Override
    public String[] getDownloadServerPorts() {
        var res = _props.getProperty(_downloadServerPortsIndex).split("\\|");
        if(res.length == 0 || res.length > 6)
            throw new IllegalArgumentException("Server ports value is invalid in properties file.");
        return res;
    }

    public void setConfigPath(String path) throws IOException {
        if(path == null || path.isBlank())
            throw new IllegalArgumentException("Config file path invalid");
        _props.load(new FileInputStream(path));
    }
}
