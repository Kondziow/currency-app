import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CurrencyModel} from "./models/currency.model";
import {Injectable} from "@angular/core";
import {CurrencyRequestModel} from "./models/currencyRequest.model";

@Injectable({providedIn: 'root'})
export class CurrencyService{
  private readonly currencyUrl = 'http://localhost:8080/api/currencies'
  private readonly getCurrencyUrl = this.currencyUrl + '/requests';
  private readonly postCurrencyUrl = this.currencyUrl + '/get-current-currency-value-command';

  constructor(private http: HttpClient) {
  }

  getAllCurrencyRequests() {
    return this.http.get<any>(this.getCurrencyUrl);
  }

  postCurrencyRequest(currencyRequest: CurrencyRequestModel) {
    return this.http.post(this.postCurrencyUrl, currencyRequest);
  }
}
