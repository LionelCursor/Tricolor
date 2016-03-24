package com.ldx.tricolor.core;

import android.net.Uri;

import com.ldx.tricolor.display.ImageTarget;

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



  public static class RequestOptions {
    // Demands of load
    int width;
    int height;

    // Demands of display
    boolean setError;
    int errorResId;
    boolean setPlaceholder;
    int placeholderResId;
    boolean fadeIn;
    int fadeInMillis;

    // Demands of cache
    boolean cacheInMemory;
    boolean cacheInDisk;

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


  }
}
