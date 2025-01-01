import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Threshold {
  id: number;
  sensorType: string;
  minThreshold: number | null;
  maxThreshold: number | null;
}

@Injectable({
  providedIn: 'root',
})
export class ThresholdService {
  private apiUrl = 'http://localhost:8082/api/thresholds';

  constructor(private http: HttpClient) {}

  getThresholds(): Observable<Threshold[]> {
    return this.http.get<Threshold[]>(this.apiUrl);
  }

  updateThreshold(id: number, threshold: Threshold): Observable<Threshold> {
    return this.http.put<Threshold>(`${this.apiUrl}/${id}`, threshold);
  }
}
