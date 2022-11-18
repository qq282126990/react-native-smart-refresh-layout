// 此处请修改为你的包名称
package com.smartrefreshlayout;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.facebook.react.views.view.ReactViewGroup;
// 下拉刷新库
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.util.SmartUtil;

public class SmartRefreshHeader
  extends ReactViewGroup
  implements RefreshHeader {

  // RefreshKernel 核心接口（用于完成高级Header功能）
  private RefreshKernel mRefreshKernel;
  // 背景颜色
  private int mBackgroundColor;
  // 主题颜色
  private Integer mPrimaryColor;
  // 下拉刷新头部
  protected RefreshHeader mRefreshHeader;
  // 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）-> 默认指定为平移即可
  private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;

  public SmartRefreshHeader(Context context) {
    super(context);
    initView(context);
  }

  /**
   * 尺寸定义初始化完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
   * @param kernel RefreshKernel 核心接口（用于完成高级Header功能）
   * @param height HeaderHeight or FooterHeight
   * @param maxDragHeight 最大拖动高度
   */
  // @Override
  public void onInitialized(
    @NonNull RefreshKernel kernel,
    int height,
    int extendHeight
  ) {
    mRefreshKernel = kernel;

    /**
     * 指定在下拉时候为 Header 或 Footer 绘制背景
     * @param internal Header Footer 调用时传 this
     * @param backgroundColor 背景颜色
     * @return RefreshKernel
     */
    mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
  }

  // 此处最好和外部 react native 设置的头部 headerHeight
  private void initView(Context context) {
    // 设置最小高度
    setMinimumHeight(30);
  }

  // 设置视图
  public void setView(View v) {
    addView(v);
  }

  /**
   * 获取真实视图（必须返回，不能为null）
   */
  @NonNull
  public View getView() {
    return this;
  }

  /**
   * 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）
   */
  @Override
  public SpinnerStyle getSpinnerStyle() {
    return this.mSpinnerStyle;
  }

  /**
   * 设置主题颜色 （如果自定义的Header没有颜色，本方法可以什么都不处理）
   * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
   */
  @Override
  public void setPrimaryColors(@ColorInt int... colors) {}

  /**
   * 指定在下拉时候为 Header 或 Footer 绘制背景
   * @param internal Header Footer 调用时传 this
   * @param backgroundColor 背景颜色
   * @return RefreshKernel
   */
  public SmartRefreshHeader setPrimaryColor(@ColorInt int primaryColor) {
    mBackgroundColor = mPrimaryColor = primaryColor;

    if (mRefreshKernel != null) {
      mRefreshKernel.requestDrawBackgroundFor(this, mPrimaryColor);
    }

    return this;
  }

  /**
   * 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）
   *
   * @param style
   * @return
   */
  public SmartRefreshHeader setSpinnerStyle(SpinnerStyle style) {
    this.mSpinnerStyle = style;

    return this;
  }

  /**
   * onMoving
   *
   * @param isDragging
   * @param percent
   * @param offset
   * @param height
   * @param maxDragHeight
   */
  @Override
  public void onMoving(
    boolean isDragging,
    float percent,
    int offset,
    int height,
    int maxDragHeight
  ) {
    if(mRefreshHeader != null) {
      mRefreshHeader.onMoving(isDragging, percent, offset, height, maxDragHeight);
    }
  }

  /**
   * 完成
   *
   * @param refreshLayout
   * @param height
   * @param extendHeight
   */
  @Override
  public void onReleased(
    RefreshLayout refreshLayout,
    int height,
    int extendHeight
  ) {}

  /**
   * 开始动画（开始刷新或者开始加载动画）
   * @param layout RefreshLayout
   * @param height HeaderHeight or FooterHeight
   * @param maxDragHeight 最大拖动高度
   */
  @Override
  public void onStartAnimator(
    RefreshLayout layout,
    int headHeight,
    int maxDragHeight
  ) {}

  /**
   * 动画结束
   * @param layout RefreshLayout
   * @param success 数据是否成功刷新或加载
   * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
   */
  @Override
  public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
    // 延迟500毫秒之后再弹回
    return 500;
  }

  /**
   * 是否支持水平拖动
   *
   * @return
   */
  @Override
  public boolean isSupportHorizontalDrag() {
    return false;
  }

  /**
   * 水平拖动
   *
   * @param percentX
   * @param offsetX
   * @param offsetMax
   */
  @Override
  public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {}

  /**
   * 手指拖动下拉（会连续多次调用，用于实时控制动画关键帧）
   * @param percent 下拉的百分比 值 = offset/headerHeight (0 - percent - (headerHeight+maxDragHeight) / headerHeight )
   * @param offset 下拉的像素偏移量  0 - offset - (headerHeight+maxDragHeight)
   * @param headerHeight Header的高度
   * @param maxDragHeight 最大拖动高度
   */
  public void onPulling(
    float percent,
    int offset,
    int headHeight,
    int maxDragHeight
  ) {}

  /**
   * 手指释放之后的持续动画（会连续多次调用，用于实时控制动画关键帧）
   * @param percent 下拉的百分比 值 = offset/headerHeight (0 - percent - (headerHeight+maxDragHeight) / headerHeight )
   * @param offset 下拉的像素偏移量  0 - offset - (headerHeight+maxDragHeight)
   * @param headerHeight Header的高度
   * @param maxDragHeight 最大拖动高度
   */
  public void onReleasing(
    float percent,
    int offset,
    int headHeight,
    int maxDragHeight
  ) {}

  /**
   * onRefreshReleased
   *
   * @param layout
   * @param headerHeight
   * @param maxDragHeight
   */
  public void onRefreshReleased(
    RefreshLayout layout,
    int headerHeight,
    int maxDragHeight
  ) {}

  /**
   * onStateChanged
   *
   * @param refreshLayout
   * @param oldState
   * @param newState
   */
  @Override
  public void onStateChanged(
    RefreshLayout refreshLayout,
    RefreshState oldState,
    RefreshState newState
  ) {}
}
