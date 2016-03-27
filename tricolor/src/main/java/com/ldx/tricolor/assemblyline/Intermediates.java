package com.ldx.tricolor.assemblyline;

import android.graphics.Bitmap;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.api.Tricolor;

import java.io.InputStream;

/**
 * The Intermediates of the Processing from
 *
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/24
 *
 * @author ldx
 */
public class Intermediates {

  // The context of the image loading.
  private Tricolor tricolor;

  // The most important intermediate and also the result.
  private Bitmap bitmap; // DECODED

  // The Intermediates from disk or network.
  private InputStream stream; // UN_DECODED

  // Generated key from request, used to cache the bitmap in disk and memory.
  private String key; // KEY_GENERATED

  // Records the original request and also the demands of it.
  // In details, Request contains the uri which means where it from , the imageTarget which means where it go
  // and the demands that demands of processing between previous two.
  private Request raw; // RAW

  // The state defines which step this request has been, it was only a raw or already been a bitmap.
  private State state = State.RAW;

  // The origin of bitmap or InputStream, which will control the behavior of Func below.
  private Origin origin = null;

  public enum Origin {
    MEMORY,
    DISK,
    NETWORK,
  }

  public enum State {
    RAW,
    KEY_GENERATED,
    UN_DECODED,
    DECODED
  }

  public Intermediates(Request request, Tricolor tricolor) {
    if (request == null || tricolor == null) {
      throw new IllegalArgumentException("Request or tricolor can not be null.");
    }
    this.raw = request;
    this.state = State.RAW;
    this.tricolor = tricolor;
  }

  public Tricolor getTricolor() {
    return tricolor;
  }

  public Origin getOrigin() {
    return origin;
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

  public InputStream getStream() {
    return stream;
  }

  public String getKey() {
    return key;
  }

  public Request getRawRequest() {
    return raw;
  }

  public State getState() {
    return state;
  }

  public void setBitmap(Bitmap bitmap) {
    if (bitmap == null) {
      throw new IllegalArgumentException("Bitmap can not be null.");
    }
    this.bitmap = bitmap;
    this.state = State.DECODED;
  }

  public void setOrigin(Origin origin) {
    if (origin == null) {
      throw new IllegalArgumentException("Origin can not be null.");
    }

    this.origin = origin;
  }

  public void setStream(InputStream stream) {
    if (stream == null) {
      throw new IllegalArgumentException("Stream can not be null.");
    }
    this.stream = stream;
    this.state = State.UN_DECODED;
  }

  public void setKey(String key) {
    if (key == null || key.isEmpty()) {
      throw new IllegalArgumentException("Key can not be null or empty.");
    }
    this.key = key;
    this.state = State.KEY_GENERATED;
  }

  public void setRawRequest(Request request) {
    if (request == null) {
      throw new IllegalArgumentException("Request can not be null.");
    }
    this.raw = request;
    this.state = State.RAW;
  }

}
