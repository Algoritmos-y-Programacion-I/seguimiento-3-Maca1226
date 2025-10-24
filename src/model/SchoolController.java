package model;

public class SchoolController {

    private String name;
    private int hourSpentSupport;
    public static final int FLOORS = 5; 
    public static final int COL = 10;
    public static final int HOURMAXSUPPORT = 100;
    private Computer[][] edificio;

    //constructor
    public SchoolController(String name, int hourSpentSupport) {
        this.name = name;
        this.hourSpentSupport = hourSpentSupport;
        this.edificio = new Computer[FLOORS][COL];
    }


    public void agregarComputador() {

    }

    public void agregarIncidenteEnComputador() {

    }

    public void getComputerList() {

    }

}
