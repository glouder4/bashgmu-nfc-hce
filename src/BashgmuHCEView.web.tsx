import * as React from 'react';

import { BashgmuHCEViewProps } from './BashgmuHCE.types';

export default function BashgmuHCEView(props: BashgmuHCEViewProps) {
  return (
    <div>
      <span>{props.name}</span>
    </div>
  );
}
