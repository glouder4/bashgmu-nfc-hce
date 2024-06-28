import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import { BashgmuHCEViewProps } from './BashgmuHCE.types';

const NativeView: React.ComponentType<BashgmuHCEViewProps> =
  requireNativeViewManager('BashgmuHCE');

export default function BashgmuHCEView(props: BashgmuHCEViewProps) {
  return <NativeView {...props} />;
}
