/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import Wcc421ViewService from './wcc-421-view.service';
import { Wcc421View } from '@/shared/model/wcc-421-view.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Wcc421View Service', () => {
    let service: Wcc421ViewService;
    let elemDefault;

    beforeEach(() => {
      service = new Wcc421ViewService();
      elemDefault = new Wcc421View(123, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Wcc421View', async () => {
        const returnedFromService = Object.assign(
          {
            cerfId: 1,
            countryChName: 'BBBBBB',
            cerfNo: 'BBBBBB',
            cerfVer: 'BBBBBB',
            cerfStatus: 'BBBBBB',
            companyChName: 'BBBBBB',
            relType: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Wcc421View', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
