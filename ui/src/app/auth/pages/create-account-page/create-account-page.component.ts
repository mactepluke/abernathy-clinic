import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
  ValidatorFn
} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {User} from "../../User";
import {DisplayService} from "../../../shared/services/display.service";
import {Patient} from "../../../features/patients/models/Patient";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-create-account-page',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    RouterLink,
    RouterLinkActive,
    MatIconModule
  ],
  providers: [
    DisplayService,
    UserService
  ],
  templateUrl: './create-account-page.component.html',
  styleUrls: ['./create-account-page.component.css']
})
export class CreateAccountPageComponent implements OnInit {
  form!: FormGroup;
  hide = true;
  newUser!: User;

  constructor(private formBuilder: FormBuilder,
              private displayService: DisplayService,
              private userService: UserService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
        username: [null, [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ- ]{3,50}$')]],
        password: [null, [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&]).{8,30}')]],
        confirmedPassword: [null, [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&]).{8,30}')]],
      },
      {
        updateOn: 'blur',
        validators: this.checkPasswords
      });
  }

  checkPasswords: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
    // @ts-ignore
    let pass = group.get('password').value;
    // @ts-ignore
    let confirmPass = group.get('confirmedPassword').value;
    return pass === confirmPass ? null : {notSame: true}
  }

  onCreate() {
    this.newUser = this.form.value;

    this.userService.createUser(this.newUser)
      .subscribe({
          next: (user) =>
            this.router.navigate(['mediscreen-abernathy/login'])
              .then(() => this.displayService.openSnackBar(`User \'${user.username}\' has been created!`)),
          error: () => this.displayService.openSnackBar(`Could not create user with username: \'${this.newUser.username}\'`)
        }
      )
  }


}
