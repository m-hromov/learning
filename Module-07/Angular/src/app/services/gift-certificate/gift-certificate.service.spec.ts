import { TestBed } from '@angular/core/testing';

import { GiftCertificateService } from './gift-certificate.service';

describe('GiftCertificateService', () => {
  let service: GiftCertificateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GiftCertificateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
