import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Patient} from "../models/Patient";
import {environment} from "../../environments/environment";
import {LightNote} from "../models/LightNote";
import {Note} from "../models/Note";

@Injectable()
export class HistoryService {

  constructor(private http: HttpClient) { }

  findHistory(patientId: number): Observable<LightNote[]> {
    return this.http.get<LightNote[]>(`${environment.mhistoryUrl}/history/getHistory?patientId=${patientId}`);
  }

  findNote(id: string): Observable<Note> {
    return this.http.get<Note>(`${environment.mhistoryUrl}/history/getNote?id=${id}`);
  }

  addNote(note: Note): Observable<Note> {
    return this.http.post<Note>(`${environment.mhistoryUrl}/history/addNote?patientId=${note.patientId}&title=${note.title}&content=${note.content}`, '');
  }

  updateNote(id: string, note: Note): Observable<Note> {
    return this.http.put<Note>(`${environment.mhistoryUrl}/history/updateNote?id=${note.id}&title=${note.title}&content=${note.content}`, '');
  }

  deleteNote(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.mhistoryUrl}/history/deleteNote?id=${id}`);
  }

  deleteAllNotes(patientId: string): Observable<void> {
    return this.http.delete<void>(`${environment.mhistoryUrl}/history/deleteAllNotes?patientId=${patientId}`);
  }
}
