package com.ldx.tricolor.worker.setup;

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
    return view.getWidth();
  }

  @Override
  public int getRequireHeight() {
    ImageView view = reference.get();
    if (view == null) {
      return 0;
    }
    return view.getHeight();
  }

  @Override
  public void call(Intermediates intermediates) {
    ImageView imageView = reference.get();
    if (imageView == null) {
      return;
    }
    imageView.setImageBitmap(intermediates.getBitmap());
    Logger.v("Tricolor has set bitmap to image target successfully.");
  }
}
