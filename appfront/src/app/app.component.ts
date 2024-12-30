import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { SensorDataService, SensorData } from './services/sensor-data.service';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    trigger('rowAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(-20px)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ])
  ]
})
export class AppComponent implements OnInit {
  displayedColumns: string[] = ['id', 'sensorType', 'sensorValue', 'unit', 'timestamp'];
  dataSource: SensorData[] = []; // Tableau pour stocker les données

  constructor(
    private sensorDataService: SensorDataService,
    private cdr: ChangeDetectorRef // Pour détecter les changements manuellement
  ) {}

  ngOnInit(): void {
    this.startDataStream();
  }

  /**
   * Démarre le flux de données et met à jour le tableau en temps réel.
   */
  startDataStream(): void {
    let lastId = 0; // Stocke l'ID le plus bas visible dans le tableau
    setInterval(() => {
      this.sensorDataService.getSensorData().subscribe((newData: SensorData[]) => {
        // Obtenir les nouvelles données uniquement au-delà du dernier ID visible
        const filteredData = newData.filter(data => data.id > lastId);

        // Ajouter les nouvelles données en haut, limiter à 10 lignes
        if (filteredData.length > 0) {
          this.dataSource = [...filteredData.reverse(), ...this.dataSource].slice(0, 10);
          lastId = this.dataSource[0].id; // Mettre à jour le dernier ID visible
        }

        // Forcer la détection des changements pour mettre à jour l'animation
        this.cdr.detectChanges();
      });
    }, 1000); // Rafraîchissement toutes les secondes
  }
}
