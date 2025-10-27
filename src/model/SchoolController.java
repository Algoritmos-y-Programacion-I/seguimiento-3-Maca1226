package model;
import java.time.LocalDate;

public class SchoolController {
//atributos
private String name;
private int hourSpentSupport;
public static final int FLOORS = 5; 
public static final int COL = 10;
public static final int HOURMAXSUPPORT = 100;
private Computer[][] edificio;

/* estas variable son porque como los metodos son void y no retornan entonces son temporales para que lso datos
se puedan guardar en el sistema que eso son los buffer y estan las entradas y las salidas, estas son las 
entradas que es lo que digita el usuario.
*/
private String inSerial;          
private boolean inNextWindow;     
private int inPiso;               
private int inFila;               
private int inColumna;            
private String inDescription;     

// estas variables son las salidas que es lo que se le muestra al usuario.
private boolean lastOk;
private String lastMessage;
private String lastComputerList;
private Computer lastTopComputer;
private int lastTopFloor;         
private int lastTopCol;           

//constructor
public SchoolController(String name) {
        this.name = name ;
        this.hourSpentSupport = 0;
        this.edificio = new Computer[FLOORS][COL];
    } // get y set de name
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    /* RF1
    Descripción: En este metodo se agrega el computador que registró el usuario al sistema
    @pre se llamó a setRegistroInputs para Serial, nextWindow y piso, el serial no está vacio y no lo puede estar
    @pre el piso es un numero valido de la matriz edificio
    @post Si el serial ya está registrado marca que tiene que ese serial ya está
    @post Si el piso esta lleno se modifica la matriz
    @param ---
    @return ----
     */
    public void agregarComputador() {
       if (inSerial == null || inSerial.isEmpty()) {
        lastOk = false;
        lastMessage = "Serial vacío, escribe el serial";
        return;
    }
    if (inPiso < 1 || inPiso > FLOORS) {
        lastOk = false;
        lastMessage = "Piso fuera de rango (1-" + FLOORS + ") ";
        return;
    }

    String s = inSerial.trim();
    if (serialExists(s)) {
        lastOk = false;
        lastMessage = "Ya existe un computador con ese serial";
        return;
    }

    int col = firstFreeCol(inPiso);
    if (col == -1) {
        lastOk = false;
        lastMessage = "El piso " + inPiso + " está lleno.";
        return;
    }

    Computer c = new Computer(s, inNextWindow);
    edificio[inPiso - 1][col] = c;

    lastOk = true;
    lastMessage = "Computador registrado en piso " + inPiso + ", columna " + (col + 1) + ".";
    }

