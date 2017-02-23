package imaf.secidea.com.imafnetwork;

import java.net.HttpURLConnection;

/**
 * Created by admin on 2017/1/12.
 */

public interface ICallBack<T> {

    void onSuccess(T result);
    void onFailure(Exception e);

//    Object handle(HttpResponse response, IProgressListener iProgressListener);

    void onProgressUpdate(long curPos, long contentLength);

    /**
     * 在子线程中对返回值做预处理，比如保存到数据库等等操作（预处理返回的对象）
     * 如果不需要什么处理的话，什么都不需要做
     * @param object
     * @return
     */
    Object onPreHandle(Object object);

    Object onPresRequest();
    T parse(HttpURLConnection connection) throws Exception;
}
