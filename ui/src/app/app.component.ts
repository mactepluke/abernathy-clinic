import { Component } from '@angular/core';
import {NgSwitch, NgSwitchDefault, NgSwitchCase, DatePipe} from '@angular/common';
import {NavigationComponent} from "./general/components/navigation/navigation.component";
import {RouterOutlet} from "@angular/router";
import {MatNativeDateModule} from "@angular/material/core";
import {MatSnackBarModule} from "@angular/material/snack-bar";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
    standalone: true,
  imports: [
    NgSwitch,
    NgSwitchDefault,
    NgSwitchCase,
    NavigationComponent,
    RouterOutlet,
    MatNativeDateModule,
    MatSnackBarModule
  ],
  providers: [
    DatePipe
  ]
})
export class AppComponent {
  title = 'Mediscreen - Abernathy';

  constructor() {}
}
