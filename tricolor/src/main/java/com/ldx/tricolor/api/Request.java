package com.ldx.tricolor.api;

import android.net.Uri;

import com.ldx.tricolor.worker.setup.ImageTarget;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public class Request {

  public Uri uri;

  public ImageTarget imageTarget;

  public RequestOptions options = null;

  public Request(RequestCreator creator) {
    imageTarget = creator.target;
    uri = creator.uri;
    options = creator.options;
  }

  @Override
  public String toString() {
    return "[Uri = " + uri + "; RequestOptions = " + options.toString() + "]";
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  public static class RequestOptions {
    // Demands of load
    public int width;
    public int height;

    // Demands of display
    public boolean setError;
    public int errorResId;
    public boolean setPlaceholder;
    public int placeholderResId;
    public boolean fadeIn;
    public int fadeInMillis;

    // Demands of cache
    public boolean cacheInMemory;
    public boolean cacheInDisk;

    public RequestOptions() {
    }

    // Copy function
    public RequestOptions(RequestOptions options) {

      width = options.width;
      height = options.height;

      setError = options.setError;
      errorResId = options.errorResId;
      setPlaceholder = options.setPlaceholder;
      placeholderResId = options.placeholderResId;
      fadeIn = options.fadeIn;

      cacheInDisk = options.cacheInDisk;
      cacheInMemory = options.cacheInMemory;
    }

    @Override
    public String toString() {
      return "[cacheInMemory = " + cacheInMemory + "; " +
          "cacheInDisk = " + cacheInDisk + "; " +
          "width = " + width + "; " +
          "height = " + height + "; " +
          "setError = " + setError + "; " +
          "setPlaceholder = " + setPlaceholder +
          "]";
    }
  }
}
