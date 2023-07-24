package com.smartrefreshlayout;

import android.graphics.Color;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
// 下拉刷新库
import com.scwang.smart.refresh.header.BezierRadarHeader;

// 经典标头
public class BezierRadarHeaderManager extends ViewGroupManager<BezierRadarHeader> {

  @Override
  public String getName() {
    return "BezierRadarHeader";
  }

  @Override
  protected BezierRadarHeader createViewInstance(ThemedReactContext reactContext) {

    return new BezierRadarHeader(reactContext);
  }

  /**
   * 设置主题颜色
   *
   * @param view
   * @param primaryColor
   */
  @ReactProp(name = "primaryColor")
  public void setPrimaryColor(BezierRadarHeader header, String primaryColor) {
    header.setPrimaryColor(Color.parseColor(primaryColor));
  }

  /**
   * 设置文字颜色
   *
   * @param view
   * @param accentColor
   */
  @ReactProp(name = "accentColor")
  public void setAccentColor(BezierRadarHeader header, String accentColor) {
    header.setAccentColor(Color.parseColor(accentColor));
  }
}
