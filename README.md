# **Project: Real-Time Data Supervision**

## **Project Description**
This project is a comprehensive application designed to monitor simulated real-time data. It includes features for data generation, management, and visualization, along with an alert system based on configurable thresholds. The project is built on a **Full-Stack architecture**, combining **Spring Boot** for the backend and **Angular** for the frontend.

---

## **Main Features**
### **1. Simulated Data Generation**
- A Spring Boot backend generates simulated data in real-time (sensor types such as P, V, T, etc.) with random values.
- The generated data includes metadata such as timestamps, units, and unique identifiers.

### **2. Data and Threshold Management**
- A second Spring Boot backend manages:
  - Data storage in a database.
  - Configurable thresholds for each sensor type.
  - Real-time alerts for out-of-threshold values.
- Thresholds can be configured via the user interface and are saved through a REST API.

### **3. Real-Time Visualization**
- An Angular interface allows users to:
  - View a dashboard displaying real-time data.
  - See an alert system for out-of-threshold values.
  - Use a table to configure thresholds for each sensor type.

### **4. Data Export**
- Users can export all displayed data in **CSV format** directly from the user interface.

---

## **Technical Architecture**
### **Backend (Spring Boot)**
- **Language**: Java
- **Framework**: Spring Boot
- **Core Services**:
  - Data management via WebSocket.
  - REST API for threshold configuration and data interaction.
  - Threshold management with a dedicated service.
  - Alerts notification via WebSocket.

### **Frontend (Angular)**
- **Language**: TypeScript
- **Framework**: Angular
- **Design**: Angular Material for a modern, responsive UI.
- **Main Modules**:
  - Real-time data visualization (dynamic table).
  - Threshold management via interactive forms.
  - Data export in CSV format.

### **Database**
- **Type**: SQL
- **Usage**: Storage for sensor data and thresholds.

---

## **Technologies Used**
### **Backend**
- **Spring Boot**: Main framework for data and threshold management.
- **WebSocket**: Real-time data transmission.
- **JPA/Hibernate**: Persistence management with an SQL database.

### **Frontend**
- **Angular**: Framework for user interaction and visualization.
- **Angular Material**: UI components library for a modern design.
- **RxJS**: Data stream management.

### **Additional Tools**
- **Git**: Version control.
- **Postman**: API testing.
- **Node.js**: Dependency management for Angular.

---

## **Installation and Usage**
### **Prerequisites**
- **Backend**: 
  - JDK 17+
  - Maven
  - A database (MySQL or H2 for quick setup)
- **Frontend**:
  - Node.js
  - Angular CLI

### **Installation Steps**
1. **Clone the repository**:
  - for ssh:
   ```bash
   git clone git@github.com:rideundercode/cern_project.git
  ```
  - for https:
   ```bash
    git clone https://github.com/rideundercode/cern_project.git
   ```
2. **Launch the backend generation**:
   - install :
     ```bash
     mvn clean install
   ```
  - run :
     ```bash
     mvn spring-boot:run
   ```

3. **Launch the backend appback**:
   - install :
     ```bash
     mvn clean install
     ```
   - run :
     ```bash
     mvn spring-boot:run
   ```
4. **Launch the frontend appfront**:
      - install :
     ```bash
    npm install
     ```
   - run :
     ```bash
     ng serve
   ```
   5. **Access the User Interface**:
   - Open http://localhost:4200 in your browser.



How It Works
Real-Time Data:

Sensor data is automatically generated on the backend.
It is displayed in real-time on the Angular dashboard.
Threshold Configuration:

Thresholds for each sensor type can be set via the user interface.
Values outside the thresholds trigger alerts visible on the dashboard.
CSV Export:

Click the "Download CSV" button to export all current data.







