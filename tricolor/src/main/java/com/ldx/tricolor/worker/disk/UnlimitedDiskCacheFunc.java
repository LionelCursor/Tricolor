package com.ldx.tricolor.worker.disk;

import android.net.Uri;

import com.ldx.tricolor.utils.IOUtils;

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
public class UnlimitedDiskCacheFunc extends BaseDiskCacheFunc {
  //UnUs

  private static final String TMP_POSTFIX = ".tmp";

  private static final int BUFFER_BYTE_LENGTH = 8192;

  public UnlimitedDiskCacheFunc(File file) {
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
  public void put(String key, InputStream inputStream) throws IOException {
    File img = new File(cacheDir, key);
    File tmp = new File(cacheDir, key + TMP_POSTFIX);
    // These statement is different from UIL, which contains many try{...}finally{},
    // that I don't know why to use it. But I guess the stream.close may be the question.
    OutputStream outputStream = null;
    boolean loaded = false;
    try {
      outputStream = new BufferedOutputStream(new FileOutputStream(tmp), BUFFER_BYTE_LENGTH);
      loaded = IOUtils.copyStream(inputStream, outputStream, BUFFER_BYTE_LENGTH);
    } finally {
      if (outputStream != null) {
        IOUtils.closeSilently(outputStream);
      }
      IOUtils.closeSilently(inputStream);
    }
    if (loaded) {
      //noinspection ResultOfMethodCallIgnored
      tmp.renameTo(img);
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
