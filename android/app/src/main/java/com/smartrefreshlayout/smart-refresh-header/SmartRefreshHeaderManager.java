// 此处请修改为你的包名称
package com.smartrefreshlayout;

import android.graphics.Color;
import androidx.annotation.ColorInt;
// 自定义头部
import com.smartrefreshlayout.SmartRefreshHeader;
// 变换方式样式常量
import com.smartrefreshlayout.SpinnerStyleConstants;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
// 下拉刷新库
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import java.util.HashMap;

public class SmartRefreshHeaderManager
  extends ViewGroupManager<SmartRefreshHeader> {

  /**
   * getName方法返回的名字会用于在 JavaScript 端引用
   *
   * @return
   */
  @Override
  public String getName() {
    return "SmartRefreshHeader";
  }

  /**
   * 视图在createViewInstance中创建， 且应当把自己初始化为默认的状态。所有属性的设置都通过后续的updateView来进行
   *
   * @param reactContext
   * @return
   */
  @Override
  protected SmartRefreshHeader createViewInstance(
    ThemedReactContext reactContext
  ) {
    return new SmartRefreshHeader(reactContext);
  }

  /**
   * 设置主调色
   *
   * @param view
   * @param primaryColor
   */
  @ReactProp(name = "primaryColor")
  public void setPrimaryColor(SmartRefreshHeader view, String primaryColor) {
    view.setPrimaryColor(Color.parseColor(primaryColor));
  }

  /**
   * 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）
   *
   * @param view
   * @param spinnerStyle
   */
  @ReactProp(name = "spinnerStyle")
  public void setSpinnerStyle(SmartRefreshHeader view, String spinnerStyle) {
    view.setSpinnerStyle(
      SpinnerStyleConstants.SpinnerStyleMap.get(spinnerStyle)
    );
  }
}
