// 此处请修改为你的包名称
package com.smartrefreshlayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
// 下拉刷新库
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.scwang.smart.refresh.layout.util.SmartUtil;
// 组件可以触发的事件
import com.smartrefreshlayout.Events;
// 外层组件
import com.smartrefreshlayout.ReactSmartRefreshLayout;
// 下拉刷新头部
import com.smartrefreshlayout.SmartRefreshHeader;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class SmartRefreshLayoutManager
  extends ViewGroupManager<ReactSmartRefreshLayout> {

  // 外层组件
  private ReactSmartRefreshLayout smartRefreshLayout;
  // 事件发射器
  private RCTEventEmitter mEventEmitter;
  private ThemedReactContext themedReactContext;

  // 完成刷新并标记没有更多数据
  private static final String COMMAND_FINISH_REFRESH_NAME = "finishRefresh";
  // 事件id
  private static final int COMMAND_FINISH_REFRESH_ID = 0;

  private int getTargetId() {
    return smartRefreshLayout.getId();
  }

  @Override
  public String getName() {
    return "SmartRefreshLayout";
  }

  @Override
  protected ReactSmartRefreshLayout createViewInstance(
    ThemedReactContext reactContext
  ) {
    smartRefreshLayout = new ReactSmartRefreshLayout(reactContext);

    // 禁止上拉加载
    smartRefreshLayout.setEnableLoadMore(false);
    // 最大显示下拉高度/Header标准高度
    smartRefreshLayout.setHeaderMaxDragRate(2);
    //触发刷新距离 与 HeaderHeight 的比率
    smartRefreshLayout.setHeaderTriggerRate(1);
    // 是否启用越界回弹
    smartRefreshLayout.setEnableOverScrollBounce(true);
    // 是否启用越界拖动（仿苹果效果）
    smartRefreshLayout.setEnableOverScrollDrag(true);

    themedReactContext = reactContext;

    // 注册事件发射器
    mEventEmitter = reactContext.getJSModule(RCTEventEmitter.class);

    return smartRefreshLayout;
  }

  /**
   * 获取导出的自定义直接事件类型常量
   *
   * @return
   */
  @Override
  public Map getExportedCustomDirectEventTypeConstants() {
    MapBuilder.Builder builder = MapBuilder.builder();

    for (Events event : Events.values()) {
      builder.put(
        event.toString(),
        MapBuilder.of("registrationName", event.toString())
      );
    }
    return builder.build();
  }

  /**
   * 获取命令映射
   *
   * @return
   */
  @Nullable
  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of(
      COMMAND_FINISH_REFRESH_NAME,
      COMMAND_FINISH_REFRESH_ID
    );
  }

  /**
   * Set the ratio of the maximum height to drag header.
   * 设置下拉最大高度和Header高度的比率（将会影响可以下拉的最大高度）
   * @param rate ratio = (the maximum height to drag header)/(the height of header)
   *             比率 = 下拉最大高度 / Header的高度
   * @return RefreshLayout
   */
  @ReactProp(name = "maxDragRate", defaultFloat = 2.0f)
  public void setMaxDragRate(ReactSmartRefreshLayout view, float maxDragRate) {
    view.setHeaderMaxDragRate(maxDragRate);
  }

  /**
   * Set the damping effect.
   * 显示拖动高度/真实拖动高度 比率（默认0.5，阻尼效果）
   * @param rate ratio = (The drag height of the view)/(The actual drag height of the finger)
   *             比率 = 视图拖动高度 / 手指拖动高度
   * @return RefreshLayout
   */
  @ReactProp(name = "dragRate", defaultFloat = 0.5f)
  public void setDragRate(ReactSmartRefreshLayout view, float dragRate) {
    view.setDragRate(dragRate);
  }

  /**
   * Set whether to enable cross-border drag (imitation iphone effect).
   * 设置是否启用越界拖动（仿苹果效果）
   * @param enabled 是否启用
   * @return RefreshLayout
   */
  @ReactProp(name = "overScrollDrag", defaultBoolean = false)
  public void setOverScrollDrag(
    ReactSmartRefreshLayout view,
    boolean overScrollDrag
  ) {
    view.setEnableOverScrollDrag(overScrollDrag);
  }

  /**
   * Set whether to enable cross-border rebound function.
   * 设置是否启用越界回弹
   * @param enabled 是否启用
   * @return RefreshLayout
   */
  @ReactProp(name = "overScrollBounce", defaultBoolean = false)
  public void setOverScrollBounce(
    ReactSmartRefreshLayout view,
    boolean overScrollBounce
  ) {
    view.setEnableOverScrollBounce(overScrollBounce);
  }

  /**
   * Set whether to enable the pure scroll mode.
   * 设置是否开启纯滚动模式
   * @param enabled 是否启用
   * @return RefreshLayout
   */
  @ReactProp(name = "pureScroll", defaultBoolean = false)
  public void setPureScroll(ReactSmartRefreshLayout view, boolean pureScroll) {
    view.setEnablePureScrollMode(pureScroll);
  }

  /**
   * Set theme color id (primaryColor and accentColor).
   * 设置主题颜色
   *
   * @param primaryColorId ColorRes 主题颜色ID
   * @return RefreshLayout
   */
  @ReactProp(name = "primaryColor", defaultInt = Color.TRANSPARENT)
  public void setPrimaryColor(ReactSmartRefreshLayout view, int primaryColor) {
    view.setPrimaryColors(primaryColor);
  }

  /**
   * Set the Header's height.
   * 设置 Header 高度
   *
   * @param heightDp Density-independent Pixels 虚拟像素（px需要调用px2dp转换）
   * @return RefreshLayout
   */
  @ReactProp(name = "headerHeight")
  public void setHeaderHeight(
    ReactSmartRefreshLayout view,
    float headerHeight
  ) {
    if (headerHeight != 0.0f) {
      view.setHeaderHeight(headerHeight);
    }
  }

  /**
   * 是否启用下拉刷新（默认启用）
   *
   * @param enabled 是否启用
   * @return SmartRefreshLayout
   */
  @ReactProp(name = "enableRefresh", defaultBoolean = true)
  public void setEnableRefresh(
    ReactSmartRefreshLayout view,
    boolean enableRefresh
  ) {
    view.setEnableRefresh(enableRefresh);
  }

  /**
   * 是否启用自动刷新
   *
   * @param view
   * @param autoRefresh
   */
  @ReactProp(name = "autoRefresh", defaultBoolean = false)
  public void setAutoRefresh(
    ReactSmartRefreshLayout view,
    ReadableMap autoRefresh
  ) {
    boolean isAutoRefresh = false;
    Integer time = null;

    if (autoRefresh.hasKey("refresh")) {
      isAutoRefresh = autoRefresh.getBoolean("refresh");
    }
    if (autoRefresh.hasKey("time")) {
      time = autoRefresh.getInt("time");
    }

    /**
     * Display refresh animation and trigger refresh event.
     * 显示刷新动画并且触发刷新事件
     * @return true or false, Status non-compliance will fail.
     *  是否成功（状态不符合会失败）
     */
    if (isAutoRefresh == true) {
      if (time != null && time > 0) {
        view.autoRefresh(time);
      } else {
        view.autoRefresh();
      }
    }
  }

  /**
   * finish refresh.
   * 完成刷新
   *
   * @param delayed 开始延时
   * @return RefreshLayout
   */
  @Override
  public void receiveCommand(
    ReactSmartRefreshLayout root,
    int commandId,
    @Nullable ReadableArray args
  ) {
    switch (commandId) {
      case COMMAND_FINISH_REFRESH_ID:
        int delayed = args.getInt(0);
        boolean success = args.getBoolean(1);

        if (delayed >= 0) {
          root.finishRefresh(delayed, success, null);
        } else {
          root.finishRefresh(success);
        }
        break;
      default:
        break;
    }
  }

  /**
   * Set the header of RefreshLayout.
   * 设置指定的 Header
   *
   * @param header RefreshHeader 刷新头
   * @return RefreshLayout
   */
  @Override
  public void addView(ReactSmartRefreshLayout view, View child, int index) {
    switch (index) {
      case 0:
        RefreshHeader header;
        if (child instanceof RefreshHeader) {
          header = (RefreshHeader) child;
        } else {
          header = new SmartRefreshHeader(themedReactContext);
          ((SmartRefreshHeader) header).setView(child);
        }

        /**
         * Set the header of RefreshLayout.
         * 设置指定的 Header
         * @param header RefreshHeader 刷新头
         * @param width the width in px, can use MATCH_PARENT and WRAP_CONTENT.
         *              宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
         * @param height the height in px, can use MATCH_PARENT and WRAP_CONTENT.
         *               高度 可以使用 MATCH_PARENT, WRAP_CONTENT
         * @return RefreshLayout
         */
        view.setRefreshHeader(
          header,
          // 匹配父母
          MATCH_PARENT,
          // 匹配父母
          MATCH_PARENT
        );

        break;
      case 1:
        /**
         * Set the content of RefreshLayout（Suitable for non-XML pages, not suitable for replacing empty layouts）。
         * 设置指定的 Content（适用于非XML页面，不适合用替换空布局）
         * @param content View 内容视图
         * @return RefreshLayout
         */
        view.setRefreshContent(child);

        break;
      case 2:
        break;
      default:
        break;
    }
  }

  /**
   * 添加视图
   *
   * @param parent
   * @param views
   */
  @Override
  public void addViews(ReactSmartRefreshLayout parent, List<View> views) {
    super.addViews(parent, views);
  }

  /**
   * 添加事件发射器
   *
   * @param reactContext
   * @param view
   */
  @Override
  protected void addEventEmitters(
    ThemedReactContext reactContext,
    final ReactSmartRefreshLayout view
  ) {
    /**
     * 必须设置OnRefreshListener，如果没有设置，
     * 则会自动触发finishRefresh
     *
     * OnRefreshListener和OnSimpleMultiPurposeListener
     * 中的onRefresh都会触发刷新，只需写一个即可
     */
    view.setOnRefreshListener(
      new OnRefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {}
      }
    );

    // 事件监听
    view.setOnMultiListener(
      // 多功能监听器
      new SimpleMultiListener() {
        private int getTargetId() {
          return view.getId();
        }

        // 头部移动
        public void onHeaderMoving(
          RefreshHeader header,
          boolean isDragging,
          float percent,
          int offset,
          int headerHeight,
          int maxDragHeight
        ) {
          WritableMap writableMap = Arguments.createMap();

          // 百分比
          writableMap.putDouble("percent", percent);
          // 偏移位置
          writableMap.putDouble("offset", SmartUtil.px2dp(offset));
          // 头部高度
          writableMap.putDouble("headerHeight", SmartUtil.px2dp(headerHeight));

          // 下拉触发
          mEventEmitter.receiveEvent(
            getTargetId(),
            Events.HEADER_MOVING.toString(),
            writableMap
          );
        }

        // 释放时进行刷新
        @Override
        public void onHeaderReleased(
          RefreshHeader header,
          int headerHeight,
          int extendHeight
        ) {
          WritableMap writableMap = Arguments.createMap();

          // 头部高度
          writableMap.putDouble("headerHeight", SmartUtil.px2dp(headerHeight));

          // 释放时进行刷新
          mEventEmitter.receiveEvent(
            getTargetId(),
            Events.HEADER_RELEASED.toString(),
            writableMap
          );
        }

        // 头部开始动画
        @Override
        public void onHeaderStartAnimator(
          RefreshHeader header,
          int headerHeight,
          int extendHeight
        ) {}

        // 头部结束
        @Override
        public void onHeaderFinish(RefreshHeader header, boolean success) {}

        // 加载更多触发
        @Override
        public void onLoadMore(RefreshLayout refreshLayout) {}

        // 刷新触发
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
          mEventEmitter.receiveEvent(
            getTargetId(),
            Events.REFRESH.toString(),
            null
          );
        }

        // 开始改变
        @Override
        public void onStateChanged(
          RefreshLayout refreshLayout,
          RefreshState oldState,
          RefreshState newState
        ) {
          switch (newState) {
            // 下拉开始刷新
            case None:
            case PullDownToRefresh:
              mEventEmitter.receiveEvent(
                getTargetId(),
                Events.PULL_DOWN_TO_REFRESH.toString(),
                null
              );
              break;
            case Refreshing:
              break;
            // 释放刷新
            case ReleaseToRefresh:
              mEventEmitter.receiveEvent(
                getTargetId(),
                Events.RELEASE_TO_REFRESH.toString(),
                null
              );
              break;
          }
        }
      }
    );
  }
}
