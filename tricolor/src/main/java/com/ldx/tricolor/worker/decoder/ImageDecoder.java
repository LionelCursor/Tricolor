package com.ldx.tricolor.worker.decoder;

import com.ldx.tricolor.assemblyline.Intermediates;

import rx.functions.Func1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface ImageDecoder extends Func1<Intermediates, Intermediates> {

  /**
   * @param intermediates transferred from previous Func1
   * @return the transferred intermediates
   */
  @Override
  Intermediates call(Intermediates intermediates);
}
