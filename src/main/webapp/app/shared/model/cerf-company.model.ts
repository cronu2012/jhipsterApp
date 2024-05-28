import { type ICerf } from '@/shared/model/cerf.model';
import { type ICompany } from '@/shared/model/company.model';

export interface ICerfCompany {
  id?: number;
  relType?: string | null;
  cerf?: ICerf | null;
  company?: ICompany | null;
}

export class CerfCompany implements ICerfCompany {
  constructor(
    public id?: number,
    public relType?: string | null,
    public cerf?: ICerf | null,
    public company?: ICompany | null,
  ) {}
}
