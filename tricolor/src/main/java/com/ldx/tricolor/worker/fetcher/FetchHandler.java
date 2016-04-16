package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.assemblyline.DataContainer;

import java.io.IOException;
import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/6
 *
 * @author ldx
 */
public abstract class FetchHandler {
  public abstract boolean canHandle(Uri uri);

  public DataContainer handle(Uri uri, Tricolor tricolor) {
    return new DataContainerImpl(uri, tricolor, this);
  }

  public abstract InputStream open(Uri uri, Tricolor tricolor) throws IOException;

}
