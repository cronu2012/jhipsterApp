import { type ICerf } from '@/shared/model/cerf.model';
import { type IStd } from '@/shared/model/std.model';

export interface ICerfStd {
  id?: number;
  relType?: string | null;
  cerf?: ICerf | null;
  std?: IStd | null;
}

export class CerfStd implements ICerfStd {
  constructor(
    public id?: number,
    public relType?: string | null,
    public cerf?: ICerf | null,
    public std?: IStd | null,
  ) {}
}
