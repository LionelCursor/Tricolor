package com.ldx.tricolor.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ldx.tricolor.api.Request.RequestOptions;
import com.ldx.tricolor.assemblyline.RequestAssemblyLine;
import com.ldx.tricolor.worker.KeyGenerator;
import com.ldx.tricolor.worker.decoder.ImageDecoder;
import com.ldx.tricolor.worker.disk.DiskCacheFunc;
import com.ldx.tricolor.worker.fetcher.ImageFetcher;
import com.ldx.tricolor.worker.memory.MemoryCacheFunc;
import com.ldx.tricolor.worker.processor.ImageProcessor;

import java.io.File;

/**
 * Main class of Tricolor providing load methods for users.
 *
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public class Tricolor {

  public static final String TAG = Tricolor.class.getSimpleName();

  // Global variables of tricolor
  private Context context;

  private boolean isLoggingEnabled;

  private RequestOptions defaultRequestOptions;

  // Global workers
  private MemoryCacheFunc memoryCacheFunc;

  private DiskCacheFunc diskCacheFunc;

  private ImageDecoder imageDecoder;

  private ImageProcessor imageProcessor;

  private ImageFetcher fetcher;

  private KeyGenerator keyGenerator;

  // Private variable of this tricolor
  private RequestAssemblyLine requestAssemblyLine;

  // Lazy loading singleton instance.
  private static volatile Tricolor singleton = null;

  private Tricolor(Builder builder) {
    context = builder.context;
    memoryCacheFunc = builder.memoryCacheFunc;
    diskCacheFunc = builder.diskCacheFunc;
    imageDecoder = builder.imageDecoder;
    imageProcessor = builder.imageProcessor;
    fetcher = builder.fetcher;
    isLoggingEnabled = builder.isLoggingEnabled;
    defaultRequestOptions = builder.defaultRequestOptions;
    keyGenerator = builder.keyGenerator;
    requestAssemblyLine = builder.requestAssemblyLine;
    // Note
    requestAssemblyLine.with(this);
  }

  /**
   * Init your own configured Tricolor instance for the singleton to be used to load images.
   *
   * @throws IllegalArgumentException
   * @throws IllegalStateException
   */
  public static void init(Context context) {
    init(new Builder(context).build());
  }

  /**
   * Init your own configured Tricolor instance for the singleton to be used to load images.
   *
   * @param instance This instance will set to singleton.
   * @throws IllegalArgumentException
   * @throws IllegalStateException
   */
  public static void init(Tricolor instance) {
    if (instance == null) {
      throw new IllegalArgumentException("Instance passed in can not be null");
    }
    synchronized (Tricolor.class) {
      if (singleton != null) {
        throw new IllegalStateException("Init should be used first of all other use.");
      }
      singleton = instance;
    }
  }

  // Get the singleton
  public static Tricolor getInstance() {
    if (singleton == null) {
      throw new IllegalStateException("Init function should be invoked previously");
    }
    return singleton;
  }

  /**
   * @see {@link #load(Uri)}
   */
  public RequestCreator load(String uri) {
    return load(Uri.parse(uri));
  }

  /**
   * @see {@link #load(Uri)}
   */
  public RequestCreator load(File file) {
    return load(Uri.fromFile(file));
  }


  /**
   * create a request creator and then bring your demands to it.
   *
   * @param uri Uri of the image to load
   * @return The first request creator
   */
  public RequestCreator load(Uri uri) {
    if (uri == null) {
      throw new IllegalArgumentException("Uri can not be null");
    }
    return new RequestCreator(this, uri);
  }

  public void dispatchRequest(Request request) {
    if (isLoggingEnabled) {
      Log.e(TAG, "Dispatched request " + request);
    }
    // Dispatch this request. Let it be executed.
    requestAssemblyLine.execute(request);
  }

  public void pause() {
    // TODO not impl
  }

  public void resume() {
    // TODO not impl
  }


  public Context getContext() {
    return context;
  }

  public MemoryCacheFunc getMemoryCacheFunc() {
    return memoryCacheFunc;
  }

  public DiskCacheFunc getDiskCacheFunc() {
    return diskCacheFunc;
  }

  public ImageDecoder getImageDecoder() {
    return imageDecoder;
  }

  public ImageProcessor getImageProcessor() {
    return imageProcessor;
  }

  public ImageFetcher getFetcher() {
    return fetcher;
  }

  public boolean isLoggingEnabled() {
    return isLoggingEnabled;
  }

  public RequestOptions getDefaultRequestOptions() {
    return defaultRequestOptions;
  }

  public KeyGenerator getKeyGenerator() {
    return keyGenerator;
  }


  /**
   * Builder for Tricolor
   */
  public static final class Builder {
    private Context context;
    private boolean isLoggingEnabled = false;
    private RequestOptions defaultRequestOptions;
    private KeyGenerator keyGenerator;
    private RequestAssemblyLine requestAssemblyLine;
    private ImageDecoder imageDecoder;
    private ImageProcessor imageProcessor;
    private ImageFetcher fetcher;

    private boolean memoryCacheEnable = true;
    private int maxSize;
    private MemoryCacheFunc memoryCacheFunc;

    private boolean diskCacheEnable = true;
    private File diskCacheDir;
    private DiskCacheFunc diskCacheFunc;


    public Builder(Context context) {
      if (context == null) {
        throw new IllegalArgumentException("Context can not be null.");
      }
      this.context = context.getApplicationContext();
    }

    public Builder() {
    }

    public Tricolor build() {

      if (context == null) {
        throw new IllegalArgumentException("Context can not be null.");
      }

      if (requestAssemblyLine == null) {
        requestAssemblyLine = DefaultConfig.defaultRequestExecutor();
      }

      if (defaultRequestOptions == null) {
        defaultRequestOptions = DefaultConfig.defaultRequestOptions(context);
      }

      if (memoryCacheEnable) {
        if (maxSize == 0) {
          maxSize = 0;// 1 / 8 of main memory.
        }

        if (memoryCacheFunc == null) {
          memoryCacheFunc = DefaultConfig.defaultMemoryCacheFunc(maxSize);
        }
      }

      if (diskCacheEnable) {

        if (diskCacheDir == null) {
          diskCacheDir = context.getCacheDir();
        }

        if (diskCacheFunc == null) {
          diskCacheFunc = DefaultConfig.defaultDiskCacheManager(diskCacheDir);
        }

      }

      if (keyGenerator == null) {
        keyGenerator = DefaultConfig.defaultKeyGenerator();
      }

      return new Tricolor(this);
    }

    public Builder maxSize(int maxSize) {
      if (maxSize < 0) {
        throw new IllegalStateException("maxSize of memory cache must not below zero.");
      }
      this.maxSize = maxSize;
      return this;
    }

    public Builder isLoggingEnabled(boolean val) {
      isLoggingEnabled = val;
      return this;
    }

    public Builder defaultRequestOptions(RequestOptions val) {
      defaultRequestOptions = val;
      return this;
    }

    public Builder keyGenerator(KeyGenerator val) {
      keyGenerator = val;
      return this;
    }

    public Builder requestAssemblyLine(RequestAssemblyLine val) {
      requestAssemblyLine = val;
      return this;
    }

    public Builder context(Context val) {
      context = val;
      return this;
    }

    public Builder memoryCacheFunc(MemoryCacheFunc val) {
      if (!memoryCacheEnable) {
        throw new IllegalStateException("Memory cache is disabled.");
      }
      memoryCacheFunc = val;
      return this;
    }

    public Builder diskCacheFunc(DiskCacheFunc val) {
      diskCacheFunc = val;
      return this;
    }

    public Builder imageDecoder(ImageDecoder val) {
      imageDecoder = val;
      return this;
    }

    public Builder imageProcessor(ImageProcessor val) {
      imageProcessor = val;
      return this;
    }

    public Builder fetcher(ImageFetcher val) {
      fetcher = val;
      return this;
    }

    public Builder diskCacheManager(DiskCacheFunc val) {
      if (!diskCacheEnable) {
        throw new IllegalStateException("Disk cache is disabled.");
      }
      diskCacheFunc = val;
      return this;
    }

    public Builder memoryCacheManager(MemoryCacheFunc val) {
      memoryCacheFunc = val;
      return this;
    }
  }
}
