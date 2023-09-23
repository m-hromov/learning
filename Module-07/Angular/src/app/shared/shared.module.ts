import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavbarComponent} from './components/navbar/navbar.component';
import {MatMenuModule} from "@angular/material/menu";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterLink} from "@angular/router";
import { CurrencyPipe } from './pipes/currency.pipe';


@NgModule({
  declarations: [
    NavbarComponent,
    CurrencyPipe
  ],
  exports: [
    NavbarComponent,
    CurrencyPipe
  ],
  imports: [
    CommonModule,
    MatMenuModule,
    MatButtonModule,
    MatIconModule,
    BrowserAnimationsModule,
    RouterLink
  ]
})
export class SharedModule {
}
