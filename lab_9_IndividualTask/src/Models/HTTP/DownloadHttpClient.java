package Models.HTTP;

import Models.HTTP.Interfaces.WebDownloadClient;

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

public class DownloadHttpClient implements WebDownloadClient {

    private final HttpClient _webClient;
    private int _response_delay = 10;
    private final HttpClient.Version _http_requests_version = HttpClient.Version.HTTP_2;

    //Internal response struct.
    private static class Response {
        final int status;
        final HttpHeaders headers;
        final BufferedInputStream inputStream;

        public Response(BufferedInputStream inputStream, int status, HttpHeaders headers) {
            this.inputStream = inputStream;
            this.status = status;
            this.headers = headers;
        }
    }

    public DownloadHttpClient(int response_delay){
        setResponseDelay(response_delay);
        _webClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(this._response_delay))
                .build();
    }

    public int getResponseDelay(){
        return _response_delay;
    }

    public void setResponseDelay(int sec){
        if(sec <= 0)
            throw new InvalidParameterException("Response delay must be positive");
        _response_delay = sec;
    }

    @Override
    public boolean DownloadFileThreading(String URL, String file_path, int thread_cunt) {


        return false;
    }

    //Getting a size of downloading file by uri.
    private long getContentSize(String URI) throws URISyntaxException, IOException, InterruptedException {

        //Build a head for getting content len request
        var head = HttpRequest.newBuilder(new URI(URI))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .version(_http_requests_version)
                .build();
        //Get a response in string view.
        var response = _webClient.send(head, HttpResponse.BodyHandlers.ofString());

        //Get from response headers content-length value or 0 if not found.
        return response.headers().firstValueAsLong("content-length").orElse(-1);
    }

    //Get a response with file part from first_byte to last_byte positions.
    private Response downloadFilePart(String URI, int first_byte, int last_byte) throws URISyntaxException, IOException, InterruptedException {

        //Getting the request description to download a part of file.
        var request = HttpRequest.newBuilder(new URI(URI))
                .header("Range", "bytes="+first_byte+"-"+last_byte)
                .GET().version(_http_requests_version)
                .build();

        var responce = _webClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

        return new Response(new BufferedInputStream(responce.body()), responce.statusCode(),responce.headers());
    }
}
