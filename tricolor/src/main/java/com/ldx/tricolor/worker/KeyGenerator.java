package com.ldx.tricolor.worker;

import com.ldx.tricolor.assemblyline.Intermediates;

import rx.functions.Func1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public interface KeyGenerator extends Func1<Intermediates, Intermediates> {
  @Override
  Intermediates call(Intermediates intermediates);
}
