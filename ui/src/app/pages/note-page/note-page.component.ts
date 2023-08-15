import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {HistoryService} from "../../services/history.service";
import {Note} from "../../models/Note";
import {MatCardModule} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";

@Component({
  selector: 'app-note-page',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatInputModule, MatSelectModule],
  templateUrl: './note-page.component.html',
  styleUrls: ['./note-page.component.css'],
  providers: [
    HistoryService
  ]
})
export class NotePageComponent implements OnInit {
note!: Note;

  constructor(private route: ActivatedRoute, private historyService: HistoryService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.historyService.findNote(<string>params.get('id'))
        .subscribe((note) => {
          this.note = note;
        });
    });
  }
}
