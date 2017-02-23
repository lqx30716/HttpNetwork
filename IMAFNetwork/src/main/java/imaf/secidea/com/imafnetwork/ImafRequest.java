package imaf.secidea.com.imafnetwork;

import java.util.HashMap;
import java.util.Map;

public class ImafRequest {
    private static final int TIMEOUT_CONNECTION = 5000;
    private static final int TIMEOUT_READ = 5000;
    private String url;
    private String method;
    private byte[] bodyContent;
    private Map<String,String> mHeaders = new HashMap<String, String>();
    private int mConnectTimeout = TIMEOUT_CONNECTION;
    private int mReadTimeout = TIMEOUT_READ;
    private boolean isFileDownloader = false;




    //默认的编码格式
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    //优先级默认为NORMAL
    protected int priori = ThreadPeriod.NORMAL;

    RequestBody mRequestBody;
    private String fileSaveDir = "/sdcard/download";

    public int getPriori()
    {
        return priori;
    }

    public ImafRequest setPriori(int priori)
    {
        this.priori = priori;
        return this;
    }
    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public ImafRequest setConnectTimeout(int connectTimeout) {
        this.mConnectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public ImafRequest setReadTimeout(int readTimeout) {
        this.mReadTimeout = readTimeout;
        return this;
    }


    public String getMethod() {
        return method;
    }

    public ImafRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public ImafRequest setHeaders(Map<String, String> headers) {
        this.mHeaders = headers;
        return this;
    }
    public ImafRequest setHeaders(String key,String value) {
        this.mHeaders.put(key, value);
        return this;
    }
    public String getUrl() {
        return url;
    }

    public ImafRequest setUrl(String url) {
        this.url = url;
        return this;
    }


    public ImafRequest put(RequestBody requestBody) {
        try {
            bodyContent = requestBody.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public ImafRequest post(RequestBody requestBody) {
        try {

            bodyContent = requestBody.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public byte[] getBodyContent() {
        return bodyContent;
    }

    public ImafRequest setFileSaveDir(String fileSaveDir) {
        this.fileSaveDir = fileSaveDir;
        isFileDownloader = true;
        return this;
    }
    public String getFileSaveDir() {
        return fileSaveDir;
    }
    public boolean isFileDownloader() {
        return isFileDownloader;
    }

}
