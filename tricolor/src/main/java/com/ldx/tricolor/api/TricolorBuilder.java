package com.ldx.tricolor.api;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/17
 *
 * @author ldx
 */

import android.content.Context;

import com.ldx.tricolor.assemblyline.RequestAssemblyLine;
import com.ldx.tricolor.worker.KeyGenerator;
import com.ldx.tricolor.worker.decoder.ImageDecoder;
import com.ldx.tricolor.worker.disk.DiskCacheFunc;
import com.ldx.tricolor.worker.fetcher.ImageFetcher;
import com.ldx.tricolor.worker.memory.MemoryCacheFunc;
import com.ldx.tricolor.worker.processor.ImageProcessor;

import java.io.File;

/**
 * Builder for Tricolor
 */
public final class TricolorBuilder {
  protected Context context;
  protected boolean isLoggingEnabled = false;
  protected Request.RequestOptions defaultRequestOptions;
  protected KeyGenerator keyGenerator;
  protected RequestAssemblyLine requestAssemblyLine;
  protected ImageDecoder imageDecoder;
  protected ImageProcessor imageProcessor;
  protected ImageFetcher imageFetcher;

  protected boolean memoryCacheEnable = true;
  protected int maxSize;
  protected MemoryCacheFunc memoryCacheFunc;

  protected boolean diskCacheEnable = true;
  protected File diskCacheDir;
  protected DiskCacheFunc diskCacheFunc;


  public TricolorBuilder(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("Context can not be null.");
    }
    this.context = context.getApplicationContext();
  }

  public TricolorBuilder() {
  }

  public Tricolor build() {

    if (context == null) {
      throw new IllegalArgumentException("Context can not be null.");
    }

    if (requestAssemblyLine == null) {
      requestAssemblyLine = DefaultConfig.defaultRequestExecutor();
    }

    if (defaultRequestOptions == null) {
      defaultRequestOptions = DefaultConfig.defaultRequestOptions(context);
    }

    if (memoryCacheEnable) {
      if (maxSize == 0) {
        maxSize = (int) Runtime.getRuntime().maxMemory() / 8; // 1 / 8 of main memory.
      }

      if (memoryCacheFunc == null) {
        memoryCacheFunc = DefaultConfig.defaultMemoryCacheFunc(maxSize);
      }
    }

    if (diskCacheEnable) {

      if (diskCacheDir == null) {
        diskCacheDir = context.getCacheDir();
      }

      if (diskCacheFunc == null) {
        diskCacheFunc = DefaultConfig.defaultDiskCacheManager(diskCacheDir);
      }

    }

    if (keyGenerator == null) {
      keyGenerator = DefaultConfig.defaultKeyGenerator();
    }

    if (imageDecoder == null) {
      imageDecoder = DefaultConfig.defaultDecoder();
    }

    if (imageFetcher == null) {
      imageFetcher = DefaultConfig.defaultFetcher();
    }

    return new Tricolor(this);
  }

  public TricolorBuilder maxSize(int maxSize) {
    if (maxSize < 0) {
      throw new IllegalStateException("maxSize of memory cache must not below zero.");
    }
    this.maxSize = maxSize;
    return this;
  }

  public TricolorBuilder isLoggingEnabled(boolean val) {
    isLoggingEnabled = val;
    return this;
  }

  public TricolorBuilder defaultRequestOptions(Request.RequestOptions val) {
    defaultRequestOptions = val;
    return this;
  }

  public TricolorBuilder keyGenerator(KeyGenerator val) {
    keyGenerator = val;
    return this;
  }

  public TricolorBuilder requestAssemblyLine(RequestAssemblyLine val) {
    requestAssemblyLine = val;
    return this;
  }

  public TricolorBuilder context(Context val) {
    context = val;
    return this;
  }

  public TricolorBuilder memoryCacheFunc(MemoryCacheFunc val) {
    if (!memoryCacheEnable) {
      throw new IllegalStateException("Memory cache is disabled.");
    }
    memoryCacheFunc = val;
    return this;
  }

  public TricolorBuilder diskCacheFunc(DiskCacheFunc val) {
    diskCacheFunc = val;
    return this;
  }

  public TricolorBuilder imageDecoder(ImageDecoder val) {
    imageDecoder = val;
    return this;
  }

  public TricolorBuilder imageProcessor(ImageProcessor val) {
    imageProcessor = val;
    return this;
  }

  public TricolorBuilder fetcher(ImageFetcher val) {
    imageFetcher = val;
    return this;
  }

  public TricolorBuilder diskCacheManager(DiskCacheFunc val) {
    if (!diskCacheEnable) {
      throw new IllegalStateException("Disk cache is disabled.");
    }
    diskCacheFunc = val;
    return this;
  }

  public TricolorBuilder memoryCacheManager(MemoryCacheFunc val) {
    memoryCacheFunc = val;
    return this;
  }
}
