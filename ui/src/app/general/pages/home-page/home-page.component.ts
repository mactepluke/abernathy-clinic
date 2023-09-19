import { Component } from '@angular/core';
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from "@angular/material/list";
import {RouterLink, RouterLinkActive} from "@angular/router";

@Component({
    selector: 'app-home-page',
    standalone: true,
    templateUrl: './home-page.component.html',
  imports: [
    MatButtonModule,
    MatCardModule,
    MatListModule,
    RouterLink,
    RouterLinkActive
  ],
    styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {

}
