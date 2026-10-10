import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { Manutencao, ManutencaoCadastro, ManutencaoAlteracao } from '../models/manutencao.model';

@Injectable({ providedIn: 'root' })
export class ManutencaoService {
  constructor(private http: HttpClient) {}

  listarPorVeiculo(veiculoId: number): Observable<Manutencao[]> {
    return this.http.get<Manutencao[]>(`${API_BASE_URL}/api/manutencoes/veiculo/${veiculoId}`);
  }

  listarPorUsuario(usuarioId: number): Observable<Manutencao[]> {
    return this.http.get<Manutencao[]>(`${API_BASE_URL}/api/manutencoes/usuario/${usuarioId}`);
  }

  buscarPorId(veiculoId: number, id: number): Observable<Manutencao | undefined> {
    return this.listarPorVeiculo(veiculoId).pipe(map(lista => lista.find(m => m.id === id)));
  }

  cadastrar(dto: ManutencaoCadastro): Observable<Manutencao> {
    return this.http.post<Manutencao>(`${API_BASE_URL}/api/manutencoes`, dto);
  }

  alterar(id: number, dto: ManutencaoAlteracao): Observable<Manutencao> {
    return this.http.put<Manutencao>(`${API_BASE_URL}/api/manutencoes/${id}`, dto);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE_URL}/api/manutencoes/${id}`);
  }
}
