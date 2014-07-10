package com.modou.demo.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.modou.demo.MainActivity;

/**
 * @author JasonZhao
 */
public class MessageCenter extends Handler {

  private static final String TAG = MessageCenter.class.getName();

  private static MessageCenter mesCen;

  public static void initInstance() {
    if (null == mesCen) {
      mesCen = new MessageCenter();
    }
  }

  public static MessageCenter getInstance() {
    return mesCen;
  }

  @Override
  public void handleMessage(Message msg) {
    switch (msg.what) {
      case CommonConsts.MSG_DISMISS_LOADING:
        msgDismissLoading(msg);
        break;
      case CommonConsts.MSG_NO_WIFI_ERROR:
        msgNoWifiError(msg);
        break;
      case CommonConsts.SHOULD_OPEN_JK_MODE:
        msgOpenJkMode(msg);
        break;
    }
  }

  private void msgOpenJkMode(Message msg) {
    if (null == msg.obj)
      return;
    final Context context = (Context) msg.obj;
    CommonUtil.showToast(context, "请在路由器上打开\"极客模式\"后重试", 5000);
  }

  private void msgDismissLoading(Message msg) {
    if (null == msg) return;
    Object obj = msg.obj;
    if (null == obj) return;
    if (obj instanceof MainActivity) {
      ((MainActivity) obj).getDisLoa().setVisibility(View.GONE);
    }
    if (obj instanceof Dialog) {
      ((Dialog) obj).dismiss();
    }
  }

  private void msgNoWifiError(Message msg) {
    if (null == msg.obj)
      return;
    Context context = (Context) msg.obj;
    CommonUtil.showNoWifiAlert(context);
  }

}
