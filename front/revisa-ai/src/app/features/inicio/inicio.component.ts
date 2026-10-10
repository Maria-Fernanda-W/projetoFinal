import { Component, OnInit } from '@angular/core';
import { SlicePipe } from '@angular/common';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { ManutencaoService } from '../../core/services/manutencao.service';
import { AuthService } from '../../core/services/auth.service';
import { Manutencao } from '../../core/models/manutencao.model';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [SlicePipe, MatCardModule],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.scss'
})
export class InicioComponent implements OnInit {
  manutencoes: Manutencao[] = [];

  constructor(private manutencaoService: ManutencaoService, private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.manutencaoService.listarPorUsuario(this.auth.getUsuarioId()).subscribe(m => this.manutencoes = m);
  }

  formatarValor(valor: number): string {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }

  formatarData(data: string): string {
    const [ano, mes, dia] = data.split('-');
    return `${dia}/${mes}/${ano}`;
  }

  editar(m: Manutencao): void {
    this.router.navigate(['/veiculos', m.veiculoId, 'manutencoes', m.id, 'editar'], {
      queryParams: { origem: 'inicio' }
    });
  }
}
