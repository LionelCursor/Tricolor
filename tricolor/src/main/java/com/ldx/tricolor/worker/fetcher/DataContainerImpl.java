package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.assemblyline.DataContainer;
import com.ldx.tricolor.utils.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/16
 *
 * @author ldx
 */
public class DataContainerImpl implements DataContainer {

  private Uri uri;

  private Tricolor tricolor;

  private FetchHandler fetchHandler;

  public DataContainerImpl(Uri uri, Tricolor tricolor, FetchHandler handler) {
    this.uri = uri;
    this.tricolor = tricolor;
    this.fetchHandler = handler;
  }

  @Override
  public InputStream open() {
    InputStream is = null;
    try {
      is = fetchHandler.open(uri, tricolor);
    } catch (IOException e) {
      Logger.e("Failed when fetching the inputStream of Uri [" + uri.toString() + "]" +
          "\n Error : " + e);
    }
    return is;
  }

}
