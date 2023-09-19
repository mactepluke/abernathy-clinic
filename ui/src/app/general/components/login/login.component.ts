import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatListModule} from "@angular/material/list";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {AuthService} from "../../../auth/services/auth.service";
import {DisplayService} from "../../../shared/services/display.service";
import {User} from "../../../auth/models/User";

@Component({
  selector: 'app-login',
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
  providers: [
    AuthService,
    DisplayService
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  user!: User;
  hide: boolean = true;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private router: Router,
              private displayService: DisplayService) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      username: [null, [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ- ]{3,50}$')]],
      password: [null, [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&]).{8,30}')]],
    });
  }

  onLogin() : void {
    this.user = this.form.value;

    this.authService.login(this.user).subscribe(
      {
        next: () => {
          this.router.navigate(['mediscreen-abernathy/patients'])
            .then(() => this.displayService.openSnackBar(`User \'${this.user.username}\' is logged in!`))
        },
        error: () => this.displayService.openSnackBar(`Could not log in, try with other credentials or create a new user`)
      }
    );
  }
}
