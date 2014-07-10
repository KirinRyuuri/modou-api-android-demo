package com.modou.demo.task;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;

import com.modou.demo.ContentActivity;
import com.modou.demo.util.CommonConsts;
import com.modou.demo.util.CommonUtil;
import com.modou.demo.util.InternetUtil;
import com.modou.demo.util.LogUtil;
import com.modou.demo.util.MessageCenter;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author JasonZhao
 */
public class ModouConnectRouterTask extends AsyncTask<Void, Void, String> {

  private static final String TAG = "MatrixConnectRouterTask";

  private Context context;

  private String routerName;

  private String routerPassword;

  private Dialog dialog;

  public ModouConnectRouterTask(Context context) {
    this.context = context;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public String getRouterName() {
    return routerName;
  }

  public void setRouterName(String routerName) {
    this.routerName = routerName;
  }

  public String getRouterPassword() {
    return routerPassword;
  }

  public void setRouterPassword(String routerPassword) {
    this.routerPassword = routerPassword;
  }

  @Override
  protected void onPreExecute() {
    dialog = CommonUtil.showLoading(context);
    super.onPreExecute();
  }

  @Override
  protected String doInBackground(Void... arg0) {
    String res = null;
    String url = String.format(CommonConsts.URL_MATRIX_LOGIN, CommonUtil.getRouterIp(context));
    try {
      JSONObject data = new JSONObject();
      data.put("password", routerPassword);
      res = InternetUtil.doMatrixLogin(context, url, data);
      LogUtil.debug(TAG, "matrix login response:" + res);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
      if (e instanceof HttpHostConnectException) {
        if (e.getMessage().indexOf("refused") >= 0) {
          Message msg = new Message();
          msg.what = CommonConsts.SHOULD_OPEN_JK_MODE;
          msg.obj = context;
          MessageCenter.getInstance().sendMessage(msg);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    } finally {
      CommonUtil.sendDismissDialogMessage(dialog);
    }
    return res;
  }

  @Override
  protected void onPostExecute(String result) {
    if (null == result) {
      CommonUtil.showToast(context, "用户名或密码错误");
    } else {
      try {
        JSONObject response = new JSONObject(result);
        if (response.has("code") && !response.getString("code").equals("0")) {
          CommonUtil.showToast(context, "用户名或密码错误");
        } else {
          Intent intent = new Intent(context, ContentActivity.class);
          context.startActivity(intent);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    super.onPostExecute(result);
  }

}
