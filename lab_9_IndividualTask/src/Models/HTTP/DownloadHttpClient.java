package Models.HTTP;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidParameterException;
import java.time.Duration;

/**A special http client for downloading files based on the HttpClient class.*/
public class DownloadHttpClient{

    private final HttpClient _webClient;
    private int _response_delay_sec = 10;
    private final HttpClient.Version _http_requests_version = HttpClient.Version.HTTP_2;

    //Internal response struct.
    public record Response(BufferedInputStream inputStream, int status, HttpHeaders headers) {}

    /**@param response_delay_sec If the time runs out, the thread will stop downloading.*/
    public DownloadHttpClient(int response_delay_sec){
        setResponseDelay(response_delay_sec);
        _webClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(this._response_delay_sec))
                .build();
    }

    public int getResponseDelay(){
        return _response_delay_sec;
    }

    /**@param sec If the time runs out, the thread will stop downloading.*/
    public void setResponseDelay(int sec){
        if(sec <= 0)
            throw new InvalidParameterException("Response delay must be positive");
        _response_delay_sec = sec;
    }

    /**
     * Getting a size of downloading file by uri.
     * @param URI Content locator (http linc).
     * @return Content size.
     */
    public long GetContentSize(String URI) throws URISyntaxException, IOException, InterruptedException {

        //Build a head for getting content len request.
        var head = HttpRequest.newBuilder(new URI(URI))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .version(_http_requests_version)
                .build();
        //Get a response in string view.
        var response = _webClient.send(head, HttpResponse.BodyHandlers.ofString());

        //Get from response headers content-length value or 0 if not found.
        return response.headers().firstValueAsLong("content-length").orElse(-1);
    }

    /**
     * Get a response with file part from first_byte to last_byte positions.
     * @param URI Content locator (http linc).
     * @param first_byte Start byte position to download.
     * @param last_byte End byte position to download.
     * @return Download try response.
     */
    public Response DownloadFilePart(String URI, int first_byte, int last_byte) throws URISyntaxException, IOException, InterruptedException {

        //Getting the request description to download a part of file.
        var request = HttpRequest.newBuilder(new URI(URI))
                .header("Range", "bytes="+first_byte+"-"+last_byte)
                .GET().version(_http_requests_version)
                .build();

        var response = _webClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

        return new Response(new BufferedInputStream(response.body()), response.statusCode(), response.headers());
    }
}
