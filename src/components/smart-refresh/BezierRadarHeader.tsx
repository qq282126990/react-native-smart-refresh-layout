import React, {type PropsWithChildren} from 'react';
import {requireNativeComponent} from 'react-native';

// 经典下拉头部
export const BezierRadarHeader = requireNativeComponent('BezierRadarHeader');

const _BezierRadarHeader: React.FC<
  PropsWithChildren<{
    style: object;
    primaryColor?: string;
    accentColor?: string;
  }>
> = props => {
  return <BezierRadarHeader {...props} />;
};

export default _BezierRadarHeader;
