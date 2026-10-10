import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { VeiculoService } from '../../../core/services/veiculo.service';
import { Veiculo } from '../../../core/models/veiculo.model';

@Component({
  selector: 'app-veiculo-list',
  standalone: true,
  imports: [MatCardModule],
  templateUrl: './veiculo-list.component.html',
  styleUrl: './veiculo-list.component.scss'
})
export class VeiculoListComponent implements OnInit {
  veiculos: Veiculo[] = [];

  constructor(private veiculoService: VeiculoService, private router: Router) {}

  ngOnInit(): void {
    this.veiculoService.listar().subscribe(veiculos => this.veiculos = veiculos);
  }

  formatarKm(km: number): string {
    return km.toLocaleString('pt-BR') + 'km';
  }

  abrir(veiculo: Veiculo): void {
    this.router.navigate(['/veiculos', veiculo.id]);
  }
}
