import {Component, OnInit} from '@angular/core';
import {StorageService} from "../../../services/storage-service/storage.service";
import {GiftCertificate} from "../../../shared/models/gift-certificate.model";
import {OrderService} from "../../../services/order/order.service";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.sass']
})
export class CheckoutComponent implements OnInit{
  giftCertificates: GiftCertificate[]
  totalPrice: number

  constructor(private storageService: StorageService, private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.giftCertificates = this.storageService.getGiftCertificates();
    this.totalPrice = this.giftCertificates.map(gc => gc.price)
      .reduce((sum, current) => sum + current, 0);
  }

  clearCart() {
    this.giftCertificates = []
    this.totalPrice = 0
    this.storageService.clearGiftCertificates()
  }

  checkout() {
    let user = this.storageService.getUser()
    if (user != null) {
      this.giftCertificates.forEach(gc => {
        this.orderService.create(user!.id, gc.id).subscribe()
      })
    }
    this.clearCart()
  }

}
