import {Component, Inject, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from "@angular/material/button";
import {MAT_SNACK_BAR_DATA, MatSnackBarModule, MatSnackBarRef} from "@angular/material/snack-bar";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-snack-bar',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatSnackBarModule,
    MatIconModule
  ],
  templateUrl: './snack-bar.component.html',
  styleUrls: ['./snack-bar.component.css']
})
export class SnackBarComponent {
  snackBarRef: MatSnackBarRef<any> = inject(MatSnackBarRef);

  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: string) { }
}
