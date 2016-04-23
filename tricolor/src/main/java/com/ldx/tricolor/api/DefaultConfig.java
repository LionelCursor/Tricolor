package com.ldx.tricolor.api;

import android.content.Context;
import android.util.DisplayMetrics;

import com.ldx.tricolor.api.Request.RequestOptions;
import com.ldx.tricolor.assemblyline.RequestAssemblyLine;
import com.ldx.tricolor.assemblyline.RxRequestAssemblyLine;
import com.ldx.tricolor.assemblyline.RxRequestAssemblyLine.BaseKeyGenerator;
import com.ldx.tricolor.worker.KeyGenerator;
import com.ldx.tricolor.worker.decoder.BaseImageDecoder;
import com.ldx.tricolor.worker.decoder.ImageDecoder;
import com.ldx.tricolor.worker.disk.DiskCacheFunc;
import com.ldx.tricolor.worker.disk.UnlimitedDiskCacheFunc;
import com.ldx.tricolor.worker.fetcher.ImageFetcher;
import com.ldx.tricolor.worker.fetcher.ImageFetcherImpl;
import com.ldx.tricolor.worker.memory.LruMemoryCache;
import com.ldx.tricolor.worker.memory.MemoryCacheFunc;

import java.io.File;

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

  public static DiskCacheFunc defaultDiskCacheManager(File cacheDir) {
    return new UnlimitedDiskCacheFunc(cacheDir);
  }

  public static KeyGenerator defaultKeyGenerator() {
    return new BaseKeyGenerator();
  }

  public static ImageDecoder defaultDecoder() {
    return new BaseImageDecoder();
  }

  public static ImageFetcher defaultFetcher() {
    return new ImageFetcherImpl();
  }

}
