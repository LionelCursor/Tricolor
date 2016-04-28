package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.assemblyline.DataContainer;
import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class BaseImageFetcher implements ImageFetcher {

  private List<FetchHandler> fetchHandlers;

  public BaseImageFetcher() {
    this(defaultHandlers());
  }

  private static List<FetchHandler> defaultHandlers() {
    List<FetchHandler> handlers = new ArrayList<>(5);
    handlers.add(new LocalDataHandler());
    handlers.add(new OkHttpFetcher());
    return handlers;
  }

  public BaseImageFetcher(List<FetchHandler> fetchHandlers) {
    if (fetchHandlers == null) {
      throw new IllegalArgumentException("Image imageFetcher can not work without any fetchHandler.");
    }
    this.fetchHandlers = fetchHandlers;
  }

  public List<FetchHandler> getFetchHandlers() {
    return fetchHandlers;
  }

  public DataContainer fetch(Uri uri, Tricolor tricolor) {
    FetchHandler fetchHandler = null;

    for (FetchHandler handler : fetchHandlers) {
      if (handler.canHandle(uri)) {
        fetchHandler = handler;
      }
    }

    if (fetchHandler == null) {
      throw new IllegalStateException("The Uri [" + uri.toString() + "] is not supported for " +
          "ImageFetcher.\n Maybe, you could extends FetchHandler and add it for ImageFetcher yourself.");
    }

    Logger.v("Uri is dispatched to FetchHandler [%s]", fetchHandler.getClass().getSimpleName());
    return fetchHandler.handle(uri, tricolor);
  }

  @Override
  public Intermediates call(Intermediates intermediates) {
    Intermediates.validIntermediates(intermediates);

    Logger.v("Fetcher starts to process the intermediates.");

    if (intermediates.getBitmap() != null) {
      Logger.v("Fetcher passed without a word, as bitmap provided.");
      return intermediates;
    }

    Intermediates memory = intermediates.getTricolor().getMemoryCacheFunc().call(intermediates);

    if (memory.getBitmap() != null) {
      return memory;
    }

    DataContainer data = fetch(intermediates.getRawRequest().uri, intermediates.getTricolor());

    Logger.v("DataContainer got.");
    intermediates.setDataContainer(data);
    return intermediates;
  }


}
