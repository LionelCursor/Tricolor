package com.ldx.tricolor.utils;

import android.util.Base64;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/19
 *
 * @author ldx
 */
public class Utils {

  public static void checkNotNull(Object... objects) {
    for (int i = 0; i < objects.length; i++) {
      if (objects[i] == null) {
        throw new IllegalStateException(String.format("The No.%d object is null", i));
      }
    }
  }

  public static String base64(String message) {
    return new String(Base64.encode(message.getBytes(), Base64.NO_WRAP));
  }

}
