import {Component, Inject, PLATFORM_ID} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {CommonModule, isPlatformBrowser} from "@angular/common";
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardContent,
    MatLabel,
    MatFormField,
    MatCardTitle,
    MatCardHeader,
    MatCard,
    MatButton,
    MatInput,
    HttpClientModule,
    CommonModule
  ],
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  loginFailed = false;
  loginErrorMessage = 'Login failed. Please check your username and password.';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }


  onSubmit() {
    if (this.loginForm.valid) {
      const loginData = this.loginForm.value;

      this.authService.login(loginData.username, loginData.password).subscribe({
        next: () => {
          console.log('Login successful, redirecting...');
          if (isPlatformBrowser(this.platformId)) {
            const redirectUrl = localStorage.getItem('redirectUrl');
            if (redirectUrl) {
              console.log('Redirecting to:', redirectUrl);
              this.router.navigate([redirectUrl]);
              localStorage.removeItem('redirectUrl');
            } else {
              console.log('Redirecting to home/dashboard');
              this.router.navigate(['/']); // Redirect to home or dashboard
            }
          } else {
             this.router.navigate(['/']);
          }
        },
        error: (error) => {
          console.error('Login failed:', error);
          this.loginFailed = true;
          this.snackBar.open(this.loginErrorMessage, 'Close', { duration: 3000 });
        }
      });
    }
  }

}
