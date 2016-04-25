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

  public static class RequestOptions {
    // Demands of load
    public int width;
    public int height;
    public boolean centerCrop;
    public boolean centerInside;

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
      centerCrop = options.centerCrop;
      centerInside = options.centerInside;

      setError = options.setError;
      errorResId = options.errorResId;
      setPlaceholder = options.setPlaceholder;
      placeholderResId = options.placeholderResId;
      fadeIn = options.fadeIn;

      cacheInDisk = options.cacheInDisk;
      cacheInMemory = options.cacheInMemory;
    }

    //TODO write a automatically util to generate toString() and hashcode
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
