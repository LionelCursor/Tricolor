package com.ldx.tricolor.assemblyline;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.api.Tricolor;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public interface RequestAssemblyLine {

  /**
   * Setup tricolor in assembly line, which contains all the workers in the assembly line.
   * @param tricolor global worker container
   */
  public void with(Tricolor tricolor);

  /**
   * Execute the request.
   * @param request all the detail message of a request of bitmap.
   */
  public void execute(Request request);

}
