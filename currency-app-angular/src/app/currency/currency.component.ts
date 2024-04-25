import {Component, OnInit} from '@angular/core';
import {CurrencyModel} from "./currency.model";
import {CurrencyService} from "./currency.service";
import {CurrencyItemComponent} from "./currency-item/currency-item.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-currency',
  standalone: true,
  imports: [
    CurrencyItemComponent,
    NgForOf
  ],
  templateUrl: './currency.component.html',
  styleUrl: './currency.component.css'
})
export class CurrencyComponent implements OnInit{
  currencyRequests: CurrencyModel[] = [];

  constructor(private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.getCurrencyRequests();
  }

  getCurrencyRequests() {
    this.currencyService.getAllCurrencyRequests()
      .subscribe(response => {
        this.currencyRequests = response.currencyRequests;
      });
  }

}
