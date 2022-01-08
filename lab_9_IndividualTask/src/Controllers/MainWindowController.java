package Controllers;

import Controllers.Interfaces.IExceptionHandleController;
import Models.DirectoriesUtils.HashSumChecker;
import Services.Interfaces.IConfigurationService;
import Services.MultiThreadDownloader;
import Views.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

/**
 * Main apps controller.
 */
public class MainWindowController {
    private final MainWindow _Main_window = new MainWindow(this::startDownloading);
    private final IExceptionHandleController _exceptions_handler = new ExceptionHandleController(_Main_window);
    private final MultiThreadDownloader _Downloader;
    private final IConfigurationService _config_service;

    /**
     * @param config_service Service for reading and application configuration.
     */
    public MainWindowController(IConfigurationService config_service) {
        _config_service = config_service;
        _Downloader = new MultiThreadDownloader(30, _config_service);
        _Downloader.SetProgressUpdater(this::updateDownloadProcessProgress);
    }

    /**
     * Callback like method that represent a process progress to window view.
     * @param pair Key - thread [0-5] index. Values - threads progress.
     */
    private void updateDownloadProcessProgress(Map.Entry<Integer, Integer> pair) {
        var thread_id = pair.getKey();
        var curr_process = pair.getValue();

        _Main_window.DisplayProgressById(thread_id, curr_process);
    }

    /**
     * The main app method. From there will be started the threading downloading process.
     * @param pair Key - URI to download. Value - file output path.
     */
    private void startDownloading(Map.Entry<String, String> pair) {
        try {
            //Dynamic configs changing.
            _config_service.setConfigPath(_Main_window.GetConfigPath());

            String uri = pair.getKey(), path = pair.getValue();

            if (uri.isBlank()) {
                _exceptions_handler.HandleException(new IllegalArgumentException("URL must be non-empty."));
                return;
            }
            if (path.isBlank() || !new File(path).exists()) {
                _exceptions_handler.HandleException(new IllegalArgumentException("Save path invalid."));
                return;
            }

            var t = System.currentTimeMillis();
            String result_file_path = _Downloader.DownloadFileThreading(uri, path);
            var res_t = (System.currentTimeMillis() - t) / 1000;

            String algo_name = _config_service.getHashMethodName();
            String expected_hash = _config_service.getExpectedHashSum();
            String received_hash = HashSumChecker.GetHashSum(result_file_path, algo_name);
            if (received_hash.equals(expected_hash))
                JOptionPane.showMessageDialog(_Main_window,
                        "Successful downloading.\n" + res_t + " second left.\nCorrect hash sum was received.");
            else
                JOptionPane.showMessageDialog(_Main_window, "Unsuccessful downloading.\n" +
                        "Hash " + expected_hash + " expected\n" +
                        "Hash " + received_hash + " got.");
        } catch (Exception e) {
            _exceptions_handler.HandleException(e);
        }
    }

    /**
     * Show GUI.
     */
    public void StartSession() {
        EventQueue.invokeLater(() -> {
            _Main_window.setVisible(true);
        });
    }
}
