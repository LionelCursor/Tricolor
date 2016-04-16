package com.ldx.tricolor.utils;

import java.io.FilterInputStream;
import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/16
 *
 * @author ldx
 */
public class MarkableInputStream extends FilterInputStream{

  public MarkableInputStream(InputStream is) {
    super(is);
    if (is.markSupported()) {

    }
  }


}
