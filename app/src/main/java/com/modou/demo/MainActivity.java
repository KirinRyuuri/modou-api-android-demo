package com.modou.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.modou.demo.task.ModouConnectRouterTask;
import com.modou.demo.task.RouterDiscoverTask;
import com.modou.demo.util.CommonUtil;
import com.modou.demo.util.MessageCenter;

/**
 *
 */
public class MainActivity extends Activity {

  private Context context;

  private ProgressBar disLoa;

  private TextView disRes;

  private EditText gateway;

  private EditText loginUsername;

  private EditText loginPass;

  public ProgressBar getDisLoa() {
    return disLoa;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
  }

  private void init() {
    initField();
  }

  @Override
  protected void onResume() {
    super.onResume();
    RouterDiscoverTask task = new RouterDiscoverTask(context);
    task.execute();
  }

  private void initField() {
    context = this;
    disLoa = (ProgressBar) findViewById(R.id.loading_discovery);
    disRes = (TextView) findViewById(R.id.result_discovery);
    gateway = (EditText) findViewById(R.id.gateway);
    loginUsername = (EditText) findViewById(R.id.login_username);
    loginPass = (EditText) findViewById(R.id.login_pass);
    MessageCenter.initInstance();
  }

  public void doLogin(View view) {
    if (submitCheck()) {
      ModouConnectRouterTask task = new ModouConnectRouterTask(context);
      task.setRouterName(loginUsername.getText().toString());
      task.setRouterPassword(loginPass.getText().toString());
      task.execute();
    }
  }


  private boolean submitCheck() {
    if (TextUtils.isEmpty(gateway.getText())) {
      CommonUtil.showToast(context, "网关地址不可为空");
      return false;
    }
    if (TextUtils.isEmpty(loginUsername.getText())) {
      CommonUtil.showToast(context, "管理用户名不可为空");
      return false;
    }
    if (TextUtils.isEmpty(loginPass.getText())) {
      CommonUtil.showToast(context, "管理密码不可为空");
      return false;
    }
    return true;
  }
  public void updateDiscoveryResult(Boolean result) {
    disRes.setText(result ? "发现1台魔豆路由器" : "发现0台魔豆路由器");
    gateway.setText(result ? CommonUtil.getGateway(context) : "");
    loginUsername.setText(result ? "matrix" : "");
    if (!result) {
      CommonUtil.showToast(context, "没有连接wifi或者没有连接魔豆路由器");
    }
  }
}
