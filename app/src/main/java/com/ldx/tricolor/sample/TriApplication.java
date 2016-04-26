package com.ldx.tricolor.sample;

import android.app.Application;

import com.ldx.tricolor.api.Tricolor;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/26
 *
 * @author ldx
 */
public class TriApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Tricolor.init(this);

    ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
    config.threadPriority(Thread.NORM_PRIORITY - 2);
    config.denyCacheImageMultipleSizesInMemory();
    config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
    config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
    config.tasksProcessingOrder(QueueProcessingType.LIFO);
    config.writeDebugLogs(); // Remove for release app

    ImageLoader.getInstance().init(config.build());
  }
}
