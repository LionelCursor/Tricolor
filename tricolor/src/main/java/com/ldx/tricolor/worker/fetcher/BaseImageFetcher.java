package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;

import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public abstract class BaseImageFetcher implements ImageFetcher {

  public InputStream fetchFromResources(Uri uri, Tricolor tricolor) {
    return null;
  }

  public InputStream fetchFromAssets(Uri uri, Tricolor tricolor) {
    return null;
  }

  public InputStream fetchFromFile(Uri uri, Tricolor tricolor) {
    return null;
  }

  public abstract InputStream fetchFromNetwork(Uri uri, Tricolor tricolor);

  public InputStream fetch(Uri uri, Tricolor tricolor) {
    switch (uri.getScheme()) {

    }

    return null;
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

    InputStream stream = fetch(intermediates.getRawRequest().uri, intermediates.getTricolor());

    if (stream == null) {
      throw new IllegalStateException("Fetch error.");
    }

    Logger.v("InputStream got.");
    intermediates.setStream(stream);
    return intermediates;
  }
}
