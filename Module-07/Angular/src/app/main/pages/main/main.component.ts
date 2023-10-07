import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {GiftCertificate} from "../../../shared/models/gift-certificate.model";
import {fromEvent, Subscription, takeWhile, tap, throttle, throttleTime, timer} from "rxjs";
import {GiftCertificateService} from "../../../services/gift-certificate/gift-certificate.service";
import {StorageService} from "../../../services/storage-service/storage.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.sass']
})
export class MainComponent implements OnInit, OnDestroy {
  giftCertificates: GiftCertificate[] = []
  private giftCertificatesSubs: Subscription[] = []
  private cartSubs: Subscription[] = []
  private scrollSub: Subscription
  page: number = 1
  private endOfPage: boolean
  private prevSearch: string

  constructor(private giftCertificateService: GiftCertificateService,
              private storageService: StorageService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.loadCertificates()
    this.scrollSub = fromEvent(window, 'scroll').pipe(
      tap(() => {
        if (!this.endOfPage)
          this.endOfPage = this.isEndOfPage()
      }),
      throttle(() => this.endOfPage ? timer(100) : timer(0)),
      tap(() => this.onWindowScroll())
    ).subscribe();
  }

  private loadCertificates() {
    this.activatedRoute.queryParams.subscribe(p => {
      if (this.prevSearch !== p['search']) {
        this.page = 1
        this.giftCertificates = []
      }
      this.prevSearch = p['search']
      this.giftCertificatesSubs.push(
        this.giftCertificateService.getCertificates(p["search"] ? p["search"] : '', this.page)
          .subscribe((res: { _embedded: { giftCertificateDtoList: GiftCertificate[]; }; }) => {
            console.log(p["search"])
            let gcs: GiftCertificate[] = res?._embedded?.giftCertificateDtoList
            if (gcs) {
              this.giftCertificates.push(...gcs)
              this.page++
              this.endOfPage = false
            }
          })
      )
    })
  }

  addToCart(id: number) {
    this.cartSubs.push(
      this.giftCertificateService.getCertificateById(id)
        .subscribe((res: GiftCertificate) => this.storageService.addGiftCertificates(res))
    )
  }

  private onWindowScroll() {
    if (this.endOfPage) {
      this.loadCertificates()
    }
  }

  private isEndOfPage() {
    return window.innerHeight + window.scrollY + 100 >= document.body.offsetHeight
  }

  ngOnDestroy(): void {
    this.giftCertificatesSubs.forEach(s => s.unsubscribe())
    this.cartSubs.forEach(s => s.unsubscribe())
    this.scrollSub.unsubscribe()
  }
}
