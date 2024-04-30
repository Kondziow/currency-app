import {Component, OnInit} from '@angular/core';
import {CurrencyRecordModel} from "./models/currencyRecord.model";
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
  currencyRecords: CurrencyRecordModel[] = [];

  constructor(private currencyService: CurrencyService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getCurrencyRecords();
  }

  getCurrencyRecords() {
    this.currencyService.getAllCurrencyRecords()
      .subscribe(response => {
        this.currencyRecords = response.currencyRecords;
      });
  }

  onSendCurrencyRequest() {
    this.router.navigate(['new'], {relativeTo: this.route});
  }

}
