import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from "../../components/login/login.component";
import {AuthService} from "../../services/auth.service";
import {DashboardComponent} from "../../components/dashboard/dashboard.component";

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [
    CommonModule,
    LoginComponent,
    DashboardComponent
  ],
  providers: [
    AuthService
  ],
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.css']
})
export class DashboardPageComponent implements OnInit {
  isLoggedIn!: boolean;
  username!: string;

  constructor(private authService: AuthService) {
  }


  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.username = this.authService.getUsername();
    console.log(this.isLoggedIn);
    console.log(this.username);
  }

}
