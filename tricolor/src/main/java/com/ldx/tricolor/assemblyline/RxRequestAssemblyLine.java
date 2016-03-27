package com.ldx.tricolor.assemblyline;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.api.Tricolor;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

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
    return this;
  }

  // Key generated
  public RxRequestAssemblyLine generateKey() {
    observable = observable.map(tricolor.getKeyGenerator());
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
    observable = observable.map(tricolor.getDiskCacheFunc());
    return this;
  }

  // If there is no bitmap gotten from memory and disk, then got from network
  public RxRequestAssemblyLine fetch() {
    observable = observable.map(tricolor.getFetcher());
    return this;
  }

  // If there is not bitmap gotten from memory,
  // then decode the InputStream. Note the demands in options.
  // Put new bitmap decoded in memory cache right now.
  // And check whether we need to put the bitmap stored in disk. If so, put it.
  public RxRequestAssemblyLine decode() {
    observable = observable.map(tricolor.getImageDecoder());
    return this;
  }

  // No matter decoded or get from memory, process the bitmap.
  public RxRequestAssemblyLine process() {
    observable = observable.map(tricolor.getImageProcessor());
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

  public interface KeyGenerator extends Func1<Intermediates, Intermediates> {
  }

  public static class BaseKeyGenerator implements KeyGenerator {

    @Override
    public Intermediates call(Intermediates intermediates) {
      if (intermediates == null) {
        throw new IllegalStateException("Intermediates can not be null.");
      }
      Request request = intermediates.getRawRequest();
      if (request == null) {
        throw new IllegalStateException("Request of intermediates can not be null.");
      }

      intermediates.setKey(request.uri + "-" + request.options.width + "x" + request.options.height);
      return intermediates;
    }
  }
}
