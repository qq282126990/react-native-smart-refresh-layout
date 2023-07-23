package com.smartrefreshlayout;

import android.graphics.Color;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
// 下拉刷新库
import com.scwang.smart.refresh.header.ClassicsHeader;

// 经典标头
public class ClassicsHeaderManager extends ViewGroupManager<ClassicsHeader> {

  @Override
  public String getName() {
    return "ClassicsHeader";
  }

  @Override
  protected ClassicsHeader createViewInstance(ThemedReactContext reactContext) {

    // 下拉标题
    ClassicsHeader.REFRESH_HEADER_PULLING = "下拉可以刷新";

    return new ClassicsHeader(reactContext);
  }

  /**
   * 设置主题颜色
   * @param view
   * @param primaryColor
   */
  @ReactProp(name = "primaryColor")
  public void setPrimaryColor(ClassicsHeader header, String primaryColor) {
    header.setPrimaryColor(Color.parseColor(primaryColor));
  }

  /**
   * 设置文字颜色
   * @param view
   * @param accentColor
   */
  @ReactProp(name = "accentColor")
  public void setAccentColor(ClassicsHeader header, String accentColor) {
    header.setAccentColor(Color.parseColor(accentColor));
  }
}
