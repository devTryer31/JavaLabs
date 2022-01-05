package Models.HTTP.Interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WebDownloadClient {
    boolean DownloadFileThreading(String URL, String file_path) throws URISyntaxException, IOException, InterruptedException;
}
