package Models.HTTP;

import Models.HTTP.Interfaces.WebDownloadClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

public class MultiThreadDownloader implements WebDownloadClient {

    private final DownloadHttpClient _http_downloader;
    private int _response_delay_sec = 10;
    private final int _current_threads_count = 6;
    private final Thread[] _threads_pool = new Thread[_current_threads_count];
    private Function<Map.Entry<Integer, Integer>, Boolean> _progress_updater = null;

    public MultiThreadDownloader(int response_delay_sec) {
        _response_delay_sec = response_delay_sec;
        _http_downloader = new DownloadHttpClient(response_delay_sec);
    }

    @Override
    public boolean DownloadFileThreading(String URI, String folder_path) throws URISyntaxException, IOException, InterruptedException {
        //Getting the filename.
        final var tmp_arr = URLDecoder.decode(URI, StandardCharsets.UTF_8.toString()).split("/");
        final String file_name = tmp_arr[tmp_arr.length - 1];

        //Build a correct file path.
        final String file_path = folder_path + "\\" + file_name;

        int bytes_len = (int) _http_downloader.GetContentSize(URI);

        int part_size = bytes_len / _current_threads_count;

        int current_thread_id = 0;

        int left_pos = 0, right_pos = part_size - 1;

        //Object mutex = new Object();
        byte[] file_bytes = new byte[bytes_len];

        while (current_thread_id != _current_threads_count) {

            //For protect threads.
            int finalLeft_pos = left_pos, finalRight_pos = right_pos;
            int thread_id = current_thread_id;
            _threads_pool[current_thread_id] = new Thread(() -> {
                try {
                    var response = new DownloadHttpClient(_http_downloader.getResponseDelay())
                            .DownloadFilePart(URI, finalLeft_pos, finalRight_pos);
                    updateProgressChecked(12, thread_id);

                    //Can be optimised.
                    byte[] file_part = response.inputStream().readAllBytes();
                    updateProgressChecked(30, thread_id);

                    //+1 for correct progress division.
                    int progress = 30, inc = file_part.length / (100 - 30) + 1;
                    for (int i = 0; i < file_part.length; ++i) {
                        //We don't need synchronisation because we write data to
                        file_bytes[finalLeft_pos + i] = file_part[i];
                        if(i % inc == 0)
                            updateProgressChecked(++progress, thread_id);
                    }
                    updateProgressChecked(100, thread_id);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            _threads_pool[current_thread_id++].start();

            left_pos = right_pos + 1;
            right_pos += part_size;
            if (bytes_len - right_pos <= part_size)
                right_pos = bytes_len - 1;
        }

        for (var th : _threads_pool) th.join();

        try (var os = Files.newOutputStream(Paths.get(file_path))) {
            os.write(file_bytes);
            os.flush();
        }


        return true;
    }

    private void updateProgressChecked(int n, int thread_idx) {
        if (_progress_updater != null)
            _progress_updater.apply(new AbstractMap.SimpleEntry<>(thread_idx, n));
    }

    public Function GetProgressUpdater() {
        return _progress_updater;
    }

    public void SetProgressUpdater(Function _progress_updater) {
        this._progress_updater = _progress_updater;
    }
}
