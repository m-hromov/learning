import {Component, OnInit} from '@angular/core';
import {GiftCertificateService} from "../../../services/gift-certificate/gift-certificate.service";
import {FormControl, FormGroup} from "@angular/forms";
import {Tag} from "../../../shared/models/tag.model";
import {GiftCertificate} from "../../../shared/models/gift-certificate.model";

@Component({
  selector: 'app-new-coupon',
  templateUrl: './new-coupon.component.html',
  styleUrls: ['./new-coupon.component.sass']
})
export class NewCouponComponent implements OnInit {
  newForm!: FormGroup
  certName: FormControl = new FormControl('')
  tagName: FormControl = new FormControl('')
  duration: FormControl = new FormControl('')
  price: FormControl = new FormControl('')
  file: FormControl = new FormControl('')
  fileSource: FormControl = new FormControl('')
  description: FormControl = new FormControl('')

  constructor(private giftCertificateService: GiftCertificateService) {
  }

  ngOnInit(): void {
    this.newForm = new FormGroup({
      certName: this.certName,
      tagName: this.tagName,
      duration: this.duration,
      price: this.price,
      file: this.file,
      fileSource: this.fileSource,
      description: this.description
    })
  }

  onFileSelect(e: any) {
    let files: FileList = e.target.files
    this.newForm.patchValue({
      fileSource: files[0]
    })
  }

  onSubmit() {
    const formData = new FormData();
    for (const key of Object.keys(this.newForm.value)) {
      formData.append(key, this.newForm.value[key])
    }
    formData.append('fileSource', this.newForm.get('fileSource')?.value)
    formData.set('duration', Math.ceil((new Date(this.duration.value).getTime() - new Date().getTime()) / (1000 * 3600 * 24)).toString())
    this.giftCertificateService.createCertificate(formData).subscribe()
  }
}
