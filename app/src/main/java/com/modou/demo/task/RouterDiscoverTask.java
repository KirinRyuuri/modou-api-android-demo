package com.modou.demo.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.modou.demo.MainActivity;
import com.modou.demo.util.CommonConsts;
import com.modou.demo.util.CommonUtil;
import com.modou.demo.util.MessageCenter;
import com.util.dns.DnsUtil;

/**
 * @author JasonZhao
 */
public class RouterDiscoverTask extends
    AsyncTask<Void, Void, Boolean> {

  private static final String MODOU_ADDRESS = "matrix.modouwifi.com";

  static {
    System.loadLibrary("dnsutil");
  }

  private static final String TAG = "CurrentSystemWifiInfoGetTask";

  private Context context;

  public RouterDiscoverTask(Context context) {
    this.context = context;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    if (context instanceof MainActivity) {
      ((MainActivity) context).getDisLoa().setVisibility(View.VISIBLE);
    }
  }

  @Override
  protected Boolean doInBackground(Void... arg0) {
    try {
      String gateway = CommonUtil.getGateway(context);
      CommonUtil.setRouterIp(context, gateway);
      if (TextUtils.isEmpty(gateway)) {
        return false;
      }
      DnsUtil du = new DnsUtil();
      String res = du.getPtrRecord(String.format("%s.in-addr.arpa", CommonUtil.ipReverse(gateway)), gateway);
      if (null == res || res.indexOf(MODOU_ADDRESS) < 0) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      Message msg = new Message();
      msg.what = CommonConsts.MSG_DISMISS_LOADING;
      msg.obj = context;
      MessageCenter.getInstance().sendMessage(msg);
    }
    return true;
  }

  @Override
  protected void onPostExecute(Boolean result) {
    if (context instanceof MainActivity) {
      ((MainActivity) context).updateDiscoveryResult(result);
    }
    super.onPostExecute(result);
  }
}
