package com.modou.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.modou.demo.model.WifiInfo2G;
import com.modou.demo.model.WifiInfo5G;
import com.modou.demo.task.ModouWifiInfoGetTask;

/**
 * Created by JasonZhao on 7/9/14.
 */
public class ContentActivity extends Activity {

  private Context context;

  private TextView display;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_content);
    init();
  }

  private void init() {
    initField();
  }

  private void initField() {
    context = this;
    display = (TextView) findViewById(R.id.display);
  }


  public void queryWifi(View view) {
    ModouWifiInfoGetTask task = new ModouWifiInfoGetTask(context);
    task.execute();
  }

  public void showWifiInfo(WifiInfo2G info2G, WifiInfo5G info5G) {
    StringBuilder sb = new StringBuilder();
    sb.append("2G ssid:").append(info2G.getSsid()).append("\n");
    sb.append("5G ssid:").append(info5G.getSsid()).append("\n");
    display.setText(sb.toString());
  }
}
