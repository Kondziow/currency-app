import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CurrencyComponent} from "./currency/currency.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, CurrencyComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

}
