import Models.HTTP.MultiThreadDownloader;

import java.io.IOException;
import java.net.URISyntaxException;

public class ITmain {
    public static void main(String[] args){
        MultiThreadDownloader downloader = new MultiThreadDownloader(30);
        try {
            downloader.DownloadFileThreading("https://speedtest.selectel.ru/100MB","D:\\Temp\\100MB_2");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
