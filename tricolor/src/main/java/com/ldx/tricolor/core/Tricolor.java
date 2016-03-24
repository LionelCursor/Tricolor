package com.ldx.tricolor.core;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ldx.tricolor.core.Request.RequestOptions;
import com.ldx.tricolor.decoder.ImageDecoder;
import com.ldx.tricolor.disk.DiskCacheFunc;
import com.ldx.tricolor.memory.MemoryCacheFunc;
import com.ldx.tricolor.processor.ImageProcessor;

import java.io.File;
import java.util.concurrent.Executor;

import rx.functions.Func1;

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

  // Global variable of tricolor

  Context context;

  MemoryCacheFunc memoryCacheFunc;

  DiskCacheFunc diskCacheFunc;

  ImageDecoder imageDecoder;

  ImageProcessor imageProcessor;

  Executor fetchThreadPool;

  boolean isLoggingEnabled;

  RequestOptions defaultRequestOptions;

  KeyGenerator keyGenerator = new KeyGenerator();

  // Private variable of this tricolor
  private RequestExecutor executor = null;

  // Lazy loading singleton instance.
  private static volatile Tricolor singleton = null;

  private Tricolor(Builder builder) {
    context = builder.context;
    memoryCacheFunc = builder.memoryCacheFunc;
    diskCacheFunc = builder.diskCacheFunc;
    imageDecoder = builder.imageDecoder;
    imageProcessor = builder.imageProcessor;
    fetchThreadPool = builder.fetchThreadPool;
    isLoggingEnabled = builder.isLoggingEnabled;
    defaultRequestOptions = builder.defaultRequestOptions;
    keyGenerator = builder.keyGenerator;
    executor = builder.executor;
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
   * TODO - not impl
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

  }

  public void pause() {
    // TODO not impl
  }

  public void resume() {
    // TODO not impl
  }

  private static class KeyGenerator implements Func1<Request, Intermediates> {

    @Override
    public Intermediates call(Request request) {

      return null;
    }
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

  public static void init(Context context) {
    init(new Builder(context).build());
  }

  /**
   * Builder for Tricolor
   */
  public static final class Builder {
    private Context context;
    private boolean isLoggingEnabled = false;
    private RequestOptions defaultRequestOptions;
    private KeyGenerator keyGenerator;
    private RequestExecutor executor;
    private RequestExecutor requestExecutor;
    private DiskCacheFunc diskCacheFunc;
    private ImageDecoder imageDecoder;
    private ImageProcessor imageProcessor;
    private Executor fetchThreadPool;
    private MemoryCacheFunc memoryCacheFunc;

    public Builder(Context context) {
      if (context == null) {
        throw new IllegalArgumentException("Context can not be null.");
      }
      this.context = context.getApplicationContext();
    }

    public Builder() {
    }

    public Tricolor build() {

      if (requestExecutor == null) {
        requestExecutor = DefaultConfig.defaultRequestExecutor();
      }

      if (defaultRequestOptions == null) {
        defaultRequestOptions = DefaultConfig.defaultRequestOptions(context);
      }

      if (memoryCacheFunc == null) {
        memoryCacheFunc = DefaultConfig.defaultMemoryCacheFunc();
      }

      if (diskCacheFunc == null) {
        diskCacheFunc = DefaultConfig.defaultDiskCacheManager();
      }

      return new Tricolor(this);
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

    public Builder executor(RequestExecutor val) {
      executor = val;
      return this;
    }

    public Builder requestExecutor(RequestExecutor val) {
      requestExecutor = val;
      return this;
    }

    public Builder context(Context val) {
      context = val;
      return this;
    }

    public Builder memoryCacheFunc(MemoryCacheFunc val) {
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

    public Builder diskCacheManager(DiskCacheFunc val) {
      diskCacheFunc = val;
      return this;
    }

    public Builder fetchThreadPool(Executor val) {
      fetchThreadPool = val;
      return this;
    }

    public Builder memoryCacheManager(MemoryCacheFunc val) {
      memoryCacheFunc = val;
      return this;
    }
  }
}
