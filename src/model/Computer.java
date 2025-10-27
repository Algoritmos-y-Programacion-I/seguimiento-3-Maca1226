package model;
import java.util.ArrayList;
//Esta clase representa los computadores que estan en un piso y columna

public class Computer {

    private String serialNumber;
    private boolean nextWindow;
    private ArrayList<Incident>incidents;
    //constructor
    public Computer(String serialNumber, boolean nextWindow) {
        this.serialNumber = serialNumber;
        this.nextWindow = nextWindow;
        this.incidents = new ArrayList<>();
        // en estos metodos hagos los getters y los setter de las dos variables que son serial numbre y next window
    }
    public String getSerialNumber(){
        return serialNumber;
    }
    public void setSerialNumber(){
        this.serialNumber = serialNumber;
    }
    public boolean getNextWindow(){
        return nextWindow;
    }
    public void setNextWindow(){
        this.nextWindow = nextWindow;

    } /* Descripción: En este metodo se añade el incidente que pasó con el computador
         @param incidente se registra el incidente
         @return --

    */
    public void addIncident(Incident incidente) {
        incidents.add(incidente);
    } /* descripción: este metodo es para los incidentes totales que estan registrados
    @pre true
    @post si hay algun cambio sobre la lista retornada no afecta a este objeto
    @return número de incidentes
    */
    public int getIncidentCount() { 
        return incidents.size(); 
    } /* descripcióm: este metodo es para el arraylist de incidents, este el get para que retorne el arraylist
    @pre true
    @post si hay algun cambio sobre la lista retornada no afecta a este objeto
    @return una nueva lista
    */
    public java.util.List<Incident> getIncidents() {
    return new java.util.ArrayList<>(incidents);
    }


}
