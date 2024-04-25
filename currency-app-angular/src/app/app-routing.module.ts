import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {CurrencyComponent} from "./currency/currency.component";

const routes: Routes = [
  {
    path: 'currencies',
    component: CurrencyComponent
  },
  {
    path: '',
    redirectTo: 'currencies', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
