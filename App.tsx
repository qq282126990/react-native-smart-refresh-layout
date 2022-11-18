import React from 'react';
import {View, StyleSheet} from 'react-native';
import Home from './src/screens/Home';

const App = () => {
  return (
    <View style={styles.wrapper}>
      <Home />
    </View>
  );
};

// styles
const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
  },
});

export default App;
