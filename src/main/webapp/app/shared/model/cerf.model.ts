export interface ICerf {
  id?: number;
  cerfNo?: string | null;
  cerfVer?: string | null;
  status?: string | null;
  pdfContentType?: string | null;
  pdf?: string | null;
  issuDt?: Date | null;
  expDt?: Date | null;
}

export class Cerf implements ICerf {
  constructor(
    public id?: number,
    public cerfNo?: string | null,
    public cerfVer?: string | null,
    public status?: string | null,
    public pdfContentType?: string | null,
    public pdf?: string | null,
    public issuDt?: Date | null,
    public expDt?: Date | null,
  ) {}
}
