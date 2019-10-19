import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SearchDto } from "./model/search";

@Injectable({
  providedIn: "root"
})
export class LocationService {
  private baseUrl = "http://localhost:8080/locations/";

  constructor(private http: HttpClient) {}

  getLocations(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  createLocation(location: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, location);
  }

  searchLocations(searchDto: SearchDto): Observable<any> {
    if (searchDto.query) {
      const params = new HttpParams()
        .set("query", searchDto.query)
        .set("radius", searchDto.radius ? searchDto.radius + "" : "");
      return this.http.get(`${this.baseUrl}search`, { params });
    }
    return this.getLocations();
  }

  deleteLocation(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}${id}`, { responseType: "text" });
  }
}
