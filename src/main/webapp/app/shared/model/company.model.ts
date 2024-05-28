export interface ICompany {
  id?: number;
  companyNo?: string | null;
  enName?: string | null;
  chName?: string | null;
  tel?: string | null;
  addr?: string | null;
  email?: string | null;
  peopleName?: string | null;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public companyNo?: string | null,
    public enName?: string | null,
    public chName?: string | null,
    public tel?: string | null,
    public addr?: string | null,
    public email?: string | null,
    public peopleName?: string | null,
  ) {}
}
