import React, {forwardRef, memo} from 'react';
import {StyleSheet} from 'react-native';

import SmartRefresh from '../smart-refresh/SmartRefresh';
import BezierRadarHeader from '../smart-refresh/BezierRadarHeader';

// types
import type {ScrollViewProps} from 'react-native';

type Props = {
  children?: ScrollViewProps;
  onRefresh: () => void;
};

// 下拉刷新头部高度
const HEADER_HEIGHT = 70;

const PullToRefresh = forwardRef(
  (props: Props, forwardedRef: React.ForwardedRef<unknown>) => {
    return (
      <SmartRefresh
        ref={forwardedRef}
        onRefresh={props.onRefresh}
        children={props.children}
        headerHeight={HEADER_HEIGHT}
        renderHeader={
          <BezierRadarHeader
            style={{
              ...styles.header,
            }}
          />
        }
      />
    );
  },
);

const styles = StyleSheet.create({
  header: {
    height: 100,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#5A66F1',
  },
});

export default memo(PullToRefresh);
