package com.ldx.tricolor.assemblyline;

import java.io.InputStream;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/16
 *
 * Marking the inputStream without limit.
 *
 * MarkableInputStream is not the proper way to handle the inputStream,
 * because it need to many bytes to handle the reset when we set wrong readLimit.
 *
 * The way Picasso use is absolutely internship style.
 *
 * @author ldx
 */
public interface DataContainer {

  public InputStream open();

  public void finish();

}
