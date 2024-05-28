import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { type IWcc421View } from '@/shared/model/wcc-421-view.model';

const baseApiUrl = 'api/wcc-421-views';

export default class Wcc421ViewService {
  public find(id: number): Promise<IWcc421View> {
    return new Promise<IWcc421View>((resolve, reject) => {
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
}
