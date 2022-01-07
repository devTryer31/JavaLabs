package Controllers;

import Controllers.Interfaces.IExceptionHandleController;
import Models.DirectoriesUtils.HashSumChecker;
import Services.MultiThreadDownloader;
import Views.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

public class MainWindowController {
    private final MainWindow _Main_window = new MainWindow(this::startDownloading);
    private final IExceptionHandleController _exceptions_handler = new ExceptionHandleController(_Main_window);
    private final MultiThreadDownloader _Downloader = new MultiThreadDownloader(30);

    public MainWindowController() {
        _Downloader.SetProgressUpdater(this::updateDownloadProcess);
    }

    private void updateDownloadProcess(Map.Entry<Integer, Integer> pair) {
        var thread_id = pair.getKey();
        var curr_process = pair.getValue();

        _Main_window.DisplayProgressById(thread_id, curr_process);
    }

    private void startDownloading(Map.Entry<String, String> pair) {
        try {
            String uri = pair.getKey(), path = pair.getValue();

            if (uri.isBlank()) {
                _exceptions_handler.HandleException(new IllegalArgumentException("URL must be non-empty."));
                //JOptionPane.showMessageDialog(_Main_window, "URL must be non-empty.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (path.isBlank() || !new File(path).exists()) {
                _exceptions_handler.HandleException(new IllegalArgumentException("Save path invalid."));
//                JOptionPane.showMessageDialog(_Main_window, "Save path invalid.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }

            var t = System.currentTimeMillis();
            String result_file_path = _Downloader.DownloadFileThreading(uri, path);
            var res_t = (System.currentTimeMillis() - t) / 1000;
            JOptionPane.showMessageDialog(_Main_window,
                    "Success downloading.\n" + res_t + " second left." + "\n MD5 hash: " + HashSumChecker.GetHashSum(result_file_path, "MD5"));
        }catch (Exception e) {
            _exceptions_handler.HandleException(e);
        }
    }


    public void StartSession() {
        EventQueue.invokeLater(() -> {
            _Main_window.setVisible(true);
        });
    }
}
