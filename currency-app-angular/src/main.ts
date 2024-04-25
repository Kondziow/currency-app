import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app/app-routing.module";
import {importProvidersFrom} from "@angular/core";

bootstrapApplication(AppComponent, {
  providers: [importProvidersFrom(AppRoutingModule, HttpClientModule)]
});

