export interface ICountry {
  id?: number;
  countryNo?: string | null;
  enName?: string | null;
  chName?: string | null;
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public countryNo?: string | null,
    public enName?: string | null,
    public chName?: string | null,
  ) {}
}
