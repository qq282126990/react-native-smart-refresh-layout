import React, {forwardRef, memo, useRef, useEffect} from 'react';

import {ScrollViewProps, StyleSheet} from 'react-native';

import SmartRefreshLayout from './SmartRefreshLayout';

type Props = {
  children?: ScrollViewProps;
  headerHeight: number;
  renderHeader: object;
  onRefresh: () => void;
};

const SmartRefresh = forwardRef((props: Props, forwardedRef: any) => {
  // ref
  const refreshLayout = useRef(null);

  // mounted
  useEffect(() => {
    forwardedRef.current = {};
  }, [forwardedRef]);

  return (
    <SmartRefreshLayout
      style={styles.wrapper}
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
