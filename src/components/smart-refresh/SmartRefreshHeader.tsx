import React, {type PropsWithChildren} from 'react';
import {requireNativeComponent} from 'react-native';

// 自定义下拉头部
export const SmartRefreshHeader = requireNativeComponent('SmartRefreshHeader');

const _SmartRefreshHeader: React.FC<
  PropsWithChildren<{
    style: object;
  }>
> = props => {
  return <SmartRefreshHeader {...props} />;
};

export default _SmartRefreshHeader;
