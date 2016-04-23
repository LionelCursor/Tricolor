package com.ldx.tricolor.utils;

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


}
