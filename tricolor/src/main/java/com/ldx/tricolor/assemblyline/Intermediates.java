package com.ldx.tricolor.assemblyline;

import android.graphics.Bitmap;

import com.ldx.tricolor.api.Request;
import com.ldx.tricolor.api.Tricolor;

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
  // I tried to code a reopenable inputStream, that means I need to wrap a imageFetcher in it... It's very
  // difficult to make it clear then. So I removed this design and will find a better way.
  // I don't want to use the MarkableInputStream(used in picasso), it will load all file in memory with byte[].
  private DataContainer dataContainer; // UN_DECODED

  // Generated key from request, used to cache the bitmap in disk and memory.
  private String key; // KEY_GENERATED

  // Records the original request and also the demands of it.
  // In details, Request contains the uri which means where it from , the imageTarget which means where it go
  // and the demands that demands of processing between previous two.
  private Request raw; // RAW

  // The state defines which step this request has been, it was only a raw or already been a bitmap.
  private State state = State.RAW;

  // The origin of bitmap or inputStream, which will control the behavior of Func below.
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

  public DataContainer getDataContainer() {
    return dataContainer;
  }

  public void setDataContainer(DataContainer dataContainer) {
    if (dataContainer == null) {
      throw new IllegalArgumentException("Stream can not be null.");
    }
    this.dataContainer = dataContainer;
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

  public static void validIntermediates(Intermediates intermediates) {

    if (intermediates == null) {
      throw new IllegalArgumentException("Intermediates can not be null");
    }

    if (intermediates.getRawRequest() == null) {
      throw new IllegalStateException("Request can not be null");
    }

    if (intermediates.getKey() == null || intermediates.getKey().isEmpty()) {
      throw new IllegalStateException("Key of request can not be null or empty.");
    }

  }

}
