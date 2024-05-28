export interface IWcc412View {
  id?: number;
  cerfId?: number | null;
  countryChName?: string | null;
  cerfNo?: string | null;
  cerfVer?: string | null;
  cerfStatus?: string | null;
  countryId?: number | null;
  prodNo?: string | null;
  prodChName?: string | null;
}

export class Wcc412View implements IWcc412View {
  constructor(
    public id?: number,
    public cerfId?: number | null,
    public countryChName?: string | null,
    public cerfNo?: string | null,
    public cerfVer?: string | null,
    public cerfStatus?: string | null,
    public countryId?: number | null,
    public prodNo?: string | null,
    public prodChName?: string | null,
  ) {}
}
