package com.ldx.tricolor.core;

import android.content.Context;
import android.util.DisplayMetrics;

import com.ldx.tricolor.core.Request.RequestOptions;
import com.ldx.tricolor.disk.BaseDiskCacheFunc;
import com.ldx.tricolor.disk.DiskCacheFunc;
import com.ldx.tricolor.memory.BaseMemoryCacheFunc;
import com.ldx.tricolor.memory.MemoryCacheFunc;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class DefaultConfig {

  public static RequestExecutor defaultRequestExecutor() {
    return new RxRequestExecutor();
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

  public static MemoryCacheFunc defaultMemoryCacheFunc() {
    return new BaseMemoryCacheFunc();
  }

  public static DiskCacheFunc defaultDiskCacheManager() {
    return new BaseDiskCacheFunc();
  }

}
