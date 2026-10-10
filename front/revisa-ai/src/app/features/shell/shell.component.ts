import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { filter, map } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { TabMemoryService } from '../../core/services/tab-memory.service';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, MatToolbarModule, MatIconModule, MatButtonModule, MatMenuModule],
  templateUrl: './shell.component.html',
  styleUrl: './shell.component.scss'
})
export class ShellComponent implements OnInit {
  titulo = 'RevisaAi';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private auth: AuthService,
    private tabMemory: TabMemoryService
  ) {}

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event) => {
      this.tabMemory.registrar((event as NavigationEnd).urlAfterRedirects);

      let child = this.route.firstChild;
      while (child?.firstChild) child = child.firstChild;
      this.titulo = child?.snapshot.data['title'] ?? 'RevisaAi';
    });
  }

  sair(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  novoVeiculo(): void {
    this.router.navigate(['/veiculos/novo']);
  }

  novaManutencao(): void {
    this.router.navigate(['/manutencoes/novo']);
  }
}
