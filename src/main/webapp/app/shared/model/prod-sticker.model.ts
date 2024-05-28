import { type IProd } from '@/shared/model/prod.model';
import { type ISticker } from '@/shared/model/sticker.model';

export interface IProdSticker {
  id?: number;
  relType?: string | null;
  prod?: IProd | null;
  sticker?: ISticker | null;
}

export class ProdSticker implements IProdSticker {
  constructor(
    public id?: number,
    public relType?: string | null,
    public prod?: IProd | null,
    public sticker?: ISticker | null,
  ) {}
}
