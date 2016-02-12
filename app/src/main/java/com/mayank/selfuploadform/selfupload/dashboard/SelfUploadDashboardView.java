package com.mayank.selfuploadform.selfupload.dashboard;

public interface SelfUploadDashboardView {
  void setUsername(CharSequence name);

  void setProgress(int progress);

  void changeSubmitButtonStatus(boolean enabled);

  void showCards(SelfUploadDashboardCard... cards);

  void updateCard(int index);
}