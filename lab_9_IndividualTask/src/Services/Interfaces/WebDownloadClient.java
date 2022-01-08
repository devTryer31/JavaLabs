package Services.Interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WebDownloadClient {

    /**
     * Start download implemented process with threads.
     * @param URI The download file internet location. (http link).
     * @param folder_path Output directory path.
     * @return Resulting file path.
     * @throws Exception Threads exceptions rethrowings.
     */
    String DownloadFileThreading(String URI, String folder_path) throws URISyntaxException, IOException, InterruptedException, Exception;
}
