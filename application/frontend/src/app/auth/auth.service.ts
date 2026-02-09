import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '../../environments/environment';

interface AuthResponse {
  jwt: string;
  aliasType: string;   // Add these fields to the interface
  aliasValue: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private aliasType: string | null = null;
  private aliasValue: string | null = null;
  private jwtHelper = new JwtHelperService();

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  getAliasType(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('aliasType');
    }
    return null;
  }

  getAliasValue(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('aliasValue');
    }
    return null;
  }

  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<AuthResponse>(`${environment.apiUrl}/auth/login`, body, { headers }).pipe(
      tap(response => {
        console.log('Response from backend:', response);
        this.setSession(response.jwt);
        this.setAlias(response.aliasType, response.aliasValue);
      })
    );
  }

  private setSession(token: string): void {
    console.log('Token:', token);
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('authToken', token);
    }
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('authToken');
      localStorage.removeItem('aliasType');
      localStorage.removeItem('aliasValue');
    }
    this.router.navigate(['/login']);
  }

  private setAlias(aliasType: string, aliasValue: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('aliasType', aliasType);
      localStorage.setItem('aliasValue', aliasValue);
    }
  }

  isLoggedIn(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('authToken');
      return token != null && !this.jwtHelper.isTokenExpired(token);
    }
    return false;
  }

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('authToken');
    }
    return null;
  }
}


// import { Injectable } from '@angular/core';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//
//   constructor() { }
// }
