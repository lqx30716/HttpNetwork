package imaf.secidea.com.imafnetwork;

import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/1/12.
 */

public class ImafHttpClient {
    ImafRequest imafRequest = new ImafRequest();
    RequestBody mRequestBody = new RequestBody();
    private String method;
    private Map<String, Object> mBodyParams = new HashMap<String, Object>();
    public ImafHttpClient setPriori(int priori) {
        imafRequest.setPriori(priori);
        return this;
    }

    public ImafHttpClient setConnectTimeout(int connectTimeout) {
        imafRequest.setConnectTimeout(connectTimeout);
        return this;
    }


    public ImafHttpClient setReadTimeout(int readTimeout) {
        imafRequest.setReadTimeout(readTimeout);
        return this;
    }


    public ImafHttpClient setMethod(String method) {
        if (TextUtils.isEmpty(method))
            throw new NullPointerException("content == null");
        this.method = method;
        imafRequest.setMethod(method);
        return this;
    }


    public ImafHttpClient setHeaders(Map<String, String> headers) {
        if (null == headers)
            throw new NullPointerException("content == null");
        imafRequest.setHeaders(headers);
        return this;
    }

    public ImafHttpClient setHeaders(String key, String value) {
        if (TextUtils.isEmpty(key))
            throw new NullPointerException("content == null");
        imafRequest.setHeaders(key, value);
        return this;
    }

    public ImafHttpClient setUrl(String url) {
        if (TextUtils.isEmpty(url))
            throw new NullPointerException("content == null");
        imafRequest.setUrl(url);
        return this;
    }

    public ImafHttpClient setFileSaveDir(String fileSaveDir) {
        if (TextUtils.isEmpty(fileSaveDir))
            throw new NullPointerException("content == null");
        imafRequest.setFileSaveDir(fileSaveDir);
        return this;
    }



    public ImafHttpClient setBodyType(String requestBodyType) {
        if (TextUtils.isEmpty(requestBodyType))
            throw new NullPointerException("content == null");
        mRequestBody.setBodyType(requestBodyType);
        return this;
    }

    public ImafHttpClient add(String key, Object value) {
        if (TextUtils.isEmpty(key) || null == value)
            throw new NullPointerException("content == null");
        mBodyParams.put(key, value);
        return this;
    }

    public ImafHttpClient add(String json) {
        if (TextUtils.isEmpty(json))
            throw new NullPointerException("content == null");
        mRequestBody.add(json);
        return this;
    }

    /**
     * 添加二进制参数, 例如Bitmap的字节流参数
     *
     * @param requestBodyType
     * @param rawData
     */
    public ImafHttpClient addBinaryPart(String requestBodyType, final byte[] rawData) {
        if (null == rawData)
            throw new NullPointerException("content == null");
        mRequestBody.addBinaryPart(requestBodyType, rawData);
        return this;
    }

    public ImafHttpClient addFormDataPart(String requestBodyType, Map<String, Object> bodyParams) {
        if (null == bodyParams || bodyParams.size() == 0)
            throw new NullPointerException("content == null");
        mRequestBody.addFormDataPart(requestBodyType, bodyParams);
        return this;
    }

    public ImafHttpClient addFilePart(String requestBodyType, File file) {
        if (null == file || TextUtils.isEmpty(requestBodyType))
            throw new NullPointerException("content == null");
        mRequestBody.addFilePart(requestBodyType, file);
        return this;
    }

    public ImafHttpClient addFilePart(List<String> requestBodyType, List<File> file) {
        if (null == file || file.isEmpty() || null == requestBodyType || requestBodyType.size() == 0)
            throw new NullPointerException("content == null");
        mRequestBody.addFilePart(requestBodyType, file);
        return this;
    }


    public ImafHttpClient addStringPart(String requestBodyType, String json) {
        if (TextUtils.isEmpty(json))
            throw new NullPointerException("content == null");
        mRequestBody.addStringPart(requestBodyType, json);
        return this;
    }

    public ImafHttpClient execute(ICallBack iCallBack) {
        if(mBodyParams.size()>0){
            mRequestBody.add(mBodyParams);
        }
        if (method.equals("POST")||method.equals("PUT")){
            imafRequest.post(mRequestBody);
        }
        NetworkExecutor.execute(imafRequest, iCallBack);
        return this;
    }
}
