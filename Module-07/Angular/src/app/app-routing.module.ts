import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./main/pages/main/main.component";
import {ItemDetailsComponent} from "./main/pages/item-details/item-details.component";
import {NewCouponComponent} from "./main/pages/new-coupon/new-coupon.component";
import {CheckoutComponent} from "./main/pages/checkout/checkout.component";
import {ErrorComponent} from "./main/pages/error/error.component";
import {adminAuthGuard} from "./services/guards/admin-auth.guard";

const routes: Routes = [
  {path: '', component: MainComponent},
  {path: 'auth', loadChildren : () => import("./main/auth/auth-routing.module").then(m => m.AuthRoutingModule)},
  {path: 'item-details/:id', component: ItemDetailsComponent},
  {path: 'new-coupon',canActivate: [adminAuthGuard], component: NewCouponComponent},
  {path: 'checkout', component: CheckoutComponent},
  {path: '**', component: ErrorComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
