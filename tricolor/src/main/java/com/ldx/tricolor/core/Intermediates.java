package com.ldx.tricolor.core;

import android.graphics.Bitmap;

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

  Bitmap bitmap; // DECODED

  InputStream stream; // UN_DECODED

  String key; // KEY_GENERATED

  public Bitmap getBitmap() {
    return bitmap;
  }

  public InputStream getStream() {
    return stream;
  }

  public String getKey() {
    return key;
  }

  public Request getRaw() {
    return raw;
  }

  public State getState() {
    return state;
  }

  // Records the original request and also the demands of it.
  // In details, Request contains the uri which means where it from , the imageTarget which means where it go
  // and the demands that demands of processing between previous two.
  Request raw; // RAW

  State state = State.RAW;

  public Intermediates(Request request) {
    this.raw = request;
    this.state = State.RAW;
  }

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
    this.state = State.DECODED;
  }

  public void setStream(InputStream stream) {
    this.stream = stream;
    this.state = State.UN_DECODED;
  }

  public void setKey(String key) {
    this.key = key;
    this.state = State.KEY_GENERATED;
  }

  public void setRawRequest(Request request) {
    this.raw = request;
    this.state = State.RAW;
  }

  public enum State {
    RAW,
    KEY_GENERATED,
    UN_DECODED,
    DECODED
  }
}
