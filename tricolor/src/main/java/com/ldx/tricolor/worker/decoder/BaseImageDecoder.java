package com.ldx.tricolor.worker.decoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.assemblyline.DataContainer;
import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;

import java.io.InputStream;

import static com.ldx.tricolor.assemblyline.Intermediates.checkCompleted;
import static com.ldx.tricolor.assemblyline.Intermediates.checkMemory;
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

    if (checkCompleted(intermediates)) {
      return intermediates;
    }

    Logger.v("Decoder starts to process the intermediates.");

    if (checkMemory(intermediates)) {
      return intermediates;
    }

    DataContainer container = intermediates.getDataContainer();

    if (intermediates.getDataContainer() == null) {
      throw new IllegalStateException("The data container passed down from imageFetcher is null.");
    }

    Logger.v("Decoder starts to open data stream.");
    InputStream is = container.open();
    Logger.v("Data stream got.");

    // TODO: can be better. The source width and height could be cached. not decode for options every time.
    BitmapFactory.Options options = decodeForOptions(is);

    container.finish();

    Logger.v("Decoder starts to open data stream again.");
    is = container.open();
    Logger.v("Data stream got.");

    //TODO should consider the bitmap exif
    Bitmap result = decodeForBitmap(is, options, intermediates.getRawRequest().options);
    container.finish();

    if (result == null) {
      throw new IllegalStateException("Bitmap can not be decoded.");
    }

    // I have not considered so many cases, especially when result == null, failed or something wrong.
    intermediates.setBitmap(result);

    intermediates.getTricolor().getMemoryCacheFunc().put(intermediates.getKey(), result);
    // TODO impl the DiskCache, I don't design for this.
    return intermediates;
  }

  public Bitmap decodeForBitmap(InputStream is, BitmapFactory.Options rawOptions, Request.RequestOptions requestOptions) {
    rawOptions.inJustDecodeBounds = false;
    rawOptions.inSampleSize =
        getSampleSize(
            rawOptions.outWidth, rawOptions.outHeight,
            requestOptions.width, requestOptions.height,
            requestOptions);
    return BitmapFactory.decodeStream(is, null, rawOptions);
  }

  public BitmapFactory.Options decodeForOptions(InputStream is) {
    BitmapFactory.Options options = new BitmapFactory.Options();// TODO cache the Options.
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeStream(is, null, options);
    return options;
  }

  public int getSampleSize(int outWidth, int outHeight, int desireWidth, int desireHeight, Request.RequestOptions options) {
    Logger.v("Calculated the sample size, " + "outWidth = [" + outWidth + "], outHeight = [" + outHeight + "], desireWidth = [" + desireWidth + "], desireHeight = [" + desireHeight + "], options = [" + options + "]");
    //TODO desireWidth and desireHeight don't doing anything with ImageTarget, which is not correct.
    int sampleSize = 1;
    if (outHeight <= desireHeight && outWidth <= desireWidth) {
      Logger.v("Sample size calculated as " + sampleSize);
      return sampleSize;
    }

    if (!options.centerInside && !options.centerCrop) {
      Logger.v("Sample size calculated as" + sampleSize);
      return sampleSize;
    }

    // Now centerInside or centerCrop is true;

    int heightRatio = outHeight / desireHeight;
    int widthRatio = outWidth / desireWidth;
    // Priority to centerInside.
    if (options.centerInside) {
      sampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
    } else {
      sampleSize = Math.max(heightRatio > widthRatio ? widthRatio : heightRatio, 1);
    }

    Logger.v("Sample size calculated as" + sampleSize);
    return sampleSize;
  }

}
