package com.ldx.tricolor.core;

import rx.Observable;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class RxRequestExecutor implements RequestExecutor {

  @Override
  public void execute(Request request, Tricolor tricolor) {
    Observable.just(request)
        .map(tricolor.keyGenerator)
        .map(tricolor.memoryCacheFunc)
        .map(tricolor.diskCacheFunc)
        .map(tricolor.imageDecoder)
        .map(tricolor.imageProcessor)
        .subscribe(request.imageTarget);
  }
}
