import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {UserService} from "../../../services/user/user.service";
import {StorageService} from "../../../services/storage-service/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup
  login: FormControl = new FormControl('', Validators.required)
  password: FormControl = new FormControl('', Validators.required)
  confirmPassword: FormControl = new FormControl('', Validators.required)

  constructor(private userService: UserService, private storageService: StorageService, private router: Router) {
  }


  ngOnInit(): void {
    this.registerForm = new FormGroup({
        login: this.login,
        password: this.password,
        confirmPassword: this.confirmPassword
  })
  this.registerForm.addValidators(
    matchValidator(this.registerForm.get('password'), this.registerForm.get('confirmPassword'))
  )
  }

  onSubmit(form: FormGroup) {
    this.userService.signup(form.value.login, form.value.password)
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


function matchValidator(
  control: AbstractControl | null,
  controlTwo: AbstractControl | null
): ValidatorFn {
  return () => {
    console.log(control?.value !== controlTwo?.value)
    if (control && controlTwo && control.value !== controlTwo.value)
      return { match_error: 'Passwords do not match' };
    return null;
  };
}
