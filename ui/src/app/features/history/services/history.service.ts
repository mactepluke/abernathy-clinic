import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {LightNote} from "../models/LightNote";
import {Note} from "../models/Note";

@Injectable()
export class HistoryService {

  constructor(private http: HttpClient) { }

  findHistory(patientId: number): Observable<LightNote[]> {
    return this.http.get<LightNote[]>(`${environment.gateway}/history/getHistory?patientId=${patientId}`);
  }

  findNote(id: string): Observable<Note> {
    return this.http.get<Note>(`${environment.gateway}/history/getNote?id=${id}`);
  }

  addNote(note: Note): Observable<Note> {
    return this.http.post<Note>(`${environment.gateway}/history/addNote`,
      {
        "patientId": `${note.patientId}`,
        "title": `${note.title}`,
        "content": `${note.content}`
      });
  }

  updateNote(note: Note): Observable<Note> {
    return this.http.put<Note>(`${environment.gateway}/history/updateNote`,
      {
        "id": `${note.id}`,
        "title": `${note.title}`,
        "content": `${note.content}`
      });
  }

  deleteNote(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.gateway}/history/deleteNote?id=${id}`);
  }

  deleteAllNotes(patientId: string): Observable<void> {
    return this.http.delete<void>(`${environment.gateway}/history/deleteAllNotes?patientId=${patientId}`);
  }

  equals(note1: Note, note2: Note): boolean {
    return note1.id === note2.id
      && note1.title === note2.title
      && note1.content === note2.content
      && note1.patientId === note2.patientId
  }
}
