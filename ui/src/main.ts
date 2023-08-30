import { importProvidersFrom } from '@angular/core';
import { AppComponent } from './app/app.component';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {provideRouter, Routes} from "@angular/router";
import {HttpClient, HttpClientModule} from "@angular/common/http";

export const routes: Routes = [
  {
    path:'', redirectTo: 'mediscreen-abernathy', pathMatch: 'full'
  },
  {
    path:'mediscreen-abernathy',
    loadComponent: () => import('./app/general/pages/home-page/home-page.component').then(module => module.HomePageComponent)
  },
  {
    path:'mediscreen-abernathy/patients',
    loadComponent: () => import('./app/features/patients/pages/patients-page/patients-page.component').then(module => module.PatientsPageComponent)
  },
  {
    path:'mediscreen-abernathy/patient-record/:family/:given',
    loadComponent: () => import('./app/features/patients/pages/patient-record-page/patient-record-page.component').then(module => module.PatientRecordPageComponent)
  },
  {
    path: 'mediscreen-abernathy/patient-note/:id/:patientId/:family/:given',
    loadComponent: () => import('./app/features/history/note-page/note-page.component').then(module => module.NotePageComponent)
  },
  {
    path: '**',
    loadComponent: () => import('./app/general/pages/page-not-found/page-not-found.component').then(module => module.PageNotFoundComponent)
  }
]

bootstrapApplication(AppComponent, {
    providers: [importProvidersFrom(BrowserModule, BrowserAnimationsModule, HttpClient, HttpClientModule),
    provideRouter(routes)]
})
  .catch(err => console.error(err));