    /* RF2
    Descripción: en este método se agrega el incidente que pasó con el computador al sistema, muestra el piso
    y la columna en la que se encuentra el computador que tuve le incidente
    @pre ya se llamó a setIncidenteInputs en los que esta Serial, fila, columna y descripción
    @pre El serial no está vacio
    @pre La descripción no está vacia 
    @pre la fila y columna es valida en el edificio
    @post Si estan vacias las celdas, el serial y no se cumplen las precondiciones no hay modificaciones en el edificio
    @post si todo ya esta válido se agrega
    @param ---
    @return ---
     */
    public void agregarIncidenteEnComputador() {
        if (inFila < 1 || inFila > FLOORS) {
        lastOk = false;
        lastMessage = "Fila/piso fuera de rango (1-" + FLOORS + ")";
        return;
    }
    if (inColumna < 1 || inColumna > COL) {
        lastOk = false;
        lastMessage = "Columna fuera de rango (1-" + COL + ").";
        return;
    }
    if (inSerial == null || inSerial.isEmpty()) {
        lastOk = false;
        lastMessage = "Serial vacío, escribe un serial";
        return;
    }
    if (inDescription == null || inDescription.isEmpty()) {
        lastOk = false;
        lastMessage = "Descripción vacía, escribe una descripcion";
        return;
    }

    Computer c = edificio[inFila - 1][inColumna - 1];
    if (c == null) {
        lastOk = false;
        lastMessage = "No hay computador en (" + inFila + "," + inColumna + ").";
        return;
    }
    if (!c.getSerialNumber().equals(inSerial.trim())) {
        lastOk = false;
        lastMessage = "El serial no coincide con el equipo en (" + inFila + "," + inColumna + ").";
        return;
    }
    c.addIncident(new Incident(inDescription.trim(), LocalDate.now()));
    lastOk = true;
    lastMessage = "Incidente registrado para " + c.getSerialNumber() + ".";
    } /* RF3
    Descripción: en este método hace una lista de todos los computadores que hay en el edificio y se determina cual
    es el que tiene más incidentes.
    @pre si no hay computadores registrados el metodo funciona y deja un mensaje que es que no hay computadores registrados
    @post tiene el listado con los computadores, si no hay registrados queda como "no hay computadores registradoa"
    @post en la lista está el computador con mas incidentes
    @post no modifica la matriz
    @param ---
    @return ---
   
     */
    public void getComputerList() {
    String listado = "";         
    boolean hay = false;
    Computer top = null;
    int max = -1;
    int topFloor = -1, topCol = -1;

    for (int f = 0; f < FLOORS; f++) {
        for (int c = 0; c < COL; c++) {
            Computer comp = edificio[f][c];
            if (comp != null) {
                hay = true;
                int cnt = comp.getIncidentCount();
                listado += "piso " + (f + 1)
                         + ", col " + (c + 1)
                         + " -> " + comp.getSerialNumber()
                         + " (incidentes " + cnt + ")\n";

                if (cnt > max) {
                    max = cnt;
                    top = comp;
                    topFloor = f + 1;
                    topCol = c + 1;
                }
            }
        }
    }

    lastComputerList = hay ? listado : "No hay computadores registrados.";
    lastTopComputer = top;
    lastTopFloor = topFloor;
    lastTopCol = topCol;
    lastOk = true;
    lastMessage = "";
}
// Los setters de las entradas por si hay modificaciones, sin get porque no retornan nada.
public void setRegistroInputs(String serial, boolean nextWindow, int piso) {
        this.inSerial = serial;
        this.inNextWindow = nextWindow;
        this.inPiso = piso;
}

public void setIncidenteInputs(String serial, int fila, int columna, String description) {
        this.inSerial = serial;
        this.inFila = fila;
        this.inColumna = columna;
        this.inDescription = description;
}

//Getters de las salidas sin setters porque no necesitan modificaciones.
public boolean getLastOk() {
    return lastOk; 
}
public String getLastMessage() { 
    return lastMessage;
}
public String getLastComputerList() { 
    return lastComputerList; 
}
public Computer getLastTopComputer() { 
    return lastTopComputer;
}
public int getLastTopFloor() { 
    return lastTopFloor; 
}
public int getLastTopCol() { 
    return lastTopCol; 
}

/*descripción: este metodo se encarga de que las variables de salida vuelvan a su estado vacio
@pre true
@post no modifica la matriz
*/
private void clearOutputs() {
    lastOk = false;
    lastMessage = "";
    lastComputerList = "";
    lastTopComputer = null;
    lastTopFloor = -1;
    lastTopCol = -1;
} /* descripción: En este método verifica si extiste un serial ya registrado en el sistema
@pre serial no esta vacia
@post No se altera la matriz
@param serial a buscar 
@return true si existe uno ya registrado
@return false si no esta registrado


*/
private boolean serialExists(String serial) {
        String s = serial.trim();
        for (int f = 0; f < FLOORS; f++) {
            for (int c = 0; c < COL; c++) {
                Computer comp = edificio[f][c];
                if (comp != null && comp.getSerialNumber().equals(s)) return true;
            }
    }
        return false;
} /* descripción: en este metodo se obtiene el indice de la primera columna libre en el piso dado
@pre el piso debe de ser menor o igual que uno y meno o igual que FLOOR
@post No se altera la matriz
@param piso número de piso 1
@return índice 0 si esta libre la primera columna y -1 si el piso esta lleno
*/
private int firstFreeCol(int piso) {
        int row = piso - 1;
        for (int c = 0; c < COL; c++) if (edificio[row][c] == null) return c;
        return -1;
}


}
