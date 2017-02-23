package imaf.secidea.com.imafnetwork;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2017/1/12.
 */

public class RequestBody {
    private final static char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();
    /**
     * 换行符
     */
    private final String NEW_LINE_STR = "\r\n";
    private final String CONTENT_TYPE = "Content-Type: ";
    private final String CONTENT_DISPOSITION = "Content-Disposition: ";
    /**
     * 文本参数和字符集
     */
    private final String TYPE_TEXT_CHARSET = "text/plain; charset=UTF-8";

    /**
     * 字节流参数
     */
    private final String TYPE_OCTET_STREAM = "application/octet-stream";
    /**
     * 二进制参数
     */
    private final byte[] BINARY_ENCODING = "Content-Transfer-Encoding: binary\r\n\r\n".getBytes();
    /**
     * 文本参数
     */
    private final byte[] BIT_ENCODING = "Content-Transfer-Encoding: 8bit\r\n\r\n".getBytes();

    /**
     * 分隔符
     */
    private String mBoundary = null;
    private String mRequestType = null;
    private String mapContent = null;
    private String jsonContent = null;
    private List<File> files = new ArrayList<File>();
    private Map<String,File> fileLists = new HashMap<String, File>();
    /**
     * 输出流
     */
    ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
    private Map<String, Object> mBodyParams = new HashMap<String, Object>();
    public RequestBody(){
        this.mBoundary = generateBoundary();
    }

    public RequestBody setBodyType(String requestBodyType){
        if (TextUtils.isEmpty(requestBodyType))
            throw new NullPointerException("content == null");
        mRequestType = requestBodyType;
        return this;
    }
    public RequestBody add(Map<String, Object> bodyParams) {

        this.mBodyParams = bodyParams;
        String mapContent = mapToPostParams(mBodyParams);
        writeToOutputStream(mapContent.getBytes(),mRequestType);
        return this;
    }

    public RequestBody add(String json) {
        jsonContent = json;
        writeToOutputStream(jsonContent.getBytes(),mRequestType);
        return this;
    }
    /**
     * 添加二进制参数, 例如Bitmap的字节流参数
     *
     * @param requestBodyType
     * @param rawData
     */
    public RequestBody addBinaryPart(String requestBodyType, final byte[] rawData) {
        if (null == rawData )
            throw new NullPointerException("content == null");
        mRequestType = requestBodyType;
        writeToOutputStream(rawData,mRequestType);
        return this;
    }

    public RequestBody addFormDataPart(String requestBodyType,Map<String,Object> bodyParams) {
        if (null == bodyParams || bodyParams.size() == 0)
            throw new NullPointerException("content == null");
        String requestBody = mapToPostParams(bodyParams);
        mRequestType = requestBodyType;
        writeToOutputStream(requestBody.getBytes(),mRequestType);
        return this;
    }

