import {Component, EventEmitter, Input, Output} from '@angular/core';
import {GiftCertificate} from "../../../../shared/models/gift-certificate.model";

@Component({
  selector: 'coupon-box',
  templateUrl: './coupon-box.component.html',
  styleUrls: ['./coupon-box.component.sass']
})
export class CouponBoxComponent {
  @Input() giftCertificates: GiftCertificate[]
  @Output() certId = new EventEmitter<number>()

  addToCart(id: number) {
    this.certId.emit(id);
  }
}
