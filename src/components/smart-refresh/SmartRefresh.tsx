import React, {forwardRef, memo, useRef, useEffect} from 'react';

import {
  ScrollViewProps,
  StyleSheet,
  requireNativeComponent,
  findNodeHandle,
  UIManager as NotTypedUIManager,
  UIManagerStatic,
} from 'react-native';

type Props = {
  // 滚动列表
  children?: ScrollViewProps;
  // 下拉刷新头部高度
  headerHeight: number;
  // 下拉刷新头部渲染函数
  renderHeader: object;
  // 刷新中回调
  onRefresh: () => void;
};

interface CustomUIManager extends UIManagerStatic {
  SmartRefreshLayout: any;
}

const UIManager = NotTypedUIManager as CustomUIManager;

// 引入原生外层组件
const SmartRefreshLayout: any = requireNativeComponent('SmartRefreshLayout');

const SmartRefresh = forwardRef((props: Props, forwardedRef: any) => {
  // ref
  const refreshLayout = useRef(null);

  // 初始化forwardedRef
  useEffect(() => {
    forwardedRef.current = {
      handleFinishRefresh,
    };
  });

  // 找到当前节点
  const findNode = () => {
    return findNodeHandle(refreshLayout.current);
  };

  // 触发事件
  const dispatchCommand = (commandName: string, params: any) => {
    UIManager.dispatchViewManagerCommand(
      findNode(),
      (UIManager.getViewManagerConfig
        ? UIManager.getViewManagerConfig('SmartRefreshLayout')
        : UIManager.SmartRefreshLayout
      ).Commands[commandName],
      params,
    );
  };

  // 停止刷新
  const handleFinishRefresh = () => {
    // -1为马上刷新完成
    // >0 为延迟刷新完成时间(毫秒)
    dispatchCommand('finishRefresh', [-1, true]);
  };

  return (
    <SmartRefreshLayout
      style={styles.wrapper}
      // 下拉刷新头部高度
      headerHeight={props.headerHeight}
      // 刷新中
      onSmartRefresh={() => {
        props.onRefresh();
      }}
      ref={refreshLayout}>
      {/* 下拉头部  */}
      {props.renderHeader}
      {/* 滚动列表 */}
      {props.children}
    </SmartRefreshLayout>
  );
});

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
  },
});

export default memo(SmartRefresh);
