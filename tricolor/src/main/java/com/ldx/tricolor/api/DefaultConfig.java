package com.ldx.tricolor.api;

import android.content.Context;
import android.util.DisplayMetrics;

import com.ldx.tricolor.api.Request.RequestOptions;
import com.ldx.tricolor.assemblyline.RequestAssemblyLine;
import com.ldx.tricolor.assemblyline.RxRequestAssemblyLine;
import com.ldx.tricolor.assemblyline.RxRequestAssemblyLine.BaseKeyGenerator;
import com.ldx.tricolor.assemblyline.RxRequestAssemblyLine.KeyGenerator;
import com.ldx.tricolor.worker.disk.BaseDiskCacheFunc;
import com.ldx.tricolor.worker.disk.DiskCacheFunc;
import com.ldx.tricolor.worker.memory.BaseMemoryCacheFunc;
import com.ldx.tricolor.worker.memory.LruMemoryCache;
import com.ldx.tricolor.worker.memory.MemoryCacheFunc;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class DefaultConfig {

  public static RequestAssemblyLine defaultRequestExecutor() {
    return new RxRequestAssemblyLine();
  }

  public static RequestOptions defaultRequestOptions(Context context) {
    RequestOptions options = new RequestOptions();
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    options.width = metrics.widthPixels;
    options.height = metrics.heightPixels;
    options.cacheInDisk = true;
    options.cacheInMemory = true;
    options.setError = false;
    options.setPlaceholder = false;
    options.fadeIn = false;
    return options;
  }

  public static MemoryCacheFunc defaultMemoryCacheFunc(int maxSize) {
    return new LruMemoryCache(maxSize);
  }

  public static DiskCacheFunc defaultDiskCacheManager() {
    return new BaseDiskCacheFunc();
  }

  public static KeyGenerator defaultKeyGenerator() {
    return new BaseKeyGenerator();
  }

}
