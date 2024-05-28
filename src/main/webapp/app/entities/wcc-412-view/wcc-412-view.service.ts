import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { type IWcc412View } from '@/shared/model/wcc-412-view.model';

const baseApiUrl = 'api/wcc-412-views';

export default class Wcc412ViewService {
  public find(id: number): Promise<IWcc412View> {
    return new Promise<IWcc412View>((resolve, reject) => {
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
