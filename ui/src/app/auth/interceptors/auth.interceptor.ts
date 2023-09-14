import {inject} from '@angular/core';
import {
  HttpRequest,
  HttpHandlerFn
} from '@angular/common/http';
import {AuthService} from "../services/auth.service";
import {Security} from "../security/security";

export function AuthInterceptor(req: HttpRequest<any>, next: HttpHandlerFn) {

  if (req.headers.has('Permitted')) {
    return next(req);
  }

  const authService = inject(AuthService);

  const credentials = btoa(authService.currentUser.username + ':' + authService.currentUser.password);

  const modifiedReq = req.clone({
    headers: req.headers
      .set('Authorization', `Basic ${credentials}`)
  });
  return next(modifiedReq);
}

