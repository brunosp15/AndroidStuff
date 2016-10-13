ckage br.com.walmart.communication.retrofit;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;


public final class CustomInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        logRequest(request);

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        logResponse(response, tookMs);

        return response;
    }

    public void logResponse(Response response, long tookMs) throws IOException {
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();


        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }


        StringBuilder sb = new StringBuilder();
        sb.append("\n=============================== RESPONSE (" + response.code() + " in " + tookMs + "(ms)) ================================\n");
        sb.append("| URL -> " + response.request().url() + "\n");
        Headers headers = response.headers();
        if (headers.size() > 0) {
            sb.append("| HEADERS:\n");
            for (int i = 0, size = headers.size(); i < size; i++) {
                sb.append("| | " + headers.name(i) + ": " + headers.value(i) + "\n");
            }
        }

        if (contentLength != 0) {
            String body = buffer.clone().readString(charset);
            try {
                sb.append("| Body -> " + body + "\n");
                sb.append("| Body Pretty-> " + new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(body)) + "\n");
            } catch (Exception e) {
                sb.append("| Body -> " + body + "\n");
                e.printStackTrace();
            }
        }
        sb.append("================================ END RESPONSE =================================\n");

        Timber.tag("OkHttp");
        Timber.d(sb.toString());
    }

    public void logRequest(Request request) throws IOException {
        RequestBody requestBody = request.body();

        String method = request.method();
        HttpUrl url = request.url();


        StringBuilder sb = new StringBuilder();
        sb.append("\n================================== REQUEST ===================================\n");
        sb.append("| " + method + " -> " + url + "\n");
        Headers headers = request.headers();
        if (headers.size() > 0) {
            sb.append("| HEADERS:\n");
            for (int i = 0, size = headers.size(); i < size; i++) {
                sb.append("| | " + headers.name(i) + ": " + headers.value(i) + "\n");
            }
        }

        Buffer buffer = new Buffer();
        if (requestBody != null) {
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            requestBody.writeTo(buffer);
            String body = buffer.readString(charset);

            sb.append("| Body -> " + body + "\n");
        }
        sb.append("================================ END REQUEST =================================\n");
        Timber.tag("OkHttp");
        Timber.d(sb.toString());

    }
}

