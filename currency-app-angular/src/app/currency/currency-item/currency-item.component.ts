import {Component, Input} from '@angular/core';
import {CurrencyRecordModel} from "../models/currencyRecord.model";

@Component({
  selector: 'app-currency-item',
  standalone: true,
  imports: [],
  templateUrl: './currency-item.component.html',
  styleUrl: './currency-item.component.css'
})
export class CurrencyItemComponent {
  @Input() currency: CurrencyRecordModel;

}
