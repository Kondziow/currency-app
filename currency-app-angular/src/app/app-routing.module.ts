import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {CurrencyComponent} from "./currency/currency.component";
import {CurrencyAddComponent} from "./currency/currency-add/currency-add.component";

const routes: Routes = [
  {
    path: 'currencies',
    component: CurrencyComponent,
    children:[
      {
        path: 'new',
        component: CurrencyAddComponent
      }
    ]
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
