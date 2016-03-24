package com.ldx.tricolor.memory;

import android.graphics.Bitmap;

import com.ldx.tricolor.core.Intermediates;

import java.util.Collection;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class BaseMemoryCacheFunc implements MemoryCacheFunc{

  @Override
  public Bitmap get() {
    return null;
  }

  @Override
  public boolean put(String key, Bitmap bitmap) {
    return false;
  }

  @Override
  public void evict(String key) {

  }

  @Override
  public Collection<String> keys() {
    return null;
  }

  @Override
  public Intermediates call(Intermediates rawRequest) {
    return null;
  }

}
