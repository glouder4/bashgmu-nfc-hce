import { StyleSheet, Text, View } from 'react-native';

import * as BashgmuHCE from 'bashgmu-hce';

export default function App() {
  return (
    <View style={styles.container}>
      <Text>{BashgmuHCE.hello()}</Text>

      <Text>{JSON.stringify(BashgmuHCE.supportNFC())}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
