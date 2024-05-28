import { type ICerf } from '@/shared/model/cerf.model';
import { type IProd } from '@/shared/model/prod.model';

export interface ICerfProd {
  id?: number;
  relType?: string | null;
  cerf?: ICerf | null;
  prod?: IProd | null;
}

export class CerfProd implements ICerfProd {
  constructor(
    public id?: number,
    public relType?: string | null,
    public cerf?: ICerf | null,
    public prod?: IProd | null,
  ) {}
}
