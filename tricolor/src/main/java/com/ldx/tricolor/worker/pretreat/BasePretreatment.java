package com.ldx.tricolor.worker.pretreat;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/26
 *
 * @author ldx
 */
public class BasePretreatment implements Pretreatment {

  @Override
  public Intermediates call(Intermediates intermediates) {
    Logger.time();
    // Check whether the intermediates is invalid.
    if (intermediates == null) {
      throw new IllegalStateException("Intermediates can not be null.");
    }
    //Check whether the has not the
    Request request = intermediates.getRawRequest();
    if (request == null) {
      throw new IllegalStateException("Request of intermediates can not be null.");
    }

    intermediates.setKey(request.uri + "-" + request.options.width + "x" + request.options.height);
    return intermediates;
  }
}
