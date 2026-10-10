import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { CadastroUsuarioComponent } from './features/cadastro-usuario/cadastro-usuario.component';
import { ShellComponent } from './features/shell/shell.component';
import { InicioComponent } from './features/inicio/inicio.component';
import { VeiculoListComponent } from './features/veiculos/list/veiculo-list.component';
import { VeiculoFormComponent } from './features/veiculos/form/veiculo-form.component';
import { VeiculoDetalheComponent } from './features/veiculos/detalhe/veiculo-detalhe.component';
import { ManutencaoFormComponent } from './features/manutencoes/form/manutencao-form.component';
import { AgendaComponent } from './features/agenda/agenda.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'cadastro', component: CadastroUsuarioComponent },
  { path: 'veiculos/novo', component: VeiculoFormComponent, canActivate: [authGuard] },
  { path: 'veiculos/:id/editar', component: VeiculoFormComponent, canActivate: [authGuard] },
  { path: 'manutencoes/novo', component: ManutencaoFormComponent, canActivate: [authGuard] },
  { path: 'veiculos/:veiculoId/manutencoes/novo', component: ManutencaoFormComponent, canActivate: [authGuard] },
  { path: 'veiculos/:veiculoId/manutencoes/:id/editar', component: ManutencaoFormComponent, canActivate: [authGuard] },
  { path: 'veiculos/:id', component: VeiculoDetalheComponent, canActivate: [authGuard] },
  {
    path: '',
    component: ShellComponent,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'inicio', pathMatch: 'full' },
      { path: 'inicio', component: InicioComponent, data: { title: 'RevisaAi' } },
      { path: 'veiculos', component: VeiculoListComponent, data: { title: 'Veículos' } },
      { path: 'agenda', component: AgendaComponent, data: { title: 'Agenda' } },
    ]
  },
];
