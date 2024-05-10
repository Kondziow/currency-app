
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {CurrencyRequestModel} from "./models/currencyRequest.model";

@Injectable({providedIn: 'root'})
export class CurrencyService{
  private readonly currencyUrl = 'http://localhost:8080/api/currencies'
  private readonly getCurrencyUrl = this.currencyUrl + '/requests';
  private readonly postCurrencyUrl = this.currencyUrl + '/get-current-currency-value-command';
  private readonly defaultPageSize = 5;

  constructor(private http: HttpClient) {
  }

  getAllCurrencyRecords(pageNumber: number) {
    return this.http.get<any>(this.getCurrencyUrl, {params : {pageNumber : pageNumber, pageSize: this.defaultPageSize}});
  }

  postCurrencyRequest(currencyRequest: CurrencyRequestModel) {
    return this.http.post<any>(this.postCurrencyUrl, currencyRequest);
  }
}
