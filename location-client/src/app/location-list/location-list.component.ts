import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { LocationDto } from "../model/location";
import { SearchDto } from "../model/search";
import { LocationService } from '../location.service';
import { Router } from '@angular/router';
import {NgbModal, NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import { CreateLocationModalComponent } from '../create-location-modal/create-location-modal.component';

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css'],
  providers:  [ LocationService, NgbActiveModal ]
})
export class LocationListComponent implements OnInit {
  locations: Observable<LocationDto[]>;
  search: SearchDto = new SearchDto();
  count: number;

  constructor(
    private locationService: LocationService, 
    private modalService: NgbModal, 
    private activeModal: NgbActiveModal,
    private router: Router) { }

  ngOnInit() {
    this.count = 1;
    this.reloadData();
  }

  ngAfterViewInit(){
    this.count = 1;
  }

  reloadData() {
    this.locations = this.locationService.getLocations();
  }

  deleteLocation(id: string) {
    this.locationService.deleteLocation(id).subscribe(() => this.reloadData(), error => console.log(error));;
  }

  onSubmit() {
    this.locations = this.locationService.searchLocations(this.search);
  }

  openModal() {
    const modalRef = this.modalService.open(CreateLocationModalComponent);
    modalRef.componentInstance.name = 'locationCreation';
    modalRef.result.then((res) => {
      if ( res === 'success' ) {
         this.reloadData();
      }
    }, (reason) => {
    });
  }
}
