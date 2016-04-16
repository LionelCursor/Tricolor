package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/16
 *
 * @author ldx
 */
public class LocalDataHandler extends FetchHandler {

  public static final String ASSETS = "assets";

  public static final String DRAWABLE = "drawable";

  public static final String FILE = "file";

  @Override
  public boolean canHandle(Uri uri) {
    switch (uri.getScheme()) {
      case FILE:
      case DRAWABLE:
      case ASSETS:
        return true;
      default:
        return false;
    }
  }

  /**
   * Fetch the uri from local file such as File Assets and drawable.
   *
   * @param uri      The uri of request.
   * @param tricolor Context.
   * @return The result of this fetcher.
   * @throws IOException           Raise when file not found or something else occurred.
   * @throws IllegalStateException Raise when unsupported scheme passed in this handler.
   */
  @Override
  public InputStream open(Uri uri, Tricolor tricolor) throws IOException {
    InputStream is;
    switch (uri.getScheme()) {
      case FILE:
        is = fetchFromFile(uri, tricolor);
        break;
      case ASSETS:
        is = fetchFromAssets(uri, tricolor);
        break;
      case DRAWABLE:
        is = fetchFromResources(uri, tricolor);
        break;
      default:
        throw new IllegalStateException("Uri [" + uri.toString() + "] is not supported by LocalFileHandler.\n" +
            "You need to call canHandler first.");
    }
    return is;
  }

  public InputStream fetchFromResources(Uri uri, Tricolor tricolor) throws IOException {
    return tricolor.getContext().getResources().openRawResource(Integer.parseInt(uri.getPath()));
  }

  public InputStream fetchFromAssets(Uri uri, Tricolor tricolor) throws IOException {
    return tricolor.getContext().getAssets().open(uri.getPath());
  }

  public InputStream fetchFromFile(Uri uri, Tricolor tricolor) throws IOException {
    return new FileInputStream(uri.getPath());
  }

}
