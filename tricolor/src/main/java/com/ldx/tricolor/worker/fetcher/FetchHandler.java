package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;

import java.io.IOException;
import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/6
 *
 * @author ldx
 */
public interface FetchHandler {
  public boolean canHandle(Uri uri);

  public InputStream handle(Uri uri, Tricolor tricolor) throws IOException;
}
