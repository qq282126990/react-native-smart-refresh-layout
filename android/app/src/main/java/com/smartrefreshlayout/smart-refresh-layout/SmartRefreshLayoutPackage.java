// 此处请修改为你的包名称
package com.smartrefreshlayout;

// 经典下拉头部
import com.smartrefreshlayout.ClassicsHeaderManager;
// 自定义刷新下拉头部
import com.smartrefreshlayout.SmartRefreshHeaderManager;
// 外层组件
import com.smartrefreshlayout.SmartRefreshLayoutManager;
// react
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SmartRefreshLayoutPackage implements ReactPackage {

  // UI Components 在此注册
  @Override
  public List<ViewManager> createViewManagers(
    ReactApplicationContext reactContext
  ) {
    return Arrays.<ViewManager>asList(
      // 外层组件
      new SmartRefreshLayoutManager(),
      // 自定义刷新下拉头部
      new SmartRefreshHeaderManager(),
      // ClassicsHeaderManager
      new ClassicsHeaderManager()
    );
  }

  // Native Modules 在此注册
  @Override
  public List<NativeModule> createNativeModules(
    ReactApplicationContext reactContext
  ) {
    return Arrays.<NativeModule>asList();
  }
}
