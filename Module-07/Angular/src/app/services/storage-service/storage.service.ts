import {Injectable} from '@angular/core';
import {User} from "../../shared/models/user.model";
import {GiftCertificate} from "../../shared/models/gift-certificate.model";

const USER_TOKEN_KEY = 'user-token-key'
const USER_KEY = 'user-key'
const CART_KEY = 'cart-key'

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  setToken(token: string) {
    window.localStorage.setItem(USER_TOKEN_KEY, token);
  }

  getToken(): string | null {
    return window.localStorage.getItem(USER_TOKEN_KEY);
  }

  clearToken() {
    window.localStorage.removeItem(USER_TOKEN_KEY)
  }

  clearUser() {
    window.localStorage.removeItem(USER_KEY)
  }

  setUser(user: User) {
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser(): User | null {
    const user = window.localStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }

  addGiftCertificates(...newGiftCertificates: GiftCertificate[]) {
    let giftCertificates = this.getGiftCertificates()
    giftCertificates.push(...newGiftCertificates)
    window.localStorage.setItem(CART_KEY, JSON.stringify(giftCertificates));
  }

  clearGiftCertificates() {
    window.localStorage.removeItem(CART_KEY);
  }

  getGiftCertificates(): GiftCertificate[] {
    const giftCertificates = window.localStorage.getItem(CART_KEY);
    if (giftCertificates) {
      return JSON.parse(giftCertificates);
    }
    return [];
  }
}
