import {
  provideHttpClient,
  withInterceptors, withFetch
} from '@angular/common/http';
import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
// Example of an authentication interceptor
import {routes} from './app.routes';
import {authInterceptorFn} from './auth/auth-interceptor.service';


export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withFetch(), withInterceptors([authInterceptorFn])),
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
  ],
};
