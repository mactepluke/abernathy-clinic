import {inject} from '@angular/core';
import {
  HttpRequest,
  HttpHandlerFn, HttpHeaders, HttpErrorResponse
} from '@angular/common/http';
import {tap} from "rxjs";
import {Router} from "@angular/router";

export function AuthInterceptor(req: HttpRequest<any>, next: HttpHandlerFn) {

  const router = inject(Router);

  let httpHeaders = new HttpHeaders();

  let jwtToken = localStorage.getItem('jwtToken');
  if (jwtToken) {
    httpHeaders = httpHeaders.append('Authorization', `Bearer ${jwtToken}`);
  }

  const modifiedReq = req.clone({
    headers: httpHeaders
  });

  return next(modifiedReq).pipe(tap(
    (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status !== 401) {
          return;
        }
        router.navigate(['mediscreen-abernathy/login']);
      }
    }));
}

