package com.ldx.tricolor.assemblyline;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.utils.Logger;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * The core of all over the library.
 * It defines all the processing sequences of a request.
 *
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class RxRequestAssemblyLine implements RequestAssemblyLine {

  private Observable<Intermediates> observable = null;

  // All the global worker contains in Tricolor instance
  private Tricolor tricolor = null;

  public RxRequestAssemblyLine() {
  }

  @Override
  public void with(Tricolor tricolor) {
    if (tricolor == null) {
      throw new IllegalArgumentException("Tricolor passed in can not be null");
    }
    if (this.tricolor != null) {
      throw new IllegalStateException("Tricolor can not be passed in twice.");
    }
    this.tricolor = tricolor;
  }

  public RxRequestAssemblyLine start(Request request) {
    observable = Observable.just(new Intermediates(request, tricolor));
    observable = observable.subscribeOn(Schedulers.io());
    return this;
  }

  // Key generated
  public RxRequestAssemblyLine generateKey() {
    if (tricolor.getPretreatment() == null) {
      throw new IllegalStateException("Must have key generator.");
    }
    observable = observable.map(tricolor.getPretreatment());
    return this;
  }

  // Get bitmap from memory
  public RxRequestAssemblyLine memoryCache() {
    if (tricolor.getMemoryCacheFunc() == null) {
      return this;
    }
    observable = observable.map(tricolor.getMemoryCacheFunc());
    return this;
  }

  // If there is not bitmap gotten from memory, then open InputStream from disk.
  public RxRequestAssemblyLine diskCache() {
    if (tricolor.getDiskCacheFunc() == null) {
      return this;
    }
    observable = observable.map(tricolor.getDiskCacheFunc());
    return this;
  }

  // If there is no bitmap gotten from memory and disk, then got from network
  public RxRequestAssemblyLine fetch() {
    if (tricolor.getFetcher() == null) {
      throw new IllegalStateException("Fetcher can not be null.");
    }
    observable = observable.map(tricolor.getFetcher());
    return this;
  }

  // If there is not bitmap gotten from memory,
  // then decode the InputStream. Note the demands in options.
  // Put new bitmap decoded in memory cache right now.
  // And check whether we need to put the bitmap stored in disk. If so, put it.
  public RxRequestAssemblyLine decode() {
    if (tricolor.getImageDecoder() == null) {
      throw new IllegalStateException("Decoder can not be null.");
    }
    observable = observable.map(tricolor.getImageDecoder());
    return this;
  }

  // No matter decoded or get from memory, process the bitmap.
  public RxRequestAssemblyLine process() {
    if (tricolor.getImageProcessor() == null) {
      return this;
    }
    observable = observable.map(tricolor.getImageProcessor());
    return this;
  }

  public Subscription subscribe() {
    observable = observable.observeOn(AndroidSchedulers.mainThread());
    return observable.subscribe(
        new Action1<Intermediates>() {
          @Override
          public void call(Intermediates intermediates) {
            Intermediates.validIntermediates(intermediates);
            intermediates.getRawRequest().imageTarget.call(intermediates);
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            Logger.e(throwable);
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
