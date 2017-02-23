package imaf.secidea.com.imafnetwork;

/**
 * Created by admin on 2017/1/20.
 */

public interface Cache<K, V> {

    public V get(K key);

    public void put(K key, V value);

    public void remove(K key);

}
