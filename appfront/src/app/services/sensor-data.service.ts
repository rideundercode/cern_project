import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SensorData {
  id: number;
  sensorType: string;
  sensorValue: number;
  unit: string;
  timestamp: string;
}

@Injectable({
  providedIn: 'root',
})
export class SensorDataService {
  private apiUrl = 'http://localhost:8082/api/sensor-data';

  constructor(private http: HttpClient) {}

  getSensorData(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}

