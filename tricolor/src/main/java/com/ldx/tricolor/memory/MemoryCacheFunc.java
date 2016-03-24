package com.ldx.tricolor.memory;

import android.graphics.Bitmap;

import com.ldx.tricolor.core.Intermediates;

import java.util.Collection;

import rx.functions.Func1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface MemoryCacheFunc extends Func1<Intermediates, Intermediates> {

  public Bitmap get();

  public boolean put(String key, Bitmap bitmap);

  public void evict(String key);

  public Collection<String> keys();

  // Whether the memory cache contains the bitmap.
  // If so, get it. Otherwise, transfer it immediately
  @Override
  public Intermediates call(Intermediates rawRequest);

}
