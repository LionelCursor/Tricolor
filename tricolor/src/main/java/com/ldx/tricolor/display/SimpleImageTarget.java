package com.ldx.tricolor.display;

import android.widget.ImageView;

import com.ldx.tricolor.core.Intermediates;

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
    this.reference = new WeakReference<>(view);
  }

  @Override
  public void call(Intermediates intermediates) {
    ImageView imageView = reference.get();
    if (imageView == null) {
      return;
    }
    imageView.setImageBitmap(intermediates.getBitmap());
  }
}
