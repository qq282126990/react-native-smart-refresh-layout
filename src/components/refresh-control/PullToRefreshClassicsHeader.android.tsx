import React, {forwardRef, memo} from 'react';

import SmartRefresh from '../smart-refresh/SmartRefresh';
import ClassicsHeader from '../smart-refresh/ClassicsHeader';

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
          <ClassicsHeader
            style={{
              height: HEADER_HEIGHT,
            }}
          />
        }
      />
    );
  },
);

export default memo(PullToRefresh);
