import {CanActivateFn, Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {inject} from "@angular/core";

export function AuthGuard(): CanActivateFn {
  return () : boolean => {
    const authService: AuthService = inject(AuthService);
    const router: Router = inject(Router);

    if (authService.isLoggedIn() ) {
      return true;
    }
    router.navigateByUrl('mediscreen-abernathy/dashboard');
    return false;
  };
}
