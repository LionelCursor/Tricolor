package com.ldx.tricolor.worker.setup;

import android.graphics.Bitmap;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.ldx.tricolor.assemblyline.Intermediates;
import com.ldx.tricolor.utils.Logger;

import java.lang.ref.WeakReference;

/**
 * Decorate of ImageView in ImageTarget
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/20
 *
 * @author ldx
 */
public class SimpleImageTarget implements ImageTarget {
  private WeakReference<ImageView> reference;

  public SimpleImageTarget(ImageView view) {
    reference = new WeakReference<>(view);
  }

  /**
   * @return Maybe 0
   */
  @Override
  public int getRequireWidth() {
    ImageView view = reference.get();
    if (view == null) {
      return 0;
    }
    int width = view.getWidth();
    if (width <= 0) {
      view.getMaxWidth();
    }

    return width;
  }

  @Override
  public int getRequireHeight() {
    ImageView view = reference.get();
    if (view == null) {
      return 0;
    }

    int height = view.getHeight();
    if (height <= 0) {
      height = view.getMaxHeight();
    }
    return height;
  }

  @Override
  public void setPlaceholder(int resId) {
    ImageView view = reference.get();
    if (view == null) {
      return;
    }
    view.setImageResource(resId);
  }

  @Override
  public void setError(int resId) {
    ImageView view = reference.get();
    if (view == null) {
      return;
    }
    view.setImageResource(resId);
  }

  @Override
  public void call(Intermediates intermediates) {
    ImageView imageView = reference.get();
    if (imageView == null) {
      return;
    }
    Bitmap bitmap = intermediates.getBitmap();
    imageView.setAlpha(0.5f);
    imageView.setImageBitmap(bitmap);
    imageView.animate().setDuration(400).setInterpolator(new LinearInterpolator()).alpha(1f).start();
    Logger.v("Tricolor has set bitmap to image target successfully.");
  }
}
