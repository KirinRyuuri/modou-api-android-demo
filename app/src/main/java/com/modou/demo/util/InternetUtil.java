package com.modou.demo.util;

import android.content.Context;
import android.os.Message;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author JasonZhao
 */
public class InternetUtil {

  private static final String TAG = "InternetUtil";

  public static boolean networkCheck(Context context) {
    boolean flag = true;
    if (null == context)
      return flag;

    flag &= CommonUtil.isWifiConnected(context);
    if (!flag) {
      Message msg = new Message();
      msg.what = CommonConsts.MSG_NO_WIFI_ERROR;
      msg.obj = context;
      MessageCenter.getInstance().sendMessage(msg);
      return flag;
    }
    return flag;
  }


  private static HttpResponse doMatrixPost(Context context, String url,
      Map<String, String> headerMap, JSONObject data)
      throws ClientProtocolException, IOException {
    if (!networkCheck(context))
      return null;
    LogUtil.debug(TAG, "request post url:" + url);
    HttpClient httpClient = getHttpClient();
    HttpPost httpPost = new HttpPost(url);
    if (null != headerMap) {
      Set<String> keySet = headerMap.keySet();
      for (String key : keySet) {
        httpPost.addHeader(key, headerMap.get(key));
      }
    }
    if (null != data) {
      httpPost.setEntity(new StringEntity(data.toString(), "utf-8"));
    }
    return httpClient.execute(httpPost);
  }

  public static String doMatrixLogin(Context context, String url,
      JSONObject data) throws ClientProtocolException, IOException {
    LogUtil.debug(TAG, "request post url:" + url);
    String res = null;
    HttpResponse response = doMatrixPost(context, url, null, data);
    if (response.getStatusLine().getStatusCode() == 200) {
      Header[] headers = response.getHeaders("Set-Cookie");
      if (null != headers && headers.length > 0) {
        CommonUtil.setCookie(context, headers[0].getValue());
      }
      res = EntityUtils.toString(response.getEntity(), "utf-8");
    }
    return res;
  }

  public static String doMatrixWifi(Context context, String url,
      Map<String, String> headerMap) throws ClientProtocolException,
      IOException {
    String res = null;
    res = doGet(context, url, headerMap, null);
    LogUtil.debug(TAG, "matrix wifi response:" + res);
    return res;
  }

  public static String doGet(Context context, String url,
      Map<String, String> headerMap, Map<String, String> params)
      throws ClientProtocolException, IOException {
    if (!networkCheck(context))
      return null;
    String res = null;
    HttpClient httpClient = getHttpClient();
    url = configGetUrl(url, params);
    LogUtil.debug(TAG, "request get url:" + url); 
    HttpGet httpGet = new HttpGet(url);
    httpGet = configGetHeader(headerMap, httpGet);
    HttpResponse response = httpClient.execute(httpGet);
    LogUtil.debug(TAG, "response:" + response);
    int responseCode = response.getStatusLine().getStatusCode();
    LogUtil.debug(TAG, "response code:" + responseCode);
    if (responseCode == 200) {
      res = EntityUtils.toString(response.getEntity(), "utf-8");
    }
    return res;
  }

  private static HttpGet configGetHeader(Map<String, String> headerMap,
      HttpGet httpGet) {
    if (null != headerMap) {
      Set<String> keySet = headerMap.keySet();
      for (String key : keySet) {
        LogUtil.debug(TAG,
            "header name:" + key + ",header value:" + headerMap.get(key));
        httpGet.addHeader(key, headerMap.get(key));
      }
    }
    return httpGet;
  }

  private static String configGetUrl(String url, Map<String, String> params) {
    if (null != params) {
      url += "?" + configParams(params);
    }
    return url;
  }

  private static String configParams(Map<String, String> params) {
    StringBuilder sb = new StringBuilder();
    Set<String> keySet = params.keySet();
    for (String key : keySet) {
      sb.append(key).append("=").append(params.get(key)).append("&");
    }
    if (sb.length() > 0) {
      sb.setLength(sb.length() - 1);
    }
    return sb.toString();
  }

  private static HttpClient getHttpClient() {
    BasicHttpParams httpParams = new BasicHttpParams();
    ConnManagerParams.setTimeout(httpParams, 5000);
    HttpConnectionParams.setConnectionTimeout(httpParams,
        CommonConsts.REQUEST_TIMEOUT);
    HttpConnectionParams.setSoTimeout(httpParams, CommonConsts.SO_TIMEOUT);
    HttpClient client = new DefaultHttpClient(httpParams);
    return client;
  }

}
