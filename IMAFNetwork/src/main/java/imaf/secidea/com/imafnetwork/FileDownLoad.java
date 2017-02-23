package imaf.secidea.com.imafnetwork;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by admin on 2017/1/20.
 */

public class FileDownLoad {
    public static void fileDownLoadResult(HttpURLConnection httpURLConnection, ImafRequest imafRequest, ICallBack iCallBack) {
        try {
            if (httpURLConnection.getResponseCode() == 200) {
                // 文件大小
                int fileLength = httpURLConnection.getContentLength();
                // 文件名
                String filePathUrl = httpURLConnection.getURL().getFile();
                String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
//                iCallBack.onSuccess(httpURLConnection.getInputStream());
                BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
                String path = imafRequest.getFileSaveDir() + File.separatorChar + fileFullName;
                File file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                OutputStream out = new FileOutputStream(file);
                int size = 0;
                int len = 0;
                byte[] buf = new byte[1024*10];
                while ((size = bin.read(buf)) != -1) {
                    len += size;
                    out.write(buf, 0, size);
                    iCallBack.onProgressUpdate(len,fileLength);
                }
                bin.close();
                out.close();
                iCallBack.onSuccess("文件下载成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

