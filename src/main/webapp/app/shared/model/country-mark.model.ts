import { type ICountry } from '@/shared/model/country.model';
import { type IMark } from '@/shared/model/mark.model';

export interface ICountryMark {
  id?: number;
  relType?: string | null;
  country?: ICountry | null;
  mark?: IMark | null;
}

export class CountryMark implements ICountryMark {
  constructor(
    public id?: number,
    public relType?: string | null,
    public country?: ICountry | null,
    public mark?: IMark | null,
  ) {}
}
