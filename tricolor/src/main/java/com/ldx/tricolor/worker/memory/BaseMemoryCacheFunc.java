package com.ldx.tricolor.worker.memory;

import android.graphics.Bitmap;

import com.ldx.tricolor.assemblyline.Intermediates;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public abstract class BaseMemoryCacheFunc implements MemoryCacheFunc {

  @Override
  public abstract Bitmap get(String key);

  @Override
  public abstract void put(String key, Bitmap bitmap);

  @Override
  public abstract void remove(String key);

  @Override
  public Intermediates call(Intermediates intermediates) {
    if (intermediates == null) {
      throw new IllegalArgumentException("Intermediates can not be null");
    }

    if (intermediates.getRawRequest() == null) {
      throw new IllegalStateException("Request can not be null");
    }

    if (intermediates.getKey() == null || intermediates.getKey().isEmpty()) {
      throw new IllegalStateException("Key of request can not be null or empty.");
    }

    if (intermediates.getTricolor().isLoggingEnabled()) {

    }

    Bitmap cached = get(intermediates.getKey());

    if (cached == null) {

      return intermediates;
    }

    return null;
  }

}
