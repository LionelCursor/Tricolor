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

  protected Tricolor(TricolorBuilder builder) {
    context = builder.context;
    memoryCacheFunc = builder.memoryCacheFunc;
    diskCacheFunc = builder.diskCacheFunc;
    imageDecoder = builder.imageDecoder;
    imageProcessor = builder.imageProcessor;
    fetcher = builder.imageFetcher;
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
    init(new TricolorBuilder(context).build());
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


}
