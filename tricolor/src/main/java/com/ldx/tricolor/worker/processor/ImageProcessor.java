package com.ldx.tricolor.worker.processor;

import com.ldx.tricolor.assemblyline.Intermediates;

import rx.functions.Func1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface ImageProcessor extends Func1<Intermediates, Intermediates> {
  @Override
  Intermediates call(Intermediates intermediates);
}
