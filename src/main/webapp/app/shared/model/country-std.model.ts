import { type ICountry } from '@/shared/model/country.model';
import { type IStd } from '@/shared/model/std.model';

export interface ICountryStd {
  id?: number;
  relType?: string | null;
  country?: ICountry | null;
  std?: IStd | null;
}

export class CountryStd implements ICountryStd {
  constructor(
    public id?: number,
    public relType?: string | null,
    public country?: ICountry | null,
    public std?: IStd | null,
  ) {}
}
