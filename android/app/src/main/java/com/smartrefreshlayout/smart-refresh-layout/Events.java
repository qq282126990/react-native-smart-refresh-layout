// 此处请修改为你的包名称
package com.smartrefreshlayout;

public enum Events {
  // 刷新触发
  REFRESH("onSmartRefresh"),
  // 下拉触发
  HEADER_MOVING("onHeaderMoving"),
  // 下拉开始刷新
  PULL_DOWN_TO_REFRESH("onPullDownToRefresh"),
  // 释放刷新
  RELEASE_TO_REFRESH("onReleaseToRefresh"),
  // 释放时进行刷新
  HEADER_RELEASED("onHeaderReleased");

  private final String mName;

  Events(final String name) {
    mName = name;
  }

  @Override
  public String toString() {
    return mName;
  }
}
