import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatListModule} from "@angular/material/list";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {User} from "../../User";
import {UserService} from "../../services/user.service";
import {AuthService} from "../../services/auth.service";
import {DisplayService} from "../../../shared/services/display.service";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatListModule,
    RouterLink,
    RouterLinkActive,
    MatIconModule
  ],
  providers:  [
    UserService,
    AuthService,
    DisplayService
  ],
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  form!: FormGroup;
  hide = true;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private userService: UserService,
              private router: Router,
              private displayService: DisplayService) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      username: [null, [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ- ]{3,50}$')]],
      password: [null, [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&]).{8,30}')]],
    });
  }

  onLogin() {
      let user: User = this.form.value;

      this.userService.loginUser(user).subscribe(
        {
          next: (user) => {
            this.authService.login(user);
            this.router.navigate(['mediscreen-abernathy/patients'])
              .then(() => this.displayService.openSnackBar(`User \'${user.username}\' is logged in!`))
          },
          error: () => this.displayService.openSnackBar(`Could not log in, try with other credentials or create a new user`)
        }
      );
  }
}
