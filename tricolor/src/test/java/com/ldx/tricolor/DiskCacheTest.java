package com.ldx.tricolor;

import android.net.Uri;

import com.ldx.tricolor.worker.disk.UnlimitedDiskCacheFunc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileInputStream;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Uri.class)
public class DiskCacheTest {

  UnlimitedDiskCacheFunc func = null;

  File cacheDir = new File("src/test/diskcache");

  @Before
  public void setup() throws Exception {
    PowerMockito.mockStatic(Uri.class);

    Uri uri = PowerMockito.mock(Uri.class);
    PowerMockito.when(Uri.class, "fromFile", Mockito.any(File.class)).thenReturn(uri);

    func = new UnlimitedDiskCacheFunc(cacheDir);
  }

  @Test
  public void getNull() {
    Assert.assertNull(func.get("mountains"));
  }

  @Test
  public void getNotNull() {
    Assert.assertNotNull(func.get("mountains.jpg"));
  }

  @Test
  public void putAndRemove() throws Exception {
    String key_for_test = "key-for-test";
    Assert.assertNull(func.get(key_for_test));
    func.put(key_for_test, new FileInputStream(new File(cacheDir, "mountains.jpg")));
    Assert.assertNotNull(func.get(key_for_test));
    Assert.assertEquals(
        new File(cacheDir, key_for_test).length(),
        new File(cacheDir, "mountains.jpg").length());
    func.remove(key_for_test);
    Assert.assertFalse(new File(cacheDir, key_for_test).exists());
  }

}