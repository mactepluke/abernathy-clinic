import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-contact-page',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatCardModule, RouterLink, RouterLinkActive, MatIconModule],
  templateUrl: './contact-page.component.html',
  styleUrls: ['./contact-page.component.css']
})
export class ContactPageComponent {

}
