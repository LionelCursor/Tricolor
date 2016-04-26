package com.ldx.tricolor.api;

import android.net.Uri;
import android.widget.ImageView;

import com.ldx.tricolor.api.Request.RequestOptions;
import com.ldx.tricolor.worker.setup.ImageTarget;
import com.ldx.tricolor.worker.setup.SimpleImageTarget;

/**
 * The builder pattern of Request, it will create a request which contains the detail message in it.
 *
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public class RequestCreator {

  Tricolor tricolor;

  Uri uri;

  ImageTarget target;

  RequestOptions options;

  public RequestCreator(Tricolor tricolor, Uri uri) {
    this.tricolor = tricolor;
    this.uri = uri;
  }

  private void checkOptions() {
    if (this.options == null) {
      // Copy the default options in tricolor global variable
      RequestOptions options = tricolor.getDefaultRequestOptions();
      if (options == null) {
        throw new IllegalArgumentException("Please set default request options in tricolor.");
      }
      this.options = new RequestOptions(options);
    }
  }

  // DEMANDS OF CACHE

  public RequestCreator noCacheInMemory() {
    checkOptions();
    options.cacheInMemory = false;
    return this;
  }

  public RequestCreator cacheInMemory() {
    checkOptions();
    options.cacheInMemory = true;
    return this;
  }

  public RequestCreator noCacheInDisk() {
    checkOptions();
    options.cacheInDisk = false;
    return this;
  }

  public RequestCreator cacheInDisk() {
    checkOptions();
    options.cacheInDisk = true;
    return this;
  }

  // DEMANDS OF DECODING

  public RequestCreator resize(int width, int height) {
    checkOptions();
    options.width = width;
    options.height = height;
    return this;
  }

  // DEMANDS OF DISPLAYING

  public RequestCreator noFade() {
    checkOptions();
    options.fadeIn = false;
    return this;
  }

  public RequestCreator fadeInMillis(int millis) {
    checkOptions();
    options.fadeIn = true;
    options.fadeInMillis = millis;
    return this;
  }

  public RequestCreator noError() {
    checkOptions();
    options.setError = false;
    return this;
  }

  public RequestCreator error(int resId) {
    checkOptions();
    options.setError = true;
    options.errorResId = resId;
    return this;
  }

  public RequestCreator centerInside(boolean centerInside) {
    checkOptions();
    options.centerInside = centerInside;
    return this;
  }

  public RequestCreator centerCrop(boolean centerCrop) {
    checkOptions();
    options.centerCrop = centerCrop;
    return this;
  }

  public RequestCreator noPlaceholder() {
    checkOptions();
    options.setPlaceholder = false;
    return this;
  }

  public RequestCreator placeholder(int resId) {
    checkOptions();
    options.setPlaceholder = true;
    options.placeholderResId = resId;
    return this;
  }

  public void into(ImageTarget target) {
    if (target == null) {
      throw new IllegalArgumentException("ImageTarget can not be null.");
    }
    this.target = target;
    // Now, we have target
    // And Uri has been prepared when construct
    // Then checkOptions for not null
    checkOptions();
    // Dispatch this request.
    tricolor.dispatchRequest(new Request(this));
  }

  public void asBitmap() {
    //TODO create callback pattern or observable pattern.
  }

  public void into(ImageView view) {
    into(new SimpleImageTarget(view));
  }

  public void fetch(ImageTarget target) {
    into(target);
  }
}
