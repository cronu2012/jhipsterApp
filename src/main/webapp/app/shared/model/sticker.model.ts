export interface ISticker {
  id?: number;
  stickerNo?: string | null;
  imgContentType?: string | null;
  img?: string | null;
}

export class Sticker implements ISticker {
  constructor(
    public id?: number,
    public stickerNo?: string | null,
    public imgContentType?: string | null,
    public img?: string | null,
  ) {}
}
