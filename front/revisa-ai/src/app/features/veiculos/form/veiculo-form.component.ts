import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { VeiculoService } from '../../../core/services/veiculo.service';
import { FormHeaderComponent } from '../../../shared/form-header/form-header.component';
import { TabMemoryService } from '../../../core/services/tab-memory.service';

@Component({
  selector: 'app-veiculo-form',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, FormHeaderComponent],
  templateUrl: './veiculo-form.component.html',
  styleUrl: './veiculo-form.component.scss'
})
export class VeiculoFormComponent implements OnInit {
  edicao = false;
  veiculoId: number | null = null;
  voltarPara: any[] = ['/veiculos'];

  nome = '';
  placa = '';
  marca = '';
  modelo = '';
  ano: number | null = null;
  quilometragemInicial: number | null = null;
  erro = '';

  constructor(
    private veiculoService: VeiculoService,
    private router: Router,
    private route: ActivatedRoute,
    private tabMemory: TabMemoryService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.edicao = true;
      this.veiculoId = Number(idParam);
      this.voltarPara = ['/veiculos', this.veiculoId];

      this.veiculoService.buscarPorId(this.veiculoId).subscribe(veiculo => {
        if (veiculo) {
          this.nome = veiculo.nome;
          this.placa = veiculo.placa;
          this.marca = veiculo.marca;
          this.modelo = veiculo.modelo;
          this.ano = veiculo.ano;
        }
      });
    } else {
      this.voltarPara = this.tabMemory.obterUltimaTab();
    }
  }

  salvar(form: NgForm): void {
    this.erro = '';

    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }

    if (this.edicao && this.veiculoId) {
      this.veiculoService.alterar(this.veiculoId, {
        nome: this.nome,
        placa: this.placa,
        marca: this.marca,
        modelo: this.modelo,
        ano: this.ano!
      }).subscribe({
        next: () => this.router.navigate(['/veiculos', this.veiculoId]),
        error: (e) => this.erro = e.error?.erro ?? 'Erro ao atualizar veículo.'
      });
    } else {
      this.veiculoService.cadastrar({
        nome: this.nome,
        placa: this.placa,
        marca: this.marca,
        modelo: this.modelo,
        ano: this.ano!,
        quilometragemInicial: this.quilometragemInicial!
      }).subscribe({
        next: () => this.router.navigate(this.tabMemory.obterUltimaTab()),
        error: (e) => this.erro = e.error?.erro ?? 'Erro ao cadastrar veículo.'
      });
    }
  }
}
