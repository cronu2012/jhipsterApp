export interface IWcc421View {
  id?: number;
  cerfId?: number | null;
  countryChName?: string | null;
  cerfNo?: string | null;
  cerfVer?: string | null;
  cerfStatus?: string | null;
  companyChName?: string | null;
  relType?: string | null;
}

export class Wcc421View implements IWcc421View {
  constructor(
    public id?: number,
    public cerfId?: number | null,
    public countryChName?: string | null,
    public cerfNo?: string | null,
    public cerfVer?: string | null,
    public cerfStatus?: string | null,
    public companyChName?: string | null,
    public relType?: string | null,
  ) {}
}
