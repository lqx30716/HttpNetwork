package imaf.secidea.com.imafnetwork;

/**
 * Created by admin on 2017/1/22.
 */

import android.util.LruCache;

/**
 * 将请求结果缓存到内存中
 *
 * @author mrsimple
 */
public class LruMemCache implements Cache<String, ImafResponse> {

    /**
     * Reponse缓存
     */
    private LruCache<String, ImafResponse> mResponseCache;

    public LruMemCache() {
        // 计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // 取八分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 8;
        mResponseCache = new LruCache<String, ImafResponse>(cacheSize) {
            @Override
            protected int sizeOf(String key, ImafResponse response) {
                return response.rawData.length / 1024;
            }
        };

    }

    @Override
    public ImafResponse get(String key) {
        
        return mResponseCache.get(key);
    }

    @Override
    public void put(String key, ImafResponse response) {
        mResponseCache.put(key, response);
    }

    @Override
    public void remove(String key) {
        mResponseCache.remove(key);
    }
}
