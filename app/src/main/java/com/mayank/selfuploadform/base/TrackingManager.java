package com.mayank.selfuploadform.base;

public class TrackingManager {
  public static void sendTrackingScreenName(String screenName) {
    Logger.logD(TrackingManager.class, screenName);
  }
}
