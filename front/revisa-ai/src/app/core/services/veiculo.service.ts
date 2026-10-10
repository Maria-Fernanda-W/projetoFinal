import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { Veiculo, VeiculoCadastro, VeiculoAlteracao } from '../models/veiculo.model';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class VeiculoService {
  constructor(private http: HttpClient, private auth: AuthService) {}

  listar(): Observable<Veiculo[]> {
    const usuarioId = this.auth.getUsuarioId();
    return this.http.get<Veiculo[]>(`${API_BASE_URL}/api/veiculos/usuario/${usuarioId}`);
  }

  buscarPorId(id: number): Observable<Veiculo | undefined> {
    return this.listar().pipe(map(veiculos => veiculos.find(v => v.id === id)));
  }

  cadastrar(dto: VeiculoCadastro): Observable<Veiculo> {
    return this.http.post<Veiculo>(`${API_BASE_URL}/api/veiculos`, dto);
  }

  alterar(id: number, dto: VeiculoAlteracao): Observable<Veiculo> {
    return this.http.put<Veiculo>(`${API_BASE_URL}/api/veiculos/${id}`, dto);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE_URL}/api/veiculos/${id}`);
  }
}
