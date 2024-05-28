import { type ICerf } from '@/shared/model/cerf.model';
import { type IMark } from '@/shared/model/mark.model';

export interface ICerfMark {
  id?: number;
  relType?: string | null;
  cerf?: ICerf | null;
  mark?: IMark | null;
}

export class CerfMark implements ICerfMark {
  constructor(
    public id?: number,
    public relType?: string | null,
    public cerf?: ICerf | null,
    public mark?: IMark | null,
  ) {}
}
