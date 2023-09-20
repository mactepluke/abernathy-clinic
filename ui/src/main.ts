import {importProvidersFrom} from '@angular/core';
import {AppComponent} from './app/app.component';
import {BrowserModule, bootstrapApplication} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {provideRouter, Routes, withHashLocation} from "@angular/router";
import {
  HttpClient,
  HttpClientModule,
  provideHttpClient, withInterceptors,
} from "@angular/common/http";
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";
import {AuthInterceptor} from "./app/auth/interceptors/auth.interceptor";
import {AuthService} from "./app/auth/services/auth.service";
import {AuthGuard} from "./app/auth/guards/AuthGuard";

export const routes: Routes = [
  {
    path: '', redirectTo: 'mediscreen-abernathy', pathMatch: 'full'
  },
  {
    path: 'mediscreen-abernathy',
    title: 'Abernathy Home',
    loadComponent: () => import('./app/general/pages/home-page/home-page.component').then(module => module.HomePageComponent)
  },
  {
    path: 'mediscreen-abernathy/patients',
    title: 'Abernathy Patients List',
    canActivate: [AuthGuard()],
    loadComponent: () => import('./app/features/patients/pages/patients-page/patients-page.component').then(module => module.PatientsPageComponent)
  },
  {
    path: 'mediscreen-abernathy/contact',
    title: 'Abernathy Contact',
    loadComponent: () => import('./app/general/pages/contact-page/contact-page.component').then(module => module.ContactPageComponent)
  },
  {
    path: 'mediscreen-abernathy/dashboard',
    title: 'Abernathy Dashboard',
    loadComponent: () => import('./app/general/pages/dashboard-page/dashboard-page.component').then(module => module.DashboardPageComponent)
  },
  {
    path: 'mediscreen-abernathy/create-account',
    title: 'Abernathy Register',
    loadComponent: () => import('./app/general/pages/create-account-page/create-account-page.component').then(module => module.CreateAccountPageComponent)
  },
  {
    path: 'mediscreen-abernathy/patient-record/:family/:given',
    title: 'Abernathy Patient Record',
    loadComponent: () => import('./app/features/patients/pages/patient-record-page/patient-record-page.component').then(module => module.PatientRecordPageComponent)
  },
  {
    path: 'mediscreen-abernathy/patient-note/:id/:patientId/:family/:given',
    title: 'Abernathy Patient Note',
    loadComponent: () => import('./app/features/history/pages/note-page/note-page.component').then(module => module.NotePageComponent)
  },
  {
    path: '**',
    title: 'Abernathy Not found',
    loadComponent: () => import('./app/general/pages/page-not-found/page-not-found.component').then(module => module.PageNotFoundComponent)
  }
]

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(BrowserModule, BrowserAnimationsModule, HttpClient, HttpClientModule),
    provideRouter(routes, withHashLocation()),
    provideHttpClient(
      withInterceptors([AuthInterceptor]),
    ),
    {provide: JWT_OPTIONS, useValue: JWT_OPTIONS},
    JwtHelperService,
    AuthService
  ]
})
  .catch(err => console.error(err));
