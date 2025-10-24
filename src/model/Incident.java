package model;
import java.time.LocalDate;
// En esta clase es para que se registre los incidentes que pasen con los computadores
// las variables de la clase incidentes son : description, solution, solutionhours y dateReport

public class Incident {
    private String description;
    private boolean solution;
    private int solutionHours;
    private LocalDate dateReport;

  //constructor
  public Incident(String description, LocalDate dateReport) {
    this.description = description;
    this.dateReport = dateReport;
    //aqui hago los métodos de getters y setters con cada una de las variables
  }
  public String getDescription(){
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public LocalDate getDateReport() {
    return dateReport;
  }
  public void setDateReport(LocalDate dateReport) {
    this.dateReport = dateReport;
  }
}
