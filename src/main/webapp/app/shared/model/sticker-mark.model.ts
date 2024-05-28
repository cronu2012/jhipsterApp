import { type ISticker } from '@/shared/model/sticker.model';
import { type IMark } from '@/shared/model/mark.model';

export interface IStickerMark {
  id?: number;
  relType?: string | null;
  sticker?: ISticker | null;
  mark?: IMark | null;
}

export class StickerMark implements IStickerMark {
  constructor(
    public id?: number,
    public relType?: string | null,
    public sticker?: ISticker | null,
    public mark?: IMark | null,
  ) {}
}
