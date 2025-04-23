import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Calculadora';
  numero1: number = 0;
  numero2: number = 0;
  resultado: number = 0;

  sumar(): void { 
    this.resultado = this.numero1 + this.numero2;
  }

  restar(): void { 
    this.resultado = this.numero1 - this.numero2;
  }

}
