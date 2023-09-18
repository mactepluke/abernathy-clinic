import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatListModule,
    MatIconModule
  ],
  providers: [
    AuthService
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  form!: FormGroup;

  @Input()
  username!: string | undefined;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService) {
  }

  onLogout() : void {
    this.authService.logout();
    location.reload();
  }
}
