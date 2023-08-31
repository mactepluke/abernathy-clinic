import {AfterViewInit, Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {HistoryService} from "../../services/history.service";
import {Note} from "../../models/Note";
import {MatCardModule} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {DisplayService} from "../../../../general/services/display.service";

@Component({
  selector: 'app-note-page',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatInputModule, MatSelectModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './note-page.component.html',
  styleUrls: ['./note-page.component.css'],
  providers: [
    HistoryService,
    FormBuilder,
    DisplayService
  ]
})
export class NotePageComponent implements OnInit, AfterViewInit {
  note!: Note;
  newNote!: Note;
  form!: FormGroup;

  constructor(private route: ActivatedRoute,
              private historyService: HistoryService,
              private formBuilder: FormBuilder,
              private router: Router,
              private displayService: DisplayService) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      title: [null, [Validators.required, Validators.pattern("^.{1,100}$")]],
      content: [null, [Validators.required, Validators.pattern("^[\\s\\S]{1,8000}$")]],
    });

    this.route.paramMap.subscribe((params: ParamMap): void => {

      let noteId: string = <string>params.get('id');

      if (noteId != '-1') {
        this.historyService.findNote(noteId)
          .subscribe((note: Note): void => {
            this.note = note;
          });
      } else {
        this.note = {
          id: '-1',
          patientId: <string>params.get('patientId'),
          title: 'Untitled',
          dateTime: new Date(),
          content: ''
        }
      }
      this.reset();
    });
  }

  ngAfterViewInit(): void {
    this.reset();
  }

  onReset(): void {
    this.newNote = this.form.value;
    this.newNote.id = this.note.id;
    this.newNote.patientId = this.note.patientId;
    this.newNote.dateTime = new Date();

    if (this.historyService.equals(this.newNote, this.note)) {
      console.log('No fields changed')
      this.displayService.openSnackBar('No fields changed');
    } else {
      this.reset();
      console.log('Fields reset to initial values')
      this.displayService.openSnackBar('Fields reset to initial values');
    }
  }

  reset(): void {
    this.form.setValue({
      title: this.note.title,
      content: this.note.content,
    });
  }

  onBack() {
    this.route.paramMap.subscribe((params: ParamMap): void => {
      this.router.navigate(['mediscreen-abernathy/patient-record', <string>params.get('family'), <string>params.get('given')])
    })
  }

  onSave() {
    this.newNote = this.form.value;
    this.newNote.id = this.note.id;
    this.newNote.patientId = this.note.patientId;
    this.newNote.dateTime = new Date();

    if (this.historyService.equals(this.newNote, this.note)) {
      console.log('No fields changed')
      this.displayService.openSnackBar('No fields changed');
    } else {

      if (this.newNote.id === '-1') {
        this.historyService.addNote(this.newNote).subscribe((note: Note): void => {
          this.note = note;
          this.reset();
          console.log('Note created successfully');
          this.displayService.openSnackBar('Note created successfully');
        });
      } else {
        this.historyService.updateNote(this.newNote).subscribe((note: Note): void => {
          this.note = note;
          this.reset();
          console.log('Note updated successfully');
          this.displayService.openSnackBar('Note updated successfully');
        });
      }
    }

  }
}
