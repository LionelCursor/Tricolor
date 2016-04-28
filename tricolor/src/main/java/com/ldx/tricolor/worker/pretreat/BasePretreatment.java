package com.ldx.tricolor.worker.pretreat;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;
import com.ldx.tricolor.worker.setup.ImageTarget;

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

    // Process target size first
    processTargetSize(request);

    processPlaceHolder(request);
    // Then, create key
    intermediates.setKey(createKey(request));

    return intermediates;
  }

  public String createKey(Request request) {
    return request.uri + "-" + request.options.width + "x" + request.options.height;
  }

  public void processPlaceHolder(Request request) {
    ImageTarget imageTarget = request.imageTarget;
    if (request.options.setPlaceholder) {
      if (request.options.placeholderResId == 0) {
        throw new IllegalStateException("PlaceholderResId error.");
      }
      imageTarget.setPlaceholder(request.options.placeholderResId);
    }
  }

  /**
   * Default target size and Image target with scale type was all set in the request.
   * Consider them all and calculate a exact size is necessary.
   *
   * @param request Request with image target init
   */
  public boolean processTargetSize(Request request) {
    Logger.v("Calculating target size, target size was set is (%d, %d)", request.options.width, request.options.height);
    //If not set, default size is (0, 0)
    int width = request.imageTarget.getRequireWidth();
    int height = request.imageTarget.getRequireHeight();

    Logger.v("Required size of image target is (%d, %d)", width, height);
    if (width <= 0 || height <= 0) {
      request.options.width = 0;
      request.options.height = 0;
      return false;
    }

    request.options.width = width;
    request.options.height = height;
    return true;
  }
}
