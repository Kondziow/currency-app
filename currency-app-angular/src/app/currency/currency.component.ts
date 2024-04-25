import {Component, OnInit} from '@angular/core';
import {CurrencyModel} from "./models/currency.model";
import {CurrencyService} from "./currency.service";
import {CurrencyItemComponent} from "./currency-item/currency-item.component";
import {NgForOf} from "@angular/common";
import {ActivatedRoute, Router, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-currency',
  standalone: true,
  imports: [
    CurrencyItemComponent,
    NgForOf,
    RouterOutlet
  ],
  templateUrl: './currency.component.html',
  styleUrl: './currency.component.css'
})
export class CurrencyComponent implements OnInit{
  currencyRequests: CurrencyModel[] = [];
  buttonDisabled = false;

  constructor(private currencyService: CurrencyService,
              private route: ActivatedRoute,
              private router: Router) {
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

  onSendCurrencyRequest() {
    this.router.navigate(['new'], {relativeTo: this.route});
    this.buttonDisabled = true;
  }

}
