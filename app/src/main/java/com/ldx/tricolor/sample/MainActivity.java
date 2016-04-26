package com.ldx.tricolor.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ldx.tricolor.api.DefaultConfig;
import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.utils.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

  String url = "https://drscdn.500px.org/photo/388736/h%3D450/73918be4abcbcf3dbf98bcba20303a65";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Logger.time();
    Tricolor.getInstance().load(url).into((ImageView) findViewById(R.id.image));

    Picasso.with(this).setLoggingEnabled(true);
    Picasso.with(this).load(url).into((ImageView) findViewById(R.id.image2), new Callback() {
      @Override
      public void onSuccess() {
        Logger.v("Picasso has set bitmap to image target successfully.");
      }

      @Override
      public void onError() {

      }
    });

    ImageLoader.getInstance().displayImage(url, (ImageView) findViewById(R.id.image3));
    Glide.with(this).load(url).asBitmap().into((ImageView) findViewById(R.id.image4));
  }
}