    public RequestBody addFilePart(String requestBodyType, File file) {
        if (null == file || TextUtils.isEmpty(requestBodyType))
            throw new NullPointerException("content == null");
        fileLists.put(requestBodyType,file);
        Log.e(TAG, "addFilePart: "+fileLists.size() );
        InputStream fin = null;
        try {
            for (Map.Entry<String,File> entry : fileLists.entrySet()){
                fin = new FileInputStream(entry.getValue());
                writeFirstBoundary();
                if (TextUtils.isEmpty(entry.getKey())){
                    mRequestType = CONTENT_TYPE + TYPE_OCTET_STREAM + NEW_LINE_STR;
                }else {
                    mRequestType = entry.getKey()+ NEW_LINE_STR;
                }
                mOutputStream.write(mRequestType.getBytes());
                mOutputStream.write(NEW_LINE_STR.getBytes());

                final byte[] tmp = new byte[4096];
                int len = 0;
                while ((len = fin.read(tmp)) != -1) {
                    mOutputStream.write(tmp, 0, len);
                }
                mOutputStream.write(NEW_LINE_STR.getBytes());

            }
            mOutputStream.flush();
            fileLists.clear();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(fin);
        }
        return this;
    }
    public RequestBody addFilePart(List<String> requestBodyType, List<File> file) {
        if (null == file || file.isEmpty() || null == requestBodyType || requestBodyType.size() == 0)
            throw new NullPointerException("content == null");
        InputStream fin = null;

        try {
            for(int i = 0;i<file.size();i++){
                fin = new FileInputStream(file.get(i));
                writeFirstBoundary();
                if (null != requestBodyType){
                    for(int j = 0; j < requestBodyType.size(); j++){
                        if (TextUtils.isEmpty(requestBodyType.get(j))){
                            mRequestType = CONTENT_TYPE + TYPE_OCTET_STREAM + NEW_LINE_STR;
                        }else {
                            mRequestType = requestBodyType.get(j)+ NEW_LINE_STR;
                        }
                        mOutputStream.write(mRequestType.getBytes());
                    }
                }
                mOutputStream.write(NEW_LINE_STR.getBytes());
                final byte[] tmp = new byte[4096];
                int len = 0;
                while ((len = fin.read(tmp)) != -1) {
                    mOutputStream.write(tmp, 0, len);
                }
                mOutputStream.write(NEW_LINE_STR.getBytes());
            }
            mOutputStream.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(fin);
        }
        return this;
    }


    public RequestBody addStringPart(String requestBodyType,String json) {
        if (TextUtils.isEmpty(json))
            throw new NullPointerException("content == null");
        mRequestType = requestBodyType;
        writeToOutputStream(json.getBytes(),mRequestType);
        return this;
    }

    /**
     * 生成分隔符
     *
     * @return
     */
    private final String generateBoundary() {
        final StringBuffer buf = new StringBuffer();
        final Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buf.toString();
    }
    private void closeSilently(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据写入到输出流中
     *
     * @param rawData
     * @param type
     */
    private void writeToOutputStream(byte[] rawData, String type) {
        try {

            if(TextUtils.isEmpty(type)){
                mOutputStream.write(rawData);
                mOutputStream.write(NEW_LINE_STR.getBytes());
            }else {
                writeFirstBoundary();
                mOutputStream.write((type + NEW_LINE_STR).getBytes());
                mOutputStream.write(NEW_LINE_STR.getBytes());
                mOutputStream.write(rawData);
                mOutputStream.write(NEW_LINE_STR.getBytes());
            }

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    private byte[] getContentDispositionBytes(String paramName, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTENT_DISPOSITION + "form-data; name=\"" + paramName + "\"");
        // 文本参数没有filename参数,设置为空即可
        if (!TextUtils.isEmpty(fileName)) {
            stringBuilder.append("; filename=\""
                    + fileName + "\"");
        }

        return stringBuilder.append(NEW_LINE_STR).toString().getBytes();
    }
    /**
     * 参数开头的分隔符
     *
     * @throws IOException
     */
    private void writeFirstBoundary() throws IOException {
        mOutputStream.write(("--" + mBoundary + "\r\n").getBytes());
    }


    private static String mapToPostParams(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        if (params != null && params.size() > 0){
            Set<Map.Entry<String, Object>> sets = params.entrySet();
            // 将Hashmap转换为string
            for (Map.Entry<String, Object> entry : sets){
                if (entry.getValue() instanceof String ||
                        entry.getValue() instanceof Boolean ||
                        entry.getValue() instanceof Integer ||
                        entry.getValue() instanceof Float ||
                        entry.getValue() instanceof Double){
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(entry.getValue());
                    sb.append("&");
                }

            }
            sb.deleteCharAt(sb.length()-1);

        }
        return sb.toString();
    }
    public byte[] getContent() throws IOException {
        if(!TextUtils.isEmpty(mRequestType)){
            // 参数最末尾的结束符
            final String endString = "--" + mBoundary + "--\r\n";
            // 写入结束符
            mOutputStream.write(endString.getBytes());
        }
        return mOutputStream.toByteArray();
    }

}
