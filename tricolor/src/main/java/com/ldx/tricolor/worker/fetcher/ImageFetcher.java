package com.ldx.tricolor.worker.fetcher;

import android.net.Uri;

import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.assemblyline.Intermediates;

import java.io.InputStream;

import rx.functions.Func1;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public interface ImageFetcher extends Func1<Intermediates, Intermediates>{

  public abstract InputStream fetch(Uri uri, Tricolor tricolor);

  @Override
  Intermediates call(Intermediates intermediates);
}
