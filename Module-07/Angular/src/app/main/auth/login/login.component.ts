import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../services/user/user.service";
import {StorageService} from "../../../services/storage-service/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit{
  loginForm!: FormGroup
  login: FormControl = new FormControl('')
  password: FormControl = new FormControl('')

  constructor(private userService: UserService, private storageService: StorageService, private router: Router) {
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      login: this.login,
      password: this.password
    })
  }

  onSubmit() {
    this.userService.signin(this.login.value, this.password.value)
      .subscribe(value => {
        this.storageService.setToken(value.accessToken)
        this.userService.getCurrentUser()
          .subscribe(value => {
            this.storageService.setUser(value)
          })
        this.router.navigate([''])
      })
  }

}
