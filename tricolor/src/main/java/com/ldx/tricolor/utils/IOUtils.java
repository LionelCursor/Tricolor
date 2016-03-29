package com.ldx.tricolor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/28
 *
 * @author ldx
 */
public class IOUtils {

  public static boolean copyStream(InputStream is, OutputStream os, int bufferSize) throws IOException {
    byte[] buffer = new byte[bufferSize];
    try {
      int len = is.read(buffer);
      while (len != -1) {
        os.write(buffer, 0, len);
        len = is.read(buffer);
      }
    } finally {
      is.close();
      os.close();
    }
    return true;
  }
}
