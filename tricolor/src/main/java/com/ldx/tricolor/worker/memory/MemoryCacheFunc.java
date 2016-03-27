package com.ldx.tricolor.worker.memory;

import android.graphics.Bitmap;

import com.ldx.tricolor.assemblyline.Intermediates;

import rx.functions.Func1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface MemoryCacheFunc extends Func1<Intermediates, Intermediates> {

  public Bitmap get(String key);

  public void put(String key, Bitmap bitmap);

  public void remove(String key);

  // Whether the memory cache contains the bitmap.
  // If so, get it. Otherwise, transfer it immediately
  @Override
  public Intermediates call(Intermediates intermediates);

}
