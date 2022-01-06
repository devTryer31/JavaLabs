package Controllers;

import Models.DirectoriesUtils.HashSumChecker;
import Models.HTTP.MultiThreadDownloader;
import Views.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MainWindowController {
    private final String _ErrorTitle = "Error!";
    private final MultiThreadDownloader _Downloader = new MultiThreadDownloader(30);
    private MainWindow _Main_window = null;

    //public int[] ThreadsProgress = new int[6];

    public MainWindowController() {
        _Downloader.SetProgressUpdater(this::updateDownloadProcess);
    }

    private void updateDownloadProcess(Map.Entry<Integer, Integer> pair) {
        var thread_id = pair.getKey();
        var curr_process = pair.getValue();

        _Main_window.DisplayProgressById(thread_id, curr_process);
        //ThreadsProgress[thread_id] = curr_process;
    }

    private void startDownloading(Map.Entry<String, String> pair) {
        try {
            String uri = pair.getKey(), path = pair.getValue();
            String result_file_path = _Downloader.DownloadFileThreading(uri, path);
            JOptionPane.showMessageDialog(_Main_window,
                    "Success downloading.\n MD5 hash: " + HashSumChecker.GetHashSum(result_file_path, "MD5"));
        } catch (URISyntaxException e) {
            JOptionPane.showMessageDialog(_Main_window, "Invalid URL input.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(_Main_window, "Invalid URL or path.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(_Main_window, "One of the operation was interrupted.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(_Main_window, "Runtime multithreading error. Partial download interrupted.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(_Main_window, "Hash name algorithm fail.", _ErrorTitle, JOptionPane.ERROR_MESSAGE);
        }
    }


    public void StartSession() {
        EventQueue.invokeLater(()->{
            _Main_window = new MainWindow(this::startDownloading);
            //_Main_window.SetStartButtonClickHandler(this::startDownloading);
            _Main_window.setVisible(true);
        });
    }
}
