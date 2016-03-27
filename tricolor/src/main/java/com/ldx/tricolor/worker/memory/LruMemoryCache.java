package com.ldx.tricolor.worker.memory;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class LruMemoryCache extends BaseMemoryCacheFunc {

  private LruCache<String, Bitmap> cache;

  public LruMemoryCache(int maxSize) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("MaxSize helper");
    }
    this.cache = new LruCache<String, Bitmap>(maxSize) {
      @Override
      protected int sizeOf(String key, Bitmap value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
          return value.getAllocationByteCount();
        } else {
          return value.getByteCount();
        }
      }
    };
  }

  @Override
  public Bitmap get(String key) {
    return cache.get(key);
  }

  @Override
  public void put(String key, Bitmap bitmap) {
    cache.put(key, bitmap);
  }

  @Override
  public void remove(String key) {
    cache.remove(key);
  }

}
