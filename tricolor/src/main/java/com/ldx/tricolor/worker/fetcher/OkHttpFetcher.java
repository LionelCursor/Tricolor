package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;

import java.io.IOException;
import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/27
 *
 * @author ldx
 */
public class OkHttpFetcher implements FetchHandler {

  @Override
  public boolean canHandle(Uri uri) {
    return false;
  }

  @Override
  public InputStream handle(Uri uri, Tricolor tricolor) throws IOException {
    return null;
  }
}
