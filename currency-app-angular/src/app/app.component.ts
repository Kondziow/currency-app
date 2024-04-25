import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CurrencyComponent} from "./currency/currency.component";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, CurrencyComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

}
