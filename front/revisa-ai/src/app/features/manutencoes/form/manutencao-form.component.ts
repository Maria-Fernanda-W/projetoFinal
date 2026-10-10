import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { ManutencaoService } from '../../../core/services/manutencao.service';
import { TipoManutencaoService } from '../../../core/services/tipo-manutencao.service';
import { VeiculoService } from '../../../core/services/veiculo.service';
import { TipoManutencao } from '../../../core/models/tipo-manutencao.model';
import { Veiculo } from '../../../core/models/veiculo.model';
import { FormHeaderComponent } from '../../../shared/form-header/form-header.component';
import { ConfirmDialogComponent } from '../../../shared/confirm-dialog/confirm-dialog.component';
import { TabMemoryService } from '../../../core/services/tab-memory.service';

@Component({
  selector: 'app-manutencao-form',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule, FormHeaderComponent],
  templateUrl: './manutencao-form.component.html',
  styleUrl: './manutencao-form.component.scss'
})
export class ManutencaoFormComponent implements OnInit {
  edicao = false;
  veiculoFixo = false;
  veiculoId: number | null = null;
  manutencaoId: number | null = null;
  veiculoNome = '';
  veiculos: Veiculo[] = [];
  tipos: TipoManutencao[] = [];
  voltarPara: any[] = ['/inicio'];
  destinoAposSalvar: any[] = ['/inicio'];

  tipoManutencaoId: number | null = null;
  nome = '';
  dataRealizacao = '';
  valor: number | null = null;
  quilometragem: number | null = null;
  localRealizado = '';
  observacoes = '';
  erro = '';

  constructor(
    private manutencaoService: ManutencaoService,
    private tipoManutencaoService: TipoManutencaoService,
    private veiculoService: VeiculoService,
    private router: Router,
    private route: ActivatedRoute,
    private tabMemory: TabMemoryService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    const veiculoIdParam = this.route.snapshot.paramMap.get('veiculoId');
    const manutencaoIdParam = this.route.snapshot.paramMap.get('id');
    const origem = this.route.snapshot.queryParamMap.get('origem');

    this.tipoManutencaoService.listar().subscribe(tipos => this.tipos = tipos);

    if (veiculoIdParam) {
      this.veiculoFixo = true;
      this.veiculoId = Number(veiculoIdParam);
      this.veiculoService.buscarPorId(this.veiculoId).subscribe(v => this.veiculoNome = v?.nome ?? '');

      if (origem === 'inicio') {
        this.voltarPara = ['/inicio'];
        this.destinoAposSalvar = ['/inicio'];
      } else {
        this.voltarPara = ['/veiculos', this.veiculoId];
        this.destinoAposSalvar = ['/veiculos', this.veiculoId];
      }
    } else {
      this.veiculoFixo = false;
      this.voltarPara = this.tabMemory.obterUltimaTab();
      this.destinoAposSalvar = this.tabMemory.obterUltimaTab();
      this.veiculoService.listar().subscribe(veiculos => this.veiculos = veiculos);
    }

    if (manutencaoIdParam && this.veiculoId) {
      this.edicao = true;
      this.manutencaoId = Number(manutencaoIdParam);

      this.manutencaoService.buscarPorId(this.veiculoId, this.manutencaoId).subscribe(m => {
        if (m) {
          this.nome = m.nome;
          this.dataRealizacao = m.dataRealizacao;
          this.valor = m.valor;
          this.quilometragem = m.quilometragem;
          this.localRealizado = m.localRealizado ?? '';
          this.observacoes = m.observacoes ?? '';
          const tipo = this.tipos.find(t => t.nome === m.tipoManutencaoNome);
          this.tipoManutencaoId = tipo?.id ?? null;
        }
      });
    }
  }

  salvar(form: NgForm): void {
    this.erro = '';

    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }

    if (this.edicao && this.manutencaoId) {
      this.manutencaoService.alterar(this.manutencaoId, {
        tipoManutencaoId: this.tipoManutencaoId!,
        nome: this.nome,
        dataRealizacao: this.dataRealizacao,
        valor: this.valor!,
        localRealizado: this.localRealizado || undefined,
        observacoes: this.observacoes || undefined
      }).subscribe({
        next: () => this.router.navigate(this.destinoAposSalvar),
        error: (e) => this.erro = e.error?.erro ?? 'Erro ao atualizar manutenção.'
      });
    } else {
      this.manutencaoService.cadastrar({
        veiculoId: this.veiculoId!,
        tipoManutencaoId: this.tipoManutencaoId!,
        nome: this.nome,
        dataRealizacao: this.dataRealizacao,
        valor: this.valor!,
        quilometragem: this.quilometragem ?? undefined,
        localRealizado: this.localRealizado || undefined,
        observacoes: this.observacoes || undefined
      }).subscribe({
        next: () => this.router.navigate(this.destinoAposSalvar),
        error: (e) => this.erro = e.error?.erro ?? 'Erro ao cadastrar manutenção.'
      });
    }
  }

  excluir(): void {
    if (!this.manutencaoId) return;

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        titulo: 'Excluir manutenção',
        mensagem: `Tem certeza que deseja excluir "${this.nome}"?`
      }
    });

    dialogRef.afterClosed().subscribe(confirmado => {
      if (confirmado) {
        this.manutencaoService.deletar(this.manutencaoId!).subscribe(() => this.router.navigate(this.destinoAposSalvar));
      }
    });
  }
}
