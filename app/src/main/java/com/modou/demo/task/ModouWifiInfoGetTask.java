package com.modou.demo.task;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import com.modou.demo.ContentActivity;
import com.modou.demo.model.WifiInfo2G;
import com.modou.demo.model.WifiInfo5G;
import com.modou.demo.util.CommonConsts;
import com.modou.demo.util.CommonUtil;
import com.modou.demo.util.InternetUtil;
import com.modou.demo.util.LogUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JasonZhao
 */
public class ModouWifiInfoGetTask extends AsyncTask<Void, Void, Map<String, Object>> {

  private static final String TAG = "MatrixWifiInfoGetTask";

  private Context context;

  private Dialog dialog;

  public ModouWifiInfoGetTask(Context context) {
    this.context = context;
  }

  @Override
  protected void onPreExecute() {
    dialog = CommonUtil.showLoading(context);
    super.onPreExecute();
  }

  @Override
  protected Map<String, Object> doInBackground(Void... arg0) {
    Map<String, Object> data = null;
    String res = null;
    String url = String.format(CommonConsts.URL_MATRIX_WIFI, CommonUtil.getRouterIp(context));
    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("Cookie", CommonUtil.getCookie(context));
    try {
      res = InternetUtil.doMatrixWifi(context, url, headerMap);
      if (null != res) {
        data = new HashMap<String, Object>();
        JSONObject jo = new JSONObject(res);
        int code = 0;
        if (jo.has("code")) {
          code = jo.getInt("code");
        }
        if (jo.has("2g")) {
          data.put("2g", analysics2G(jo.getJSONObject("2g")));
        }
        if (jo.has("5g")) {
          data.put("5g", analysics5G(jo.getJSONObject("5g")));
        }
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    } finally {
      CommonUtil.sendDismissDialogMessage(dialog);
    }
    LogUtil.debug(TAG, "wifi info map:" + data);
    return data;
  }

  private Object analysics2G(JSONObject obj) throws JSONException {
    WifiInfo2G info2G = new WifiInfo2G();
    if (null != obj) {
      if (obj.has("encrypt")) {
        info2G.setEncrypt(obj.getString("encrypt"));
      }
      if (obj.has("wmm_enabled")) {
        info2G.setWmm_enabled(obj.getBoolean("wmm_enabled"));
      }
      if (obj.has("ssid")) {
        info2G.setSsid(obj.getString("ssid"));
      }
      if (obj.has("power")) {
        info2G.setPower(obj.getInt("power"));
      }
      if (obj.has("beacon")) {
        info2G.setBeacon(obj.getInt("beacon"));
      }
      if (obj.has("shortgi_enabled")) {
        info2G.setShortgi_enabled(obj.getBoolean("shortgi_enabled"));
      }
      if (obj.has("broadcastssid")) {
        info2G.setBroadcastssid(obj.getBoolean("broadcastssid"));
      }
      if (obj.has("ap_enabled")) {
        info2G.setAp_enabled(obj.getBoolean("ap_enabled"));
      }
      if (obj.has("password")) {
        info2G.setPassword(obj.getString("password"));
      }
      if (obj.has("mac")) {
        info2G.setMac(obj.getString("mac"));
      }
      if (obj.has("security_mode")) {
        info2G.setSecurity_mode(obj.getString("security_mode"));
      }
      if (obj.has("channel")) {
        info2G.setChannel(obj.getInt("channel"));
      }
      if (obj.has("apsd_enabled")) {
        info2G.setApsd_enabled(obj.getBoolean("apsd_enabled"));
      }
      if (obj.has("band_width_mode")) {
        info2G.setBand_width_mode(obj.getInt("band_width_mode"));
      }
      if (obj.has("net_type")) {
        info2G.setNet_type(obj.getInt("net_type"));
      }
      if (obj.has("enabled")) {
        info2G.setEnabled(obj.getBoolean("enabled"));
      }
    }
    return info2G;
  }

  private Object analysics5G(JSONObject obj) throws JSONException {
    WifiInfo5G info5G = new WifiInfo5G();
    if (null != obj) {
      if (obj.has("same_as_2g")) {
        info5G.setSame_as_2g(obj.getBoolean("same_as_2g"));
      }
      if (obj.has("encrypt")) {
        info5G.setEncrypt(obj.getString("encrypt"));
      }
      if (obj.has("wmm_enabled")) {
        info5G.setWmm_enabled(obj.getBoolean("wmm_enabled"));
      }
      if (obj.has("ssid")) {
        info5G.setSsid(obj.getString("ssid"));
      }
      if (obj.has("power")) {
        info5G.setPower(obj.getInt("power"));
      }
      if (obj.has("beacon")) {
        info5G.setBeacon(obj.getInt("beacon"));
      }
      if (obj.has("shortgi_enabled")) {
        info5G.setShortgi_enabled(obj.getBoolean("shortgi_enabled"));
      }
      if (obj.has("broadcastssid")) {
        info5G.setBroadcastssid(obj.getBoolean("broadcastssid"));
      }
      if (obj.has("ap_enabled")) {
        info5G.setAp_enabled(obj.getBoolean("ap_enabled"));
      }
      if (obj.has("password")) {
        info5G.setPassword(obj.getString("password"));
      }
      if (obj.has("mac")) {
        info5G.setMac(obj.getString("mac"));
      }
      if (obj.has("security_mode")) {
        info5G.setSecurity_mode(obj.getString("security_mode"));
      }
      if (obj.has("channel")) {
        info5G.setChannel(obj.getInt("channel"));
      }
      if (obj.has("apsd_enabled")) {
        info5G.setApsd_enabled(obj.getBoolean("apsd_enabled"));
      }
      if (obj.has("band_width_mode")) {
        info5G.setBand_width_mode(obj.getInt("band_width_mode"));
      }
      if (obj.has("net_type")) {
        info5G.setNet_type(obj.getInt("net_type"));
      }
      if (obj.has("enabled")) {
        info5G.setEnabled(obj.getBoolean("enabled"));
      }
    }
    return info5G;
  }

  @Override
  protected void onPostExecute(Map<String, Object> result) {
    if (null == result) return;
    WifiInfo2G info2G = (WifiInfo2G) result.get("2g");
    WifiInfo5G info5G = (WifiInfo5G) result.get("5g");
    if (context instanceof ContentActivity) {
      ((ContentActivity) context).showWifiInfo(info2G, info5G);
    }
    super.onPostExecute(result);
  }
}
