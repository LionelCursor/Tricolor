package com.ldx.tricolor.worker.decoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.assemblyline.DataContainer;
import com.ldx.tricolor.assemblyline.Intermediates;

import java.io.InputStream;

import static com.ldx.tricolor.assemblyline.Intermediates.validIntermediates;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/27
 *
 * @author ldx
 */
public class BaseImageDecoder implements ImageDecoder {


  public BaseImageDecoder() {
  }

  @Override
  public Intermediates call(Intermediates intermediates) {

    validIntermediates(intermediates);

    if (intermediates.getBitmap() != null) {
      return intermediates;
    }

    intermediates = intermediates.getTricolor().getMemoryCacheFunc().call(intermediates);

    if (intermediates.getBitmap() != null) {
      return intermediates;
    }

    DataContainer container = intermediates.getDataContainer();

    if (intermediates.getDataContainer() == null) {
      throw new IllegalStateException("The data container passed down from imageFetcher is null.");
    }

    InputStream is = container.open();

    // TODO: can be better. the source width and height could be cached.
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeStream(is, null, options);

    is = container.open();

    options.inJustDecodeBounds = false;
    Request.RequestOptions requestOptions = intermediates.getRawRequest().options;
    options.inSampleSize = getSampleSize(options.outWidth, options.outHeight, requestOptions.width, requestOptions.height);
    Bitmap result = BitmapFactory.decodeStream(is, null, options);
    if (result == null) {
      throw new IllegalStateException("Bitmap can not be decoded.");
    }

    // I have not considered so many cases, especially when result == null, failed or something wrong.
    intermediates.setBitmap(result);

    intermediates.getTricolor().getMemoryCacheFunc().put(intermediates.getKey(), result);
    // TODO impl the DiskCache, I don't design for this.
    return intermediates;
  }

  public int getSampleSize(int outWidth, int outHeight, int desireWidth, int desireHeight) {
    //TODO impl sample size
    return 1;
  }

}
