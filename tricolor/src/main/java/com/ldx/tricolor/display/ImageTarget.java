package com.ldx.tricolor.display;

import com.ldx.tricolor.core.Intermediates;

import rx.functions.Action1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface ImageTarget extends Action1<Intermediates> {

  @Override
  void call(Intermediates intermediates);
}
