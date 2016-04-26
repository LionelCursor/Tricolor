package com.ldx.tricolor.sample;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ldx.tricolor.api.Tricolor;
import com.ldx.tricolor.utils.Logger;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

  String url = "https://drscdn.500px.org/photo/388736/h%3D450/73918be4abcbcf3dbf98bcba20303a65";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ImageView mImageView = (ImageView) findViewById(R.id.image);
    Logger.time();
    Tricolor.getInstance().load(Uri.parse(url)).into(mImageView);
    Logger.e("mImageView2 inited.");
    ImageView mImageView2 = (ImageView) findViewById(R.id.image2);
    Picasso.with(this).load(Uri.parse(url)).into(mImageView2, new Callback() {
      @Override
      public void onSuccess() {
        Logger.v("Picasso has set bitmap to image target successfully.");
      }

      @Override
      public void onError() {

      }
    });
  }
}
