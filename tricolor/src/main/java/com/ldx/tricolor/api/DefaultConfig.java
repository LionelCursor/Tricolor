package com.ldx.tricolor.api;

import android.content.Context;

import com.ldx.tricolor.api.Request.RequestOptions;
import com.ldx.tricolor.assemblyline.RequestAssemblyLine;
import com.ldx.tricolor.assemblyline.RxRequestAssemblyLine;
import com.ldx.tricolor.worker.decoder.BaseImageDecoder;
import com.ldx.tricolor.worker.decoder.ImageDecoder;
import com.ldx.tricolor.worker.disk.DiskCacheFunc;
import com.ldx.tricolor.worker.disk.UnlimitedDiskCacheFunc;
import com.ldx.tricolor.worker.fetcher.ImageFetcher;
import com.ldx.tricolor.worker.fetcher.BaseImageFetcher;
import com.ldx.tricolor.worker.memory.LruMemoryCache;
import com.ldx.tricolor.worker.memory.MemoryCacheFunc;
import com.ldx.tricolor.worker.pretreat.BasePretreatment;
import com.ldx.tricolor.worker.pretreat.Pretreatment;

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
    options.width = 0;
    options.height = 0;
    options.centerCrop = true;
    options.centerInside = false;
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

  public static Pretreatment defaultPretreatment() {
    return new BasePretreatment();
  }

  public static ImageDecoder defaultDecoder() {
    return new BaseImageDecoder();
  }

  public static ImageFetcher defaultFetcher() {
    return new BaseImageFetcher();
  }

}
