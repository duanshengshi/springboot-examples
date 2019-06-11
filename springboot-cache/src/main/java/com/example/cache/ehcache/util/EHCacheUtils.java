package com.example.cache.ehcache.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EHCacheUtils {
    private static CacheManager cacheManager = CacheManager.newInstance();

    public static CacheManager getCacheManager(){
        if(cacheManager!=null){
            return cacheManager;
        }else {
            return CacheManager.newInstance();
        }
    }

    /**
     * 设置缓存对象
     * @param cacheName
     * @param key
     * @param object
     */
    public static void setCache(String cacheName, String key, Object object){

        Cache cache = cacheManager.getCache(cacheName);

        Element element = new Element(key,object);
        cache.put(element);
    }

    /**
     * 从缓存中取出对象
     * @param cacheName
     * @param key
     * @return
     */
    public static Object getCache(String cacheName,String key){
        Object object = null;
        Cache cache = cacheManager.getCache(cacheName);
        if(cache.get(key)!=null && !cache.get(key).equals("")){
            object = cache.get(key).getObjectValue();
        }
        return object;

    }
}
