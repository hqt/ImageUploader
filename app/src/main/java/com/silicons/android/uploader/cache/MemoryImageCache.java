package com.silicons.android.uploader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

public class MemoryImageCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryImageCache() {
        createCache();
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public boolean containsKey(String key) {
        return mMemoryCache.get(key) != null;
    }

    public void clearCache() {
        // recycle all caches ASAP
        createCache();
    }

    private void createCache() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/10th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 12;
        Log.e("hqthao", "cache size: " + cacheSize + " KB");
        mMemoryCache = new LruCache<String, Bitmap>(5 * 1024) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }
}