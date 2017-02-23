package imaf.secidea.com.imafnetwork;

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;


/**
 * Created by admin on 2017/1/12.
 */

public class NetworkExecutor {
    private static final String TAG = "NetworkExecutor";
    private static Handler mHandler;
    static {
        mHandler = new Handler();
    }
    public static void execute(final ImafRequest imafRequest, final ICallBack iCallBack){

        ThreadTask.getInstance().executorNetThread(new Runnable() {
            @Override
            public void run() {

                try {
                    final HttpURLConnection httpURLConnection  = HttpUrlConnectionUtils.execute(imafRequest);
                    if(imafRequest.isFileDownloader()){
                        FileDownLoad.fileDownLoadResult(httpURLConnection,imafRequest,iCallBack);
                    }else {
                        final String result = httpResult(httpURLConnection);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    iCallBack.onSuccess(result);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    postFailed(iCallBack,e);
                }
            }
        },imafRequest.getPriori());

    }

    private static String httpResult(HttpURLConnection connection) {
        int status = 0;
        String result = null;
        try {
            status = connection.getResponseCode();
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
                result = new String(out.toByteArray());
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void postFailed(final ICallBack iCallBack, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                iCallBack.onFailure(e);
            }
        });
    }

}
