import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { type IFeeProdCerfCompany } from '@/shared/model/fee-prod-cerf-company.model';

const baseApiUrl = 'api/fee-prod-cerf-companies';

export default class FeeProdCerfCompanyService {
  public find(id: number): Promise<IFeeProdCerfCompany> {
    return new Promise<IFeeProdCerfCompany>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IFeeProdCerfCompany): Promise<IFeeProdCerfCompany> {
    return new Promise<IFeeProdCerfCompany>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IFeeProdCerfCompany): Promise<IFeeProdCerfCompany> {
    return new Promise<IFeeProdCerfCompany>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IFeeProdCerfCompany): Promise<IFeeProdCerfCompany> {
    return new Promise<IFeeProdCerfCompany>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
