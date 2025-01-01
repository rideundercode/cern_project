import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { SensorDataService, SensorData } from './services/sensor-data.service';
import { ThresholdService, Threshold } from './services/threshold.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  displayedColumns: string[] = ['id', 'sensorType', 'sensorValue', 'unit', 'timestamp'];
  dataSource: SensorData[] = [];
  alerts: SensorData[] = [];
  thresholds: Threshold[] = [];
  sensorTypes: string[] = ['P', 'V', 'n', 'T', 'I']; // Liste des types de capteurs
  thresholdValues: { [key: string]: Threshold } = {}; // Stocke les seuils pour manipulation directe

  constructor(
    private sensorDataService: SensorDataService,
    private thresholdService: ThresholdService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadThresholds();
    this.startDataStream();
  }

  /**
   * Charge les seuils configurés depuis le backend.
   */
  loadThresholds(): void {
    this.thresholdService.getThresholds().subscribe(
      (thresholds) => {
        console.log('Thresholds loaded from backend:', thresholds);
        this.thresholds = thresholds;
  
        this.sensorTypes.forEach((type) => {
          const threshold = thresholds.find((t) => t.sensorType === type);
          this.thresholdValues[type] = threshold || { id: 0, sensorType: type, minThreshold: null, maxThreshold: null };
        });
        console.log('Threshold values map:', this.thresholdValues);
      },
      (error) => {
        console.error('Error loading thresholds:', error); // Log des erreurs de chargement des seuils
      }
    );
  }
  

  /**
   * Démarre le flux de données et met à jour le tableau en temps réel.
   */
  startDataStream(): void {
    setInterval(() => {
      this.sensorDataService.getSensorData().subscribe((newData: SensorData[]) => {
        console.log('New sensor data received:', newData); // Log pour vérifier les données reçues
        
        if (newData && newData.length > 0) {
          const filteredData = newData.filter((data) => !this.isWithinThreshold(data));
  
          this.alerts = [...filteredData, ...this.alerts].slice(0, 10);
          this.dataSource = [...newData.reverse(), ...this.dataSource].slice(0, 10);
          console.log('Updated alerts:', this.alerts); // Log pour vérifier les alertes
          console.log('Updated dataSource:', this.dataSource); // Log pour vérifier les données sources
        }
        this.cdr.detectChanges();
      }, (error) => {
        console.error('Error fetching sensor data:', error); // Log des erreurs
      });
    }, 1000);
  }
  
  /**
   * Vérifie si une donnée est dans les seuils définis.
   */
  isWithinThreshold(data: SensorData): boolean {
    const threshold = this.thresholdValues[data.sensorType];
    if (!threshold) return true; // Pas de seuil défini
    return (
      (threshold.minThreshold === null || data.sensorValue >= threshold.minThreshold) &&
      (threshold.maxThreshold === null || data.sensorValue <= threshold.maxThreshold)
    );
  }

  /**
   * Enregistre ou met à jour un seuil.
   */
  saveThreshold(sensorType: string): void {
    const threshold = this.thresholdValues[sensorType];
    if (threshold) {
      this.thresholdService.updateThreshold(threshold.id, threshold).subscribe(() => {
        this.loadThresholds(); // Recharger les seuils après la mise à jour
      });
    }
  }

    /**
   * Récupère le seuil d'un type de capteur.
   */
    getThreshold(sensorType: string): Threshold | undefined {
      return this.thresholds.find((threshold) => threshold.sensorType === sensorType);
    }
  



    /**
 * Télécharge les données en temps réel au format CSV.
 */
downloadCSV(): void {
  // Convertir les données en CSV
  const csvData = this.convertToCSV(this.dataSource);
  
  // Créer un fichier Blob à partir des données CSV
  const blob = new Blob([csvData], { type: 'text/csv' });
  const url = window.URL.createObjectURL(blob);

  // Créer un lien temporaire pour télécharger le fichier
  const a = document.createElement('a');
  a.setAttribute('href', url);
  a.setAttribute('download', 'donnees_reelles.csv');
  a.click();

  // Libérer l'URL après le téléchargement
  window.URL.revokeObjectURL(url);
}

/**
 * Convertit un tableau d'objets en une chaîne CSV.
 * @param data Tableau d'objets à convertir en CSV
 * @returns Chaîne CSV
 */
convertToCSV(data: any[]): string {
  if (!data || data.length === 0) {
    return '';
  }

  // Récupérer les en-têtes des colonnes
  const headers = Object.keys(data[0]).join(',');

  // Récupérer les valeurs des données
  const rows = data.map(row => 
    Object.values(row)
      .map(value => (typeof value === 'string' ? `"${value}"` : value))
      .join(',')
  );

  // Combiner les en-têtes et les lignes de données
  return [headers, ...rows].join('\n');
}


}


