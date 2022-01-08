package Services.Interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WebDownloadClient {

    //Return resulting file path
    String DownloadFileThreading(String URL, String file_path) throws URISyntaxException, IOException, InterruptedException, Exception;
}
