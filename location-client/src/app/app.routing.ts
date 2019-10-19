import { RouterModule, Routes } from '@angular/router';
import { LocationListComponent } from './location-list/location-list.component';
import { CreateLocationModalComponent } from './create-location-modal/create-location-modal.component';


const routes: Routes = [
  {path : '', component : LocationListComponent},
];

export const routing = RouterModule.forRoot(routes);