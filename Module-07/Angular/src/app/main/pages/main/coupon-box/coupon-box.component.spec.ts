import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CouponBoxComponent } from './coupon-box.component';

describe('CouponBoxComponent', () => {
  let component: CouponBoxComponent;
  let fixture: ComponentFixture<CouponBoxComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CouponBoxComponent]
    });
    fixture = TestBed.createComponent(CouponBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
