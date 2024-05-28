export interface IMark {
  id?: number;
  markNo?: string | null;
  enName?: string | null;
  chName?: string | null;
  imgContentType?: string | null;
  img?: string | null;
}

export class Mark implements IMark {
  constructor(
    public id?: number,
    public markNo?: string | null,
    public enName?: string | null,
    public chName?: string | null,
    public imgContentType?: string | null,
    public img?: string | null,
  ) {}
}
