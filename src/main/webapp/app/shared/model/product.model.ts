export interface IProduct {
  id?: number;
  mainTitle?: string | null;
  subTitle?: string | null;
  description?: string | null;
  price?: string | null;
  createBy?: string | null;
  createdTime?: Date | null;
  updatedTime?: Date | null;
  status?: number | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public mainTitle?: string | null,
    public subTitle?: string | null,
    public description?: string | null,
    public price?: string | null,
    public createBy?: string | null,
    public createdTime?: Date | null,
    public updatedTime?: Date | null,
    public status?: number | null,
  ) {}
}
