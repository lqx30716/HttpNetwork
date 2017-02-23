package imaf.secidea.com.imafnetwork;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by admin on 2017/1/19.
 */

public abstract class JsonCallBack<T> extends AbstractCallback<T> {
    @Override
    protected T bindData(String result) throws Exception {
        JSONObject object = new JSONObject(result);
        JSONObject data = object.optJSONObject("data");
        Gson gson = new Gson();
        //获取声明的泛型参数类型
        Type type = ((ParameterizedType) getClass().
                getGenericSuperclass()).getActualTypeArguments()[0];
        return gson.fromJson(data.toString(), type);
    }
}
