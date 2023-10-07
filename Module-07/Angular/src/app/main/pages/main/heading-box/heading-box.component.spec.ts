import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeadingBoxComponent } from './heading-box.component';

describe('HeadingBoxComponent', () => {
  let component: HeadingBoxComponent;
  let fixture: ComponentFixture<HeadingBoxComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeadingBoxComponent]
    });
    fixture = TestBed.createComponent(HeadingBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
