import {Component, OnInit} from '@angular/core';
import {GiftCertificate} from "../../../shared/models/gift-certificate.model";
import {ActivatedRoute} from "@angular/router";
import {GiftCertificateService} from "../../../services/gift-certificate/gift-certificate.service";
import {StorageService} from "../../../services/storage-service/storage.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.sass']
})
export class ItemDetailsComponent implements OnInit {
  giftCertificate!: Observable<GiftCertificate>

  constructor(
    private giftCertificateService: GiftCertificateService,
    private activatedRoute: ActivatedRoute,
    private storageService: StorageService
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(p => {
      this.giftCertificate = this.giftCertificateService.getCertificateById(p["id"])
    })
  }

  addToCart(id: number) {
    this.giftCertificateService.getCertificateById(id)
      .subscribe((res: GiftCertificate) => this.storageService.addGiftCertificates(res))
  }
}
