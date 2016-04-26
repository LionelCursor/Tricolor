package com.ldx.tricolor;

import android.graphics.Bitmap;

import com.ldx.tricolor.worker.memory.BaseMemoryCacheFunc;
import com.ldx.tricolor.worker.memory.LruMemoryCache;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/24
 *
 * @author ldx
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MemoryCacheTest {

  private BaseMemoryCacheFunc func;

  public void prepare() {
    func = new LruMemoryCache(4096);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInit() {
    func = new LruMemoryCache(0);
  }

  @Test
  public void testGet() {
    prepare();
    Assert.assertNull(func.get("asbdfbasdbf"));
  }


  @Test
  public void testPutAndGet() {
    prepare();
    Bitmap bitmap = Mockito.mock(Bitmap.class);
    Mockito.when(bitmap.getByteCount()).thenReturn(100);
    func.put("abc", bitmap);
    Assert.assertNotNull(bitmap);
    Assert.assertNotNull(func.get("abc"));
  }
}
