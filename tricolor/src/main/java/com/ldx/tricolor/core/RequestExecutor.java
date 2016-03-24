package com.ldx.tricolor.core;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public interface RequestExecutor {

  /**
   * how
   * @param request
   */
  public void execute(Request request, Tricolor tricolor);

}
