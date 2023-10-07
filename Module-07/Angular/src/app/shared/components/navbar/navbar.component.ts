import {Component, ElementRef, ViewChild} from '@angular/core';
import {StorageService} from "../../../services/storage-service/storage.service";
import {UserService} from "../../../services/user/user.service";
import {Router} from "@angular/router";
import {debounceTime} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.sass']
})
export class NavbarComponent {
  @ViewChild('search')
  search: ElementRef

  constructor(private storageService: StorageService, private userService: UserService, private router: Router) {
  }

  isAuthorized(): boolean {
    return this.storageService.getToken() != null
  }

  signout() {
    this.userService.signout()
    this.storageService.clearToken()
    this.storageService.clearUser()
  }

  timeout: any = null
  doSearch() {
    if (this.timeout) {
      clearTimeout(this.timeout)
    }
    this.timeout = setTimeout(() =>{
      this.router.navigate(['/'], {queryParams:{
          search: this.search.nativeElement.value
        }})
    }, 1000)
  }
}
