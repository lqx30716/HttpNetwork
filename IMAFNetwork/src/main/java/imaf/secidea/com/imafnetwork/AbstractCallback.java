package imaf.secidea.com.imafnetwork;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by admin on 2017/1/16.
 */

public abstract class AbstractCallback<T>  implements ICallBack<T>  {

    public T parse(HttpURLConnection connection) throws Exception {
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            is.close();
            out.flush();
            out.close();
            String result = new String(out.toByteArray());
            Log.e("TAG", "result: "+ result);
            // ★此行以上的所有代码是公用的，所以抽取出来，而下面的方法需要根据不同情况去解析
            return bindData(result);
        }
        return null;
    }
    /**
     * ★让子类去实现如何解析这个result（因为可能result是xml，也可能是json，或者bitmap）
     *
     * @param result
     * @return
     */
    protected abstract T bindData(String result) throws Exception;

    @Override
    public Object onPreHandle(Object object) {
        return null;
    }

    @Override
    public void onProgressUpdate(long curPos, long contentLength) {

    }

    @Override
    public Object onPresRequest() {
        return null;
    }

}
