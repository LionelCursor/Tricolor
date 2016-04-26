package com.ldx.tricolor.sample;

import android.app.Application;
import android.content.res.Configuration;

import com.ldx.tricolor.api.Tricolor;

/**
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/4/26
 *
 * @author ldx
 */
public class TriApplication extends Application{
  @Override
  public void onCreate() {
    super.onCreate();
    Tricolor.init(this);
  }
}
