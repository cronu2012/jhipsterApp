export interface IStd {
  id?: number;
  stdNo?: string | null;
  stdVer?: string | null;
  enName?: string | null;
  chName?: string | null;
  status?: string | null;
  issuDt?: Date | null;
  expDt?: Date | null;
}

export class Std implements IStd {
  constructor(
    public id?: number,
    public stdNo?: string | null,
    public stdVer?: string | null,
    public enName?: string | null,
    public chName?: string | null,
    public status?: string | null,
    public issuDt?: Date | null,
    public expDt?: Date | null,
  ) {}
}
