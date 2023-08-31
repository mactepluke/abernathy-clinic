import { Injectable } from '@angular/core';
import {MatSnackBar, MatSnackBarRef} from "@angular/material/snack-bar";
import {SnackBarComponent} from "../components/snack-bar/snack-bar.component";


@Injectable()
export class DisplayService {

  constructor(private snackBar: MatSnackBar) {
  }

  openSnackBar(message: string): void {
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 3000,
      data: message
    });
  }
}
