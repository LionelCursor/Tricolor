package com.ldx.tricolor.worker.decoder;

import com.ldx.tricolor.assemblyline.Intermediates;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/27
 *
 * @author ldx
 */
public class BaseImageDecoder implements ImageDecoder {
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



    return null;
  }
}
