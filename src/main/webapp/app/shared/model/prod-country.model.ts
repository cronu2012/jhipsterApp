import { type IProd } from '@/shared/model/prod.model';
import { type ICountry } from '@/shared/model/country.model';

export interface IProdCountry {
  id?: number;
  relType?: string | null;
  prod?: IProd | null;
  country?: ICountry | null;
}

export class ProdCountry implements IProdCountry {
  constructor(
    public id?: number,
    public relType?: string | null,
    public prod?: IProd | null,
    public country?: ICountry | null,
  ) {}
}
