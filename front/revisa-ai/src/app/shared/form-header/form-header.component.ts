import { Component, Input } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-form-header',
  standalone: true,
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './form-header.component.html',
  styleUrl: './form-header.component.scss'
})
export class FormHeaderComponent {
  @Input({ required: true }) titulo!: string;
  @Input() voltarPara?: any[];

  constructor(private location: Location, private router: Router) {}

  voltar(): void {
    if (this.voltarPara) {
      this.router.navigate(this.voltarPara);
    } else {
      this.location.back();
    }
  }
}
