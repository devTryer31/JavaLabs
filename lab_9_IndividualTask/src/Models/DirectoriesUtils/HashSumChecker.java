package Models.DirectoriesUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSumChecker {

    public static boolean CheckFilesEqualityByHash(String path1, String path2, String algo_name) throws IOException, NoSuchAlgorithmException {
        return GetHashSum(path1, algo_name).equals(GetHashSum(path2, algo_name));
    }

    public static String GetHashSum(String path, String algo_name) throws IOException, NoSuchAlgorithmException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        var arr = MessageDigest.getInstance(algo_name).digest(bytes);
        var result = new StringBuilder();
        for (byte b : arr) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
