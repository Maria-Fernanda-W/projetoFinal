import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SlicePipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { VeiculoService } from '../../../core/services/veiculo.service';
import { ManutencaoService } from '../../../core/services/manutencao.service';
import { Veiculo } from '../../../core/models/veiculo.model';
import { Manutencao } from '../../../core/models/manutencao.model';
import { FormHeaderComponent } from '../../../shared/form-header/form-header.component';
import { ConfirmDialogComponent } from '../../../shared/confirm-dialog/confirm-dialog.component';
import { TabMemoryService } from '../../../core/services/tab-memory.service';

@Component({
  selector: 'app-veiculo-detalhe',
  standalone: true,
  imports: [SlicePipe, RouterLink, MatCardModule, MatButtonModule, MatIconModule, FormHeaderComponent],
  templateUrl: './veiculo-detalhe.component.html',
  styleUrl: './veiculo-detalhe.component.scss'
})
export class VeiculoDetalheComponent implements OnInit {
  veiculo: Veiculo | undefined;
  manutencoes: Manutencao[] = [];
  veiculoId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private veiculoService: VeiculoService,
    private manutencaoService: ManutencaoService,
    private dialog: MatDialog,
    public tabMemory: TabMemoryService
  ) {}

  ngOnInit(): void {
    this.veiculoId = Number(this.route.snapshot.paramMap.get('id'));
    this.carregar();
  }

  carregar(): void {
    this.veiculoService.buscarPorId(this.veiculoId).subscribe(veiculo => this.veiculo = veiculo);
    this.manutencaoService.listarPorVeiculo(this.veiculoId).subscribe(manutencoes => this.manutencoes = manutencoes);
  }

  formatarKm(km: number): string {
    return km.toLocaleString('pt-BR') + 'km';
  }

  formatarValor(valor: number): string {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }

  editar(): void {
    this.router.navigate(['/veiculos', this.veiculoId, 'editar']);
  }

  excluir(): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        titulo: 'Excluir veículo',
        mensagem: `Tem certeza que deseja excluir "${this.veiculo?.nome}"? Isso também apaga todo o histórico e manutenções deste veículo.`
      }
    });

    dialogRef.afterClosed().subscribe(confirmado => {
      if (confirmado) {
        this.veiculoService.deletar(this.veiculoId).subscribe(() => this.router.navigate(this.tabMemory.obterUltimaTab()));
      }
    });
  }

  editarManutencao(m: Manutencao): void {
    this.router.navigate(['/veiculos', this.veiculoId, 'manutencoes', m.id, 'editar']);
  }

  formatarData(data: string): string {
    const [ano, mes, dia] = data.split('-');
    return `${dia}/${mes}/${ano}`;
  }
}
