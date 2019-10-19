import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LocationService } from '../location.service';
import { LocationDto } from "../model/location";

@Component({
  selector: 'app-create-location-modal',
  templateUrl: './create-location-modal.component.html',
  styleUrls: ['./create-location-modal.component.css'],
  providers:  [ LocationService ]
})
export class CreateLocationModalComponent implements OnInit {
  @Input() name;

  location: LocationDto = new LocationDto();
  submitted = false;

  constructor(private locationService: LocationService, public activeModal: NgbActiveModal, private router: Router) {}

  ngOnInit() {
  }

  newLocation(): void {
    this.submitted = false;
    this.location = new LocationDto();
  }

  save() {
    this.locationService.createLocation(this.location)
      .subscribe(data => console.log(data), error => console.log(error));
    this.location = new LocationDto();
    this.activeModal.close('success');
  }

  onSubmit() {
    this.submitted = true;
    this.save();    
  }
}
