import React, {useRef} from 'react';
import {View, Text, StyleSheet, FlatList} from 'react-native';

// import PullToRefreshAndroid from '../components/refresh-control/PullToRefresh.android';
import PullToRefreshAndroid from '../components/refresh-control/PullToRefreshClassicsHeader.android';
import PullToRefreshBezierRadarHeader from '../components/refresh-control/PullToRefreshBezierRadarHeader.android';

type Item = {
  title: string;
  id?: string;
};

type PullToRefreshAndroid = {
  handleFinishRefresh: () => void;
};

// item高度
const ITEM_HEIGHT: number = 70;

// 数据
let DATA = Array.from({length: 20}, (_, index) => {
  return {
    title: `React真好用👍-${index}`,
    id: `${index}`,
  };
});

// item
const Item = ({title}: Item) => {
  return (
    <View style={styles.itemWrapper}>
      <View style={styles.item}>
        <Text style={styles.title}>{title}</Text>
      </View>
    </View>
  );
};

// 渲染item
const renderItem = ({item}: {item: Item}) => <Item title={item.title} />;

const Home: React.FC<{}> = () => {
  // 安卓下拉刷新组件
  const pullToRefreshAndroid = useRef<PullToRefreshAndroid>(null);

  return (
    <View style={styles.wrapper}>
      <FlatList
        data={DATA}
        renderItem={renderItem}
        getItemLayout={(data, index) => ({
          length: ITEM_HEIGHT,
          offset: ITEM_HEIGHT * index,
          index,
        })}
        keyExtractor={item => item.id}
        refreshControl={
          <PullToRefreshBezierRadarHeader
            onRefresh={() => {
              console.log('开始刷新');

              setTimeout(() => {
                pullToRefreshAndroid.current?.handleFinishRefresh();
              }, 1000);
            }}
            ref={pullToRefreshAndroid}
          />
        }
      />
    </View>
  );
};

// styles
const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
  },
  itemWrapper: {
    padding: 10,
    height: ITEM_HEIGHT,
  },
  item: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#5B8EFF',
    borderRadius: 5,
  },
  title: {
    color: '#ffffff',
    fontSize: 16,
  },
});

export default Home;
