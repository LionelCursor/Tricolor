package com.ldx.tricolor.worker.setup;

import com.ldx.tricolor.assemblyline.Intermediates;

import rx.functions.Action1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface ImageTarget extends Action1<Intermediates> {

  public int getRequireWidth();

  public int getRequireHeight();

  public void setPlaceholder(int resId);

  public void setError(int resId);


  @Override
  public void call(Intermediates intermediates);
}
