export interface IProd {
  id?: number;
  prodNo?: string | null;
  enName?: string | null;
  chName?: string | null;
  hsCode?: string | null;
  cccCode?: string | null;
}

export class Prod implements IProd {
  constructor(
    public id?: number,
    public prodNo?: string | null,
    public enName?: string | null,
    public chName?: string | null,
    public hsCode?: string | null,
    public cccCode?: string | null,
  ) {}
}
