package com.ldx.tricolor.core;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * The core of all over the library.
 * It defines all the processing sequences of a request.
 *
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class RxRequestExecutor implements RequestExecutor {

  private Observable<Intermediates> observable = null;

  // All the global variable
  private final Tricolor tricolor;

  public RxRequestExecutor(Tricolor tricolor) {
    if (tricolor == null) {
      throw new IllegalArgumentException("Tricolor can not be null");
    }
    this.tricolor = tricolor;
  }

  public RxRequestExecutor start(Request request) {
    observable = Observable.just(new Intermediates(request, tricolor));
    return this;
  }

  // Key generated
  public RxRequestExecutor generateKey() {
    observable = observable.map(tricolor.keyGenerator);
    return this;
  }

  // Get bitmap from memory
  public RxRequestExecutor memoryCache() {
    observable = observable.map(tricolor.memoryCacheFunc);
    return this;
  }

  // If there is not bitmap gotten from memory, then open InputStream from disk.
  public RxRequestExecutor diskCache() {
    observable = observable.map(tricolor.diskCacheFunc);
    return this;
  }

  // If there is no bitmap gotten from memory and disk, then got from network
  public RxRequestExecutor fetch() {
    observable = observable.map(tricolor.fetcher);
    return this;
  }

  // If there is not bitmap gotten from memory,
  // then decode the InputStream. Note the demands in options.
  // Put new bitmap decoded in memory cache right now.
  // And check whether we need to put the bitmap stored in disk. If so, put it.
  public RxRequestExecutor decode() {
    observable = observable.map(tricolor.imageDecoder);
    return this;
  }

  // No matter decoded or get from memory, process the bitmap.
  public RxRequestExecutor process() {
    observable = observable.map(tricolor.imageProcessor);
    return this;
  }

  public Subscription subscribe() {
    return observable.subscribe(new Action1<Intermediates>() {
      @Override
      public void call(Intermediates intermediates) {
        if (intermediates == null) {
          throw new IllegalArgumentException("Intermediates of subscribe can not be null.");
        }
        if (intermediates.getRawRequest() == null) {
          throw new IllegalArgumentException("Request of intermediates in subscribe can not be null.");
        }
        if (intermediates.getRawRequest().imageTarget == null) {
          throw new IllegalArgumentException("ImageTarget attached to request in subscribe can not be null");
        }
        intermediates.getRawRequest().imageTarget.call(intermediates);
      }
    });
  }

  @Override
  public void execute(Request request) {
    start(request)
        .generateKey()
        .memoryCache()
        .diskCache()
        .fetch()
        .decode()
        .process()
        .subscribe();
  }
}
