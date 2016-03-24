package com.ldx.tricolor.core;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public interface RequestExecutor {

  /**
   * Execute the request.
   * @param request all the detail message of a request of bitmap.
   */
  public void execute(Request request);

}
