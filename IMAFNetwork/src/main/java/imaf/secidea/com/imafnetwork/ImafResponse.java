package imaf.secidea.com.imafnetwork;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by admin on 2017/1/12.
 */

public class ImafResponse {
    public static byte[] rawData = new byte[0];

    public static byte[] getResult(HttpURLConnection connection) {
        int status = 0;
        ByteArrayOutputStream out = null;
        try {
            status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                out = new ByteArrayOutputStream();
                InputStream is = connection.getInputStream();
                byte[] buffer = new byte[2048];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                is.close();
                out.flush();
                out.close();
                rawData = out.toByteArray();

            }

            return rawData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] getRawData() {
        return rawData;
    }
}
