package imaf.secidea.com.imafnetwork;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by admin on 2017/1/12.
 */

public class HttpUrlConnectionUtils {


    public static HttpURLConnection execute(ImafRequest imafRequest) {
        switch (imafRequest.getMethod()) {
            case "GET":
            case "DELETE":
                return get(imafRequest);
            case "POST":
            case "PUT":
                return post(imafRequest);
            default:
                throw new IllegalStateException("the method" + imafRequest.getMethod() + "doesn't support");
        }
    }

    private static HttpURLConnection post(ImafRequest imafRequest) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(imafRequest.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(imafRequest.getMethod());
            connection.setConnectTimeout(imafRequest.getConnectTimeout());
            connection.setReadTimeout(imafRequest.getReadTimeout());
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            addHeader(connection, imafRequest);
            DataOutputStream dataOutputStream =
                    new DataOutputStream(connection.getOutputStream());
            byte[] body = imafRequest.getBodyContent();
            if (null != body)
            dataOutputStream.write(body);
            dataOutputStream.close();
            return connection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static HttpURLConnection get(ImafRequest imafRequest) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(imafRequest.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(imafRequest.getMethod());
            connection.setConnectTimeout(imafRequest.getConnectTimeout());
            connection.setReadTimeout(imafRequest.getReadTimeout());
            connection.setDoInput(true);
            connection.setUseCaches(false);
            addHeader(connection, imafRequest);
            connection.connect();
            return connection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void addHeader(HttpURLConnection connection, ImafRequest imafRequest) {
        Map<String,String> headers = imafRequest.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(),entry.getValue());
        }
    }
}
