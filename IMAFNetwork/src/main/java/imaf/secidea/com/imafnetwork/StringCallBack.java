package imaf.secidea.com.imafnetwork;

/**
 * Created by admin on 2017/1/16.
 */

public abstract class StringCallBack extends AbstractCallback<String> {

    @Override
    protected String bindData(String result) {
        this.onSuccess(result);
        return result;
    }
}
