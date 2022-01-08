import Controllers.MainWindowController;
import Services.ConfigurationService;
import Views.MainWindow;

public class ITmain {
    public static void main(String[] args) {
        var main_controller = new MainWindowController(new ConfigurationService());
        main_controller.StartSession();

//        MultiThreadDownloader downloader = new MultiThreadDownloader(30);
//
//        downloader.SetProgressUpdater((n) -> {
//            try {
//                Thread.sleep(100L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            var pair = (Map.Entry<Integer, Integer>) n;
//            System.out.println("Progress on " + pair.getKey() + " is: " + pair.getValue());
//            return true;
//        });
//
//        try {
//            downloader.DownloadFileThreading("https://speedtest.selectel.ru/100MB", "D:\\Temp");
//            System.out.println(HashSumChecker.GetHashSum("D:\\Temp\\100MB", "MD5"));
//            System.out.println(HashSumChecker.GetHashSum("D:\\Temp\\100MB_2", "MD5"));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
    }
}
