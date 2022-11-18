import React, {forwardRef} from 'react';
import {requireNativeComponent} from 'react-native';

// 经典下拉头部
export const SmartRefreshLayout = requireNativeComponent('SmartRefreshLayout');

type Ref = React.Ref<any>;

type Props = {
  style: object;
  ref: Ref;
  headerHeight: number;
  onSmartRefresh: () => void;
  children: object;
};

const _SmartRefreshLayout = forwardRef((props: Props, forwardedRef: Ref) => {
  return <SmartRefreshLayout {...props} ref={forwardedRef} />;
});

export default _SmartRefreshLayout;
