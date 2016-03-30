package com.ldx.tricolor;

import android.test.InstrumentationTestCase;

import com.ldx.tricolor.worker.disk.UnlimitedDiskCacheFunc;

import java.io.File;


/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/29
 *
 * @author ldx
 */
public class DiskCacheTest extends InstrumentationTestCase {


  UnlimitedDiskCacheFunc func = null;

  File cacheDir = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    cacheDir = getInstrumentation().getTargetContext().getCacheDir();
    func = new UnlimitedDiskCacheFunc(cacheDir);
  }

}
