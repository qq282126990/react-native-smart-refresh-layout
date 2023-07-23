import React, {type PropsWithChildren} from 'react';
import {requireNativeComponent} from 'react-native';

// 经典下拉头部
export const ClassicsHeader = requireNativeComponent('ClassicsHeader');

const _ClassicsHeader: React.FC<
  PropsWithChildren<{
    style: object;
    primaryColor?: string;
    accentColor?: string;
  }>
> = props => {
  return <ClassicsHeader {...props} />;
};

export default _ClassicsHeader;
