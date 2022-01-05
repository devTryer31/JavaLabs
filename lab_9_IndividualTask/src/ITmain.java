import DirectoriesUtils.HashSumChecker;
import Models.HTTP.MultiThreadDownloader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

public class ITmain {
    public static void main(String[] args){
        MultiThreadDownloader downloader = new MultiThreadDownloader(30);
        try {
            downloader.DownloadFileThreading("https://speedtest.selectel.ru/100MB","D:\\Temp");
            System.out.println(HashSumChecker.GetHashSum("D:\\Temp\\100MB", "MD5"));
            System.out.println(HashSumChecker.GetHashSum("D:\\Temp\\100MB_2", "MD5"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
