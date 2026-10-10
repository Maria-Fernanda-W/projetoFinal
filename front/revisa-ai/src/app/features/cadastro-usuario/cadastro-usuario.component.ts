import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-cadastro-usuario',
  standalone: true,
  imports: [FormsModule, RouterLink, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './cadastro-usuario.component.html',
  styleUrl: './cadastro-usuario.component.scss'
})
export class CadastroUsuarioComponent {
  nome = '';
  email = '';
  senha = '';
  erro = '';
  sucesso = false;

  constructor(private auth: AuthService, private router: Router) {}

  cadastrar(form: NgForm): void {
    this.erro = '';

    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }

    this.auth.cadastrar(this.nome, this.email, this.senha).subscribe({
      next: () => {
        this.sucesso = true;
        setTimeout(() => this.router.navigate(['/login']), 1200);
      },
      error: (e) => {
        if (e.status === 409) {
          this.erro = 'Este e-mail já está cadastrado.';
        } else if (e.error?.senha) {
          this.erro = e.error.senha;
        } else {
          this.erro = 'Erro ao cadastrar. Verifique os dados.';
        }
      }
    });
  }
}
