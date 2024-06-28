import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to BashgmuHCE.web.ts
// and on native platforms to BashgmuHCE.ts
import BashgmuHCEModule from './BashgmuHCEModule';
import BashgmuHCEView from './BashgmuHCEView';
import { ChangeEventPayload, BashgmuHCEViewProps } from './BashgmuHCE.types';

// Get the native constant value.
export const PI = BashgmuHCEModule.PI;

export function hello(): string {
  return BashgmuHCEModule.hello();
}

export async function setValueAsync(value: string) {
  return await BashgmuHCEModule.setValueAsync(value);
}

const emitter = new EventEmitter(BashgmuHCEModule ?? NativeModulesProxy.BashgmuHCE);

export function addChangeListener(listener: (event: ChangeEventPayload) => void): Subscription {
  return emitter.addListener<ChangeEventPayload>('onChange', listener);
}

export { BashgmuHCEView, BashgmuHCEViewProps, ChangeEventPayload };
