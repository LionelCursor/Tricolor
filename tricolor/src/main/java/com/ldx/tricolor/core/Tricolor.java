package com.ldx.tricolor.core;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ldx.tricolor.core.Request.RequestOptions;
import com.ldx.tricolor.decoder.ImageDecoder;
import com.ldx.tricolor.disk.DiskCacheFunc;
import com.ldx.tricolor.fetcher.ImageFetcher;
import com.ldx.tricolor.memory.MemoryCacheFunc;
import com.ldx.tricolor.processor.ImageProcessor;

import java.io.File;

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

  ImageFetcher fetcher;

  boolean isLoggingEnabled;

  RequestOptions defaultRequestOptions;

  KeyGenerator keyGenerator;

  // Private variable of this tricolor
  private RequestExecutor requestExecutor;

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
    requestExecutor = builder.requestExecutor;
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
    requestExecutor.execute(request);
  }

  public void pause() {
    // TODO not impl
  }

  public void resume() {
    // TODO not impl
  }

  public interface KeyGenerator extends Func1<Intermediates, Intermediates> {
  }

  public static class BaseKeyGenerator implements KeyGenerator {

    @Override
    public Intermediates call(Intermediates intermediates) {
      if (intermediates == null) {
        throw new IllegalStateException("Intermediates can not be null.");
      }
      Request request = intermediates.getRawRequest();
      if (request == null) {
        throw new IllegalStateException("Request of intermediates can not be null.");
      }
      intermediates.key = request.uri + "-" + request.options.width + "x" + request.options.height;
      return intermediates;
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
    private RequestExecutor requestExecutor;
    private DiskCacheFunc diskCacheFunc;
    private ImageDecoder imageDecoder;
    private ImageProcessor imageProcessor;
    private ImageFetcher fetcher;
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

      if (keyGenerator == null) {
        keyGenerator = DefaultConfig.defaultKeyGenerator();
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

    public Builder fetcher(ImageFetcher val) {
      fetcher = val;
      return this;
    }

    public Builder diskCacheManager(DiskCacheFunc val) {
      diskCacheFunc = val;
      return this;
    }

    public Builder memoryCacheManager(MemoryCacheFunc val) {
      memoryCacheFunc = val;
      return this;
    }
  }
}
