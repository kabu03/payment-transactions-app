import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { AuthService } from './auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.authService.isLoggedIn()) {
      return true; // Allow access
    } else {
      // Ensure we're in a browser environment before accessing localStorage
      if (isPlatformBrowser(this.platformId)) {
        // Store the attempted URL for redirecting after login
        localStorage.setItem('redirectUrl', state.url);
      }
      this.router.navigate(['/login']); // Redirect to login
      return false; // Block access
    }
  }
}
