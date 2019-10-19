import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {routing} from "./app.routing";
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { LocationListComponent } from './location-list/location-list.component';
import { CreateLocationModalComponent } from './create-location-modal/create-location-modal.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    LocationListComponent,
    CreateLocationModalComponent
  ],
  imports: [
    BrowserModule,
    routing,
    HttpClientModule,
    NgbModule.forRoot(),
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [CreateLocationModalComponent],
})
export class AppModule { }
