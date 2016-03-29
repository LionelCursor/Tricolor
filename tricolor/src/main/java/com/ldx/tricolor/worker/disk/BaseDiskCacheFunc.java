package com.ldx.tricolor.worker.disk;

import android.graphics.Bitmap;
import android.net.Uri;

import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public abstract class BaseDiskCacheFunc implements DiskCacheFunc {

  File cacheDir;

  public BaseDiskCacheFunc(File cacheDir) {
    if (cacheDir == null) {
      throw new IllegalArgumentException("Cache directory can not be null.");
    }

    if (!cacheDir.exists() || !cacheDir.isDirectory() || !cacheDir.mkdirs()) {
      throw new IllegalStateException("Cache directory mkdirs failed");
    }

    this.cacheDir = cacheDir;
  }

  public abstract Uri get(String key);

  public abstract void put(String key, InputStream inputStream) throws IOException;

  public abstract void remove(String key) throws IOException;

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

    Logger.v("Disk cache starts to process intermediates.");

    if (intermediates.getBitmap() != null) {
      Logger.v("Disk cache passed without a word, as bitmap hit.");
      return intermediates;
    }

    Uri uri = get(intermediates.getKey());

    if (uri == null) {
      Logger.v("Disk cache miss.");
      return intermediates;
    }

    Logger.v("Disk cache hit.");
    intermediates.setOrigin(Intermediates.Origin.DISK);
    Logger.v("Uri of raw request has been changed to %s", uri.getPath());
    intermediates.getRawRequest().uri = uri;
    return intermediates;
  }

}
