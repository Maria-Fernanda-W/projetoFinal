import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';

interface LoginResposta {
  usuario: { id: number; nome: string; email: string };
  token: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  cadastrar(nome: string, email: string, senha: string): Observable<any> {
    return this.http.post(`${API_BASE_URL}/api/usuarios`, { nome, email, senha });
  }

  login(email: string, senha: string): Observable<LoginResposta> {
    return this.http.post<LoginResposta>(`${API_BASE_URL}/api/usuarios/login`, { email, senha })
      .pipe(tap(resposta => {
        localStorage.setItem('token', resposta.token);
        localStorage.setItem('usuarioId', String(resposta.usuario.id));
        localStorage.setItem('usuarioNome', resposta.usuario.nome);
      }));
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('usuarioId');
    localStorage.removeItem('usuarioNome');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUsuarioId(): number {
    return Number(localStorage.getItem('usuarioId'));
  }

  getUsuarioNome(): string | null {
    return localStorage.getItem('usuarioNome');
  }
}
