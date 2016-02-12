package com.mayank.selfuploadform.base;

import android.util.Log;

import java.util.Locale;

public class Logger {
  public static final boolean DEBUG_FLAG = true;

  /**
   * Logs formatted string, using the supplied format and arguments
   *
   * @param caller the caller object used for tag finding
   * @param text   the format string (see {@link java.util.Formatter#format})
   * @param args   the list of arguments passed to the formatter. If there are
   *               more arguments than required by {@code format},
   *               additional arguments are ignored.
   * @throws NullPointerException             if {@code format == null}
   * @throws java.util.IllegalFormatException if the format is invalid.
   * @since 1.5
   */
  public static void logD(Object caller, String text, Object args) {
    if (DEBUG_FLAG) {
      String tag = getTag(caller);
      Log.d(tag, String.format(Locale.ENGLISH, text, args));
    }
  }

  /**
   * Logs string, using the supplied text and caller
   *
   * @param caller the caller object used for tag finding
   * @param text   the format string (see {@link java.util.Formatter#format})
   */
  public static void logD(Object caller, String text) {
    if (DEBUG_FLAG) {
      String tag = getTag(caller);
      Log.d(tag, text);
    }
  }

  private static String getTag(Object caller) {
    if (caller instanceof Class) {
      return ((Class) caller).getSimpleName();
    } else {
      return caller.getClass().getSimpleName();
    }
  }
}