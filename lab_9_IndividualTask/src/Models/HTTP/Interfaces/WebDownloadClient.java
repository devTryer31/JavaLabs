package Models.HTTP.Interfaces;

public interface WebDownloadClient {
    boolean DownloadFileThreading(String URL, String file_path, int thread_cunt);
}
