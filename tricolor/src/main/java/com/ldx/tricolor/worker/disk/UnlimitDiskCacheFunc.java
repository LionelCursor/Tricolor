package com.ldx.tricolor.worker.disk;

import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/27
 *
 * @author ldx
 */
public class UnlimitDiskCacheFunc extends BaseDiskCacheFunc {

  public static final String TMP_POSTFIX = ".tmp";

  public UnlimitDiskCacheFunc(File file) {
    super(file);
  }

  @Override
  public Uri get(String key) {
    File file = new File(cacheDir, key);
    if (!file.exists() || !file.isFile()) {
      return null;
    }
    return Uri.fromFile(file);
  }

  @Override
  public void put(String key, InputStream inputStream) throws IOException{
    File img = new File(cacheDir, key);
    File tmp = new File(cacheDir, key + TMP_POSTFIX);
    boolean loaded = false;
    try {
      byte[] buffer = new byte[8192];
      OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tmp), 8192);
    } finally {

    }

  }

  @Override
  public void remove(String key) throws IOException {
    File img = new File(cacheDir, key);
    if (!img.delete()) {
      throw new IOException("Image delete failed.");
    }
  }
}
