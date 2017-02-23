package imaf.secidea.com.httpnetwork;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import imaf.secidea.com.imafnetwork.ImafHttpClient;
import imaf.secidea.com.imafnetwork.StringCallBack;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = Environment.getExternalStorageDirectory().toString() + "/Pictures/" +"2016_09_08_04_08_28.png";
        String path2 = Environment.getExternalStorageDirectory().toString() + "/Pictures/" +"2016_09_08_04_09_40.png";
        String path3 = Environment.getExternalStorageDirectory().toString() + "/Proguard.txt" ;
        String path4 = Environment.getExternalStorageDirectory().toString() + "/IMAF.txt";
        String path5 = Environment.getExternalStorageDirectory().toString() + "/ip6.txt";
        File file = new File(path);
        String json = "{\"IsActivated\":\"0\",\"Action\":\"0\",\"other\":null}";
        Map<String,Object> map = new HashMap<>();
        map.put("test","123");
        map.put("hello",36);
        List<File> files = new ArrayList<File>();
        List<String> requestBodyTypes = new ArrayList<String>();
        requestBodyTypes.add("Content-Disposition: form-data; name=\"file\"; filename=\"12.png\"");
        requestBodyTypes.add("Content-Type: application/octet-stream");
        files.add(new File(path3));
        files.add(new File(path4));
        files.add(new File(path5));
//        final RequestBody requestBody = new RequestBody()
//                .addFormDataPart(null,map)
//                .addStringPart("Content-Type:application/json; charset=utf-8",json)
//                .addFilePart("Content-Disposition: form-data; name=\"file\"; filename=\"2016_09_08_04_08_28.png\"",new File(path4))
//                .addFilePart("Content-Disposition: form-data; name=\"file2\"; filename=\"2016_09_08_04_08_30.png\"",new File(path5))
//                .addFilePart("Content-Disposition: form-data; name=\"file3\"; filename=\"2016_09_08_04_08_30.png\"",new File(path5))
//                .addFilePart("Content-Disposition: form-data; name=\"file4\"; filename=\"2016_09_08_04_08_30.png\"",new File(path5))
//                .addFilePart(requestBodyTypes,files);
//
        String url = "http://xxx.xxx.xxx.xxx/fuck.php?a=124&c=456#ffff";
        String params = url.substring(url.indexOf("?") + 1, url.length());
        Log.e(TAG, "onCreate: "+params );
        new ImafHttpClient()
                .setMethod("POST")
                .setReadTimeout(5000)
                .setConnectTimeout(10000)
                .setUrl("http://xxx.xxx.xxx.xxx/fuck.php")
                .addFilePart(requestBodyTypes,files)
                .addFormDataPart("Content-Type:application/json; charset=utf-8",map)
                .addStringPart("Content-Type:application/json; charset=utf-8",json)
                .addFilePart("Content-Disposition: form-data; name=\"file1\"; filename=\"2016_09_08_04_08_28.png\"",new File(path4))
                .addFilePart("Content-Disposition: form-data; name=\"file2\"; filename=\"2016_09_08_04_08_30.png\"",new File(path4))
                .execute(new StringCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e(TAG, "onSuccess: "+result );
                    }

                    @Override
                    public void onFailure(Exception result) {

                    }
                });
//        new ImafHttpClient()
//                .setMethod("GET")
//                .setFileSaveDir(Environment.getExternalStorageDirectory().toString()+"/Download")
//                .setUrl("http://192.168.199.165:81/test/com.hiapk.live_030511.apk")//http://192.168.199.223:81/test/com.hiapk.live_030511.apk,http://192.168.199.232/fuck.php
//                .execute(new StringCallBack() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Log.e(TAG, "onSuccess: "+result );
//                    }
//
//                    @Override
//                    public void onFailure(Exception result) {
//
//                    }
//
//                    @Override
//                    public void onProgressUpdate(long curPos, long contentLength) {
//                        Log.e(TAG, "onSuccess: "+curPos*100/contentLength+"%" );
//                    }
//                });
    }























}
