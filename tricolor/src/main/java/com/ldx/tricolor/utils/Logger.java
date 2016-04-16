package com.ldx.tricolor.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Log util.
 *
 * EMAIL : danxionglei@foxmail.com
 * DATE : 16/3/25
 *
 * @author ldx
 */
public class Logger {

  private static class HOLDER {
    public static final Logger logger = new Logger();
  }

  private static Logger getInstance() {
    return HOLDER.logger;
  }

  private static final int MAX_LOG_LENGTH = 4000;
  private static final int CALL_STACK_INDEX = 5;
  private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

  private final ThreadLocal<String> explicitTag = new ThreadLocal<>();

  private final ThreadLocal<Long> threadTime = new ThreadLocal<>();

  /**
   * Extract the tag which should be used for the message from the {@code element}. By default
   * this will use the class name without any anonymous class suffixes (e.g., {@code Foo$1}
   * becomes {@code Foo}).
   */
  private String createStackElementTag(StackTraceElement element) {
    String tag = element.getClassName();
    Matcher m = ANONYMOUS_CLASS.matcher(tag);
    if (m.find()) {
      tag = m.replaceAll("");
    }
    return tag.substring(tag.lastIndexOf('.') + 1);
  }

  private String getTag() {
    String tag = explicitTag.get();
    if (tag != null) {
      return tag;
    }

    // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
    // because Robolectric runs them on the JVM but on Android the elements are different.
    StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    if (stackTrace.length <= CALL_STACK_INDEX) {
      throw new IllegalStateException(
          "Synthetic stacktrace didn't have enough elements: are you using proguard?");
    }
    return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
  }

  private String wrapTime(String message) {
    Long startTime = threadTime.get();
    if (startTime == null) {
      return message;
    }
    return String.format("%s\n(%d ms used)", message, System.currentTimeMillis() - startTime);
  }

  private String format(String message, Object... objects) {
    return wrapTime(String.format(message, objects));
  }

  public static void tag(String tag) {
    Logger.getInstance().explicitTag.set(tag);
  }

  public static void evictTag() {
    getInstance().explicitTag.remove();
  }

  public static void time() {
    getInstance().threadTime.set(System.currentTimeMillis());
  }

  public static void evictTime() {
    getInstance().threadTime.remove();
  }

  public static void v(String message, Object... objects) {
    Logger logger = Logger.getInstance();
    Log.v(logger.getTag(), logger.format(message, objects));
  }

  public static void d(String message, Object... objects) {
    Logger logger = Logger.getInstance();
    Log.d(logger.getTag(), logger.format(message, objects));
  }

  public static void e(String message, Object... objects) {
    Logger logger = Logger.getInstance();
    Log.e(logger.getTag(), logger.format(message, objects));
  }

  public static void e(Throwable throwable) {
    Logger logger = Logger.getInstance();
    Log.e(logger.getTag(), Log.getStackTraceString(throwable));
  }

}
