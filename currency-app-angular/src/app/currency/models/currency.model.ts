export class CurrencyModel {
  constructor(
    public currencyName: string,
    public requesterName: string,
    public date: string,
    public currencyValue:number
  ) {
  }
}
