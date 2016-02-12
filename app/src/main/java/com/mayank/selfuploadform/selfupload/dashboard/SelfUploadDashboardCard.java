package com.mayank.selfuploadform.selfupload.dashboard;

public class SelfUploadDashboardCard {

  private CharSequence title;
  private CharSequence secondary;
  private int status;

  public CharSequence getTitle() {
    return title;
  }

  public void setTitle(CharSequence title) {
    this.title = title;
  }

  public CharSequence getSecondary() {
    return secondary;
  }

  public void setSecondary(CharSequence secondary) {
    this.secondary = secondary;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public static final class DashboardCardStatus {
    public static final int NEW = 0;
    public static final int INCOMPLETE = 1;
    public static final int COMPLETE = 2;
  }
}
