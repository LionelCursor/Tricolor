package com.ldx.tricolor.disk;

import com.ldx.tricolor.core.Intermediates;

import rx.functions.Func1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface DiskCacheFunc extends Func1<Intermediates, Intermediates> {

  /**
   * This func should be used to get InputStream from disk cache.
   * If bitmap has been already gotten from memory, then just do nothing and pass by.
   *
   * @param intermediates provide the key, or memory got bitmap
   * @return return the InputStream in Intermediates
   */
  @Override
  Intermediates call(Intermediates intermediates);
}
