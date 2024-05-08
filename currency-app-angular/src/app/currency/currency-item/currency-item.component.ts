import {Component, Input} from '@angular/core';
import {CurrencyRecordModel} from "../models/currencyRecord.model";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-currency-item',
  standalone: true,
  imports: [
    DatePipe
  ],
  templateUrl: './currency-item.component.html',
  styleUrl: './currency-item.component.css'
})
export class CurrencyItemComponent {
  @Input() currency: CurrencyRecordModel;

}
