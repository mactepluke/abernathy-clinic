import { Component } from '@angular/core';
import { NgSwitch, NgSwitchDefault, NgSwitchCase } from '@angular/common';
import {NavigationComponent} from "./components/navigation/navigation.component";
import {Router, RouterOutlet} from "@angular/router";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
    standalone: true,
  imports: [NgSwitch, NgSwitchDefault, NgSwitchCase, NavigationComponent, RouterOutlet]
})
export class AppComponent {
  title = 'Mediscreen - Abernathy';

  constructor(private route: Router) {}
}
