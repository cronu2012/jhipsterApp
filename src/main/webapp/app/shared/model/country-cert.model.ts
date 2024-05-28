import { type ICountry } from '@/shared/model/country.model';
import { type ICerf } from '@/shared/model/cerf.model';

export interface ICountryCert {
  id?: number;
  relType?: string | null;
  country?: ICountry | null;
  cerf?: ICerf | null;
}

export class CountryCert implements ICountryCert {
  constructor(
    public id?: number,
    public relType?: string | null,
    public country?: ICountry | null,
    public cerf?: ICerf | null,
  ) {}
}
