package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.assemblyline.DataContainer;
import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class ImageFetcherImpl implements ImageFetcher {

  public static final String HTTP = "http";

  public static final String HTTPS = "https";

  private List<FetchHandler> fetchHandlers;

  public ImageFetcherImpl() {
    this(defaultHandlers());
  }

  private static List<FetchHandler> defaultHandlers() {
    List<FetchHandler> handlers = new ArrayList<>(10);
    handlers.add(new LocalDataHandler());
    handlers.add(new OkHttpFetcher());
    return handlers;
  }

  public ImageFetcherImpl(List<FetchHandler> fetchHandlers) {
    if (fetchHandlers == null) {
      throw new IllegalArgumentException("Image fetcher can not work without any fetchHandler.");
    }
    this.fetchHandlers = fetchHandlers;
  }

  public List<FetchHandler> getFetchHandlers() {
    return fetchHandlers;
  }

  private void checkNull(InputStream is, Uri uri) {
    if (is == null) {
      throw new IllegalStateException("InputStream fetched is null from " + uri.toString());
    }
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
    return fetchHandler.handle(uri, tricolor);
  }

  @Override
  public Intermediates call(Intermediates intermediates) {
    if (intermediates == null) {
      throw new IllegalArgumentException("Intermediates can not be null");
    }

    if (intermediates.getRawRequest() == null) {
      throw new IllegalStateException("Request can not be null");
    }

    if (intermediates.getKey() == null || intermediates.getKey().isEmpty()) {
      throw new IllegalStateException("Key of request can not be null or empty.");
    }

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

    Logger.v("Data got.");
    intermediates.setDataContainer(data);
    return intermediates;
  }


}
