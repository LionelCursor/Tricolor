package com.ldx.tricolor.worker.display;

import com.ldx.tricolor.assemblyline.Intermediates;

import rx.functions.Action1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface ImageTarget extends Action1<Intermediates> {

  @Override
  public void call(Intermediates intermediates);
}
