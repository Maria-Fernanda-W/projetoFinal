import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class TabMemoryService {
  private ultimaTab: string[] = ['/inicio'];

  registrar(url: string): void {
    if (url === '/inicio' || url === '/veiculos' || url === '/agenda') {
      this.ultimaTab = [url];
    }
  }

  obterUltimaTab(): string[] {
    return this.ultimaTab;
  }
}
