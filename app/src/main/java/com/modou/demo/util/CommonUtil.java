package com.modou.demo.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by JasonZhao on 7/9/14.
 */
public class CommonUtil {

  public static String getGateway(Context context) {
    WifiManager wm = (WifiManager) context
        .getSystemService(Context.WIFI_SERVICE);
    DhcpInfo dhcpInfo = wm.getDhcpInfo();
    return getIpAddress(dhcpInfo.gateway);
  }

  public static String getIpAddress(int ip) {
    String ipString = "";
    if (ip != 0) {
      ipString = ((ip & 0xff) + "." + (ip >> 8 & 0xff) + "."
          + (ip >> 16 & 0xff) + "." + (ip >> 24 & 0xff));
    }
    return ipString;
  }

  public static boolean isWifiConnected(Context context) {
    if (null == context)
      return false;
    ConnectivityManager connectivity = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null) {
      NetworkInfo[] info = connectivity.getAllNetworkInfo();
      if (info != null) {
        for (int i = 0; i < info.length; i++) {
          if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static String ipReverse(String ip) {
    String[] parts = ip.split("\\.");
    StringBuilder sb = new StringBuilder();
    for (int i = parts.length - 1; i >= 0; i--) {
      sb.append(parts[i]).append(".");
    }
    if (sb.length() > 0) {
      sb.setLength(sb.length() - 1);
    }
    return sb.toString();
  }

  public static void showToast(Context context, int msg, int... others) {
    showToast(context, context.getString(msg), others);
  }

  public static void showToast(Context context, String msg, int... others) {
    Toast toast = null;
    int duration = Toast.LENGTH_SHORT;
    if (others.length > 0) {
      duration += others[0];
    }
    toast = Toast.makeText(context, msg, duration);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
  }

  public static void sendDismissDialogMessage(Dialog dialog) {
    Message msg = new Message();
    msg.what = CommonConsts.MSG_DISMISS_LOADING;
    msg.obj = dialog;
    MessageCenter.getInstance().sendMessage(msg);
  }

  public static Dialog showLoading(Context context) {
    return ProgressDialog.show(context, "ModouDemo", "Loading...");
  }

  public static void setRouterIp(Context context, String routerIp) {
    SharedPreferences sp = context.getSharedPreferences(
        CommonConsts.CACHE_LUYOUBAO, 0);
    sp.edit().putString(CommonConsts.CACHE_ROUTER_IP, routerIp).commit();
  }

  public static String getRouterIp(Context context) {
    SharedPreferences sp = context.getSharedPreferences(
        CommonConsts.CACHE_LUYOUBAO, 0);
    return sp.getString(CommonConsts.CACHE_ROUTER_IP, "");
  }

  public static void showNoWifiAlert(final Context context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("ModouDemo");
    builder.setMessage("请连接wifi后重试");
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        showWifiSettig(context);
      }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  public static void showWifiSettig(Context context) {
    context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
  }

  public static void setCookie(Context context, String cookie) {
    SharedPreferences sp = context.getSharedPreferences(
        CommonConsts.CACHE_LUYOUBAO, 0);
    sp.edit().putString(CommonConsts.CACHE_COOKIE, cookie).commit();
  }

  public static String getCookie(Context context) {
    SharedPreferences sp = context.getSharedPreferences(
        CommonConsts.CACHE_LUYOUBAO, 0);
    return sp.getString(CommonConsts.CACHE_COOKIE, "");
  }
}
