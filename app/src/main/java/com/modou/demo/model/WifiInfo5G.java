package com.modou.demo.model;

import java.io.Serializable;

/**
 * @author JasonZhao
 */
public class WifiInfo5G implements Serializable {

  private static final long serialVersionUID = 1L;

  private boolean same_as_2g;

  private boolean ap_enabled;

  private boolean shortgi_enabled;

  private boolean wmm_enabled;

  private int band_width_mode;

  private boolean enabled;

  private String ssid;

  private int power;

  private int beacon;

  private boolean broadcastssid;

  private String password;

  private String security_mode;

  private int channel;

  private boolean apsd_enabled;

  private String encrypt;

  private int net_type;

  private String mac;

  public boolean isSame_as_2g() {
    return same_as_2g;
  }

  public void setSame_as_2g(boolean same_as_2g) {
    this.same_as_2g = same_as_2g;
  }

  public String getEncrypt() {
    return encrypt;
  }

  public void setEncrypt(String encrypt) {
    this.encrypt = encrypt;
  }

  public boolean isWmm_enabled() {
    return wmm_enabled;
  }

  public void setWmm_enabled(boolean wmm_enabled) {
    this.wmm_enabled = wmm_enabled;
  }

  public String getSsid() {
    return ssid;
  }

  public void setSsid(String ssid) {
    this.ssid = ssid;
  }

  public int getPower() {
    return power;
  }

  public void setPower(int power) {
    this.power = power;
  }

  public int getBeacon() {
    return beacon;
  }

  public void setBeacon(int beacon) {
    this.beacon = beacon;
  }

  public boolean isShortgi_enabled() {
    return shortgi_enabled;
  }

  public void setShortgi_enabled(boolean shortgi_enabled) {
    this.shortgi_enabled = shortgi_enabled;
  }

  public boolean isBroadcastssid() {
    return broadcastssid;
  }

  public void setBroadcastssid(boolean broadcastssid) {
    this.broadcastssid = broadcastssid;
  }

  public boolean isAp_enabled() {
    return ap_enabled;
  }

  public void setAp_enabled(boolean ap_enabled) {
    this.ap_enabled = ap_enabled;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getSecurity_mode() {
    return security_mode;
  }

  public void setSecurity_mode(String security_mode) {
    this.security_mode = security_mode;
  }

  public int getChannel() {
    return channel;
  }

  public void setChannel(int channel) {
    this.channel = channel;
  }

  public boolean isApsd_enabled() {
    return apsd_enabled;
  }

  public void setApsd_enabled(boolean apsd_enabled) {
    this.apsd_enabled = apsd_enabled;
  }

  public int getBand_width_mode() {
    return band_width_mode;
  }

  public void setBand_width_mode(int band_width_mode) {
    this.band_width_mode = band_width_mode;
  }

  public int getNet_type() {
    return net_type;
  }

  public void setNet_type(int net_type) {
    this.net_type = net_type;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

}
