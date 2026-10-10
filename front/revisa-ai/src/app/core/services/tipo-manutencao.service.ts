import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { TipoManutencao } from '../models/tipo-manutencao.model';

@Injectable({ providedIn: 'root' })
export class TipoManutencaoService {
  constructor(private http: HttpClient) {}

  listar(): Observable<TipoManutencao[]> {
    return this.http.get<TipoManutencao[]>(`${API_BASE_URL}/api/tipos-manutencao`);
  }
}
