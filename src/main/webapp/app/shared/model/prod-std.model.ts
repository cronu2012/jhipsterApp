import { type IProd } from '@/shared/model/prod.model';
import { type IStd } from '@/shared/model/std.model';

export interface IProdStd {
  id?: number;
  relType?: string | null;
  prod?: IProd | null;
  std?: IStd | null;
}

export class ProdStd implements IProdStd {
  constructor(
    public id?: number,
    public relType?: string | null,
    public prod?: IProd | null,
    public std?: IStd | null,
  ) {}
}
