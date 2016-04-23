package com.ldx.tricolor.worker.memory;

import android.graphics.Bitmap;

import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;

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
    Intermediates.validIntermediates(intermediates);

    Logger.v("Memory cache starts to process the intermediates.");

    Bitmap cached = get(intermediates.getKey());

    if (cached == null) {
      Logger.v("Memory cache miss.");
      return intermediates;
    }

    Logger.v("Memory cache hits");
    intermediates.setOrigin(Intermediates.Origin.MEMORY);
    intermediates.setBitmap(cached);
    return intermediates;
  }

}
