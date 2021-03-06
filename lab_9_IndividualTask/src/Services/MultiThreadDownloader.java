package Services;

import Models.HTTP.DownloadHttpClient;
import Services.Interfaces.WebDownloadClient;
import Services.Interfaces.IConfigurationService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Consumer;

/**The class responsible for multithreaded downloading with the output of the progress of each thread.*/
public class MultiThreadDownloader implements WebDownloadClient {

    private final DownloadHttpClient _http_downloader;
    private final IConfigurationService _config_service;
    private final int _current_threads_count = 6;
    private final Thread[] _threads_pool = new Thread[_current_threads_count];
    private Consumer<Map.Entry<Integer, Integer>> _progress_updater = null;

    /**@param response_delay_sec Will be set for each thread. If the time runs out, the thread will stop downloading.*/
    public MultiThreadDownloader(int response_delay_sec, IConfigurationService config_service) {
        _http_downloader = new DownloadHttpClient(response_delay_sec);
        _config_service = config_service;
    }

    /**
     * Algorithm mainly used DownloadHttpClient.DownloadFilePart(String, int, int) method.
     * @param URI The download file internet location. (http link).
     * @param folder_path Output directory path.
     * @return Resulting file path.
     * @throws Exception Threads exceptions rethrowings.
     */
    @Override
    public String DownloadFileThreading(String URI, String folder_path) throws URISyntaxException, IOException, InterruptedException, Exception {
        //Getting correct filename without '%20' eth.
        final var tmp_arr = URLDecoder.decode(URI, StandardCharsets.UTF_8.toString()).split("/");
        final String file_name = tmp_arr[tmp_arr.length - 1];

        //Build a correct file path.
        final String file_path = folder_path + "\\" + file_name;

        int bytes_len = (int) _http_downloader.GetContentSize(URI);

        int part_size = bytes_len / _current_threads_count;

        int current_thread_id = 0;

        int left_pos = 0, right_pos = part_size - 1;

        byte[] file_bytes = new byte[bytes_len];

        //For 'rethrowing' exceptions from child threads.
        final ExceptionLocator exceptionLocator = new ExceptionLocator();

        while (current_thread_id != _current_threads_count) {

            //For protect threads.
            int finalLeft_pos = left_pos, finalRight_pos = right_pos;
            int thread_id = current_thread_id;
            _threads_pool[current_thread_id] = new Thread(() -> {
                try {
                    String[] ss = URI.split("/");
                    //Enter port to domain.
                    ss[2] = ss[2]+":"+_config_service.getDownloadServerPorts()[thread_id];
                    String thread_uri = String.join("/", ss);

                    var response = new DownloadHttpClient(_http_downloader.getResponseDelay())
                            .DownloadFilePart(thread_uri, finalLeft_pos, finalRight_pos);

                    if (!(200 <= response.status() && response.status() < 300))
                        throw new RuntimeException("Bad response code.");

                    var response_stream = response.inputStream();
                    updateProgressChecked(12, thread_id);

                    //Bytes writing there.
                    //+1 for correct progress division.
                    int progress = 12, inc = (finalRight_pos - finalLeft_pos) / (100 - progress) + 1;
                    int idx = finalLeft_pos;
                    int b;
                    while ((b = response_stream.read()) != -1) {
                        file_bytes[idx++] = (byte) b;
                        if (idx % inc == 0)
                            updateProgressChecked(++progress, thread_id);
                    }

                    updateProgressChecked(100, thread_id);//Progress coverage.
                } catch (Exception e) {
                    exceptionLocator.exception = e;
                    exceptionLocator.isCaught = true;
                }
            });

            _threads_pool[current_thread_id++].start();

            left_pos = right_pos + 1;
            right_pos += part_size;
            if (bytes_len - right_pos <= part_size)
                right_pos = bytes_len - 1;
        }

        for (var th : _threads_pool) th.join();

        if (exceptionLocator.isCaught) {
            throw exceptionLocator.exception;
        }

        try (var os = Files.newOutputStream(Paths.get(file_path))) {
            os.write(file_bytes);
            os.flush();
        }

        return file_path;
    }

    /**
     * Call back to ProgressUpdater method to notify about new thread download progress state.
     * @param n Thread progress state [0-100].
     * @param thread_idx Targeted thread index [0-5].
     */
    private void updateProgressChecked(int n, int thread_idx) {
        if (_progress_updater != null)
            _progress_updater.accept(new AbstractMap.SimpleEntry<>(thread_idx, n));
    }

    /**@param progress_updater This method will call when the new download progress will be received.*/
    public void SetProgressUpdater(Consumer<Map.Entry<Integer, Integer>> progress_updater) {
        this._progress_updater = progress_updater;
    }

    /**Subsidiary internal struct.*/
    private static class ExceptionLocator {
        public Exception exception = null;
        public boolean isCaught = false;
    }
}
