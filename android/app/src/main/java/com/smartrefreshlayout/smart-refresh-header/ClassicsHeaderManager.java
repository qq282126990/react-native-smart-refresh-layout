package com.smartrefreshlayout;

import android.graphics.Color;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
// 下拉刷新库
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshHeader;

// 经典标头
public class ClassicsHeaderManager extends ViewGroupManager<ClassicsHeader> {

  @Override
  public String getName() {
    return "ClassicsHeader";
  }

  @Override
  protected ClassicsHeader createViewInstance(ThemedReactContext reactContext) {
    // // 下拉标题
    ClassicsHeader.REFRESH_HEADER_PULLING = "下拉可以刷新";

    ClassicsHeader header = new ClassicsHeader(reactContext);

    // Resources res = reactContext.getResources();

    // Drawable myImage = res.getDrawable(R.drawable.ic_index_dashboard);

    // header.setProgressDrawable(myImage);
    // header.setArrowResource(R.drawable.ic_index_dashboard);
    // header.setProgressResource(R.drawable.ic_progress_puzzle);

    return header;
  }

  /**
   * 设置主题颜色
   *
   * @param view
   * @param primaryColor
   */
  @ReactProp(name = "primaryColor")
  public void setPrimaryColor(ClassicsHeader header, String primaryColor) {
    header.setPrimaryColor(Color.parseColor(primaryColor));
  }

  /**
   * 设置文字颜色
   *
   * @param view
   * @param accentColor
   */
  @ReactProp(name = "accentColor")
  public void setAccentColor(ClassicsHeader header, String accentColor) {
    header.setAccentColor(Color.parseColor(accentColor));
  }
}
