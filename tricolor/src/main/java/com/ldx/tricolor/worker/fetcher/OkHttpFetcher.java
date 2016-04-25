package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/27
 *
 * //TODO Now I integrate okhttp myself. It's not very good, I want users to demand which web module to use.
 *
 * @author ldx
 */
public class OkHttpFetcher extends FetchHandler {

  public static final String HTTP = "http";

  public static final String HTTPS = "https";

  private OkHttpClient mOkHttpClient;

  public OkHttpFetcher() {
    mOkHttpClient = new OkHttpClient();
  }

  @Override
  public boolean canHandle(Uri uri) {
    String scheme = uri.getScheme();
    return scheme.equals("http") || scheme.equals("https");
  }

  @Override
  public InputStream open(Uri uri, Tricolor tricolor) throws IOException {
    okhttp3.Request request = new Request.Builder().url(uri.toString()).build();
    okhttp3.Call call = mOkHttpClient.newCall(request);
    Response response = call.execute();
    return response.body().byteStream();
  }
}
