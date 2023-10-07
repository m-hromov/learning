import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {LoginComponent} from "./auth/login/login.component";
import {MainComponent} from "./pages/main/main.component";
import {ItemDetailsComponent} from "./pages/item-details/item-details.component";
import {NewCouponComponent} from "./pages/new-coupon/new-coupon.component";
import {RegisterComponent} from "./auth/register/register.component";
import {CheckoutComponent} from "./pages/checkout/checkout.component";
import {ErrorComponent} from "./pages/error/error.component";
import {SharedModule} from "../shared/shared.module";
import {ServicesModule} from "../services/services.module";
import {ReactiveFormsModule} from "@angular/forms";
import {RouterLink} from "@angular/router";
import { CouponBoxComponent } from './pages/main/coupon-box/coupon-box.component';
import { HeadingBoxComponent } from './pages/main/heading-box/heading-box.component';



@NgModule({
  declarations: [
    LoginComponent,
    MainComponent,
    ItemDetailsComponent,
    NewCouponComponent,
    RegisterComponent,
    CheckoutComponent,
    ErrorComponent,
    CouponBoxComponent,
    HeadingBoxComponent],
    imports: [
        CommonModule,
        SharedModule,
        ServicesModule,
        NgOptimizedImage,
        ReactiveFormsModule,
        RouterLink
    ]
})
export class MainModule { }
