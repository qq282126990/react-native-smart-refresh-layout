// 此处请修改为你的包名称
package com.smartrefreshlayout;

import android.content.Context;
import android.view.MotionEvent;
import com.facebook.react.uimanager.events.NativeGestureUtil;
// 下拉刷新库
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshKernel;

public class ReactSmartRefreshLayout extends SmartRefreshLayout{

  // 按下位置
  private float mPrevTouchX = 0;
  // 是否要拦截
  private boolean mIntercepted = false;

  protected RefreshKernel mKernel = new RefreshKernelImpl();

  public ReactSmartRefreshLayout(Context context) {
    super(context);
  }

  /**
   * 布局 Header Footer Content
   * 1.布局代码看起来相对简单，时因为测量的时候，已经做了复杂的计算，布局的时候，直接按照测量结果，布局就可以了
   * @param changed 是否改变
   * @param l 左
   * @param t 上
   * @param r 右
   * @param b 下
   */
  @Override
  public void onLayout(
    boolean changed,
    int left,
    int top,
    int right,
    int bottom
  ) {
    super.onLayout(true, left, top, right, bottom);
  }

  /**
   * 拦截触摸事件->拦截x轴移动
   *
   * @param ev
   * @return
   */
  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (shouldInterceptTouchEvent(ev) && super.onInterceptTouchEvent(ev)) {
      // 通知原生手势开始
      NativeGestureUtil.notifyNativeGestureStarted(this, ev);

      return true;
    }

    return false;
  }

  /**
   * 应该拦截触摸事件
   *
   * @param ev
   * @return
   */
  private boolean shouldInterceptTouchEvent(MotionEvent ev) {
    switch (ev.getAction()) {
      // 手指按下
      case MotionEvent.ACTION_DOWN:
        // 按下位置
        mPrevTouchX = ev.getX();

        mIntercepted = false;
        break;
      // 手指移动
      case MotionEvent.ACTION_MOVE:
        // x轴移动位置
        final float eventX = ev.getX();

        // 拦截x轴移动
        final float xDiff = Math.abs(eventX - mPrevTouchX);

        // 拦截
        if (mIntercepted || xDiff > mTouchSlop) {
          mIntercepted = true;

          return false;
        }
    }

    return true;
  }
}
