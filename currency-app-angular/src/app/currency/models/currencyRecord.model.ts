export class CurrencyRecordModel {
  constructor(
    public currencyName: string,
    public requesterName: string,
    public date: string,
    public currencyValue:number
  ) {
  }
}
