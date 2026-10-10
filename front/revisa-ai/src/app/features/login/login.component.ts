import { Component } from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  email = '';
  senha = '';
  erro = '';

  constructor(private auth: AuthService, private router: Router) {}

  entrar(form: NgForm): void {
    this.erro = '';

    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }

    this.auth.login(this.email, this.senha).subscribe({
      next: () => this.router.navigate(['/inicio']),
      error: () => this.erro = 'E-mail ou senha inválidos.'
    });
  }
}
