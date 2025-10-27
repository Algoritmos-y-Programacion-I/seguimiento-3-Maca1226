/* Seguimiento #3
COMPUTARICEMOS:
Análisis: En este problema debemos de hacer un sistema en el cual se pueda registar los computadores que tienen incidentes
y se muestre donde está ubicado para facilitarle al usuario cual es el computador que debe de arreglar.
La institución tiene un edificio con 5 pisos y 10 columnas por piso (matriz 5x10) para ubicar computadores.
En este caso cada computador tiene un serial único y la bandera “nextWindow”, los incidentes se registran
con una descripción y una fecha.
Este problema tiene tres requerimientos funcionales: Adicionar un computador, Registrar un incidente para un computador y
Consultar el computador con más incidentes
Este sistema tiene 4 clases que son: Computer, Incident, SchoolController y SchoolApp, cada uno tiene sus funciones
Reglas a tener en cuenta: El serial no puede estar vacío y debe ser único en todo el edificio, piso (1-5) y columna (1-10),
la descripción del incidente no puede estar vacía, al registrar computador se ubica en la primera columna libre del piso y
si el piso está lleno o el serial ya existe se informa error.
*/
package ui;

import java.util.Scanner;
import model.Computer;
import model.SchoolController;

public class SchoolApp {

    private final Scanner input;
    private final SchoolController controller; 

    public static void main(String[] args) {
        SchoolApp ui = new SchoolApp();
        ui.menu();
    }
    //constructor
    public SchoolApp() {
        input = new Scanner(System.in);
        this.controller = new SchoolController("Computaricemos"); 
    }
    /* descripción: en este método se imprimen todas las opciones del menu que el usuario puede elegir 
    @post el método finaliza solo cuando el usuario ingresa la opción 0
    @post según la opcion que escoja el usuario se ejecutará el metodo que le corresponde
    */
    public void menu() {
        System.out.println("Bienvenido a Computaricemos");
        int option;
        do {
            System.out.println("\nMenu Principal");
            System.out.println("--------------------------------------------------------");
            System.out.println("Digite alguna de las siguientes opciones");
            System.out.println("1) Registrar computador");
            System.out.println("2) Registrar incidente en computador");
            System.out.println("3) Consultar el computador con más incidentes");
            System.out.println("0) Salir del sistema");
            option = leerEntero("Opción: ");

            switch (option) {
                case 1 -> registrarComputador();
                case 2 -> registrarIncidenteEnComputador();
                case 3 -> consultarComputadorConMasIncidentes();
                case 0 -> System.out.println("\nGracias por usar nuestros servicios. ¡Adiós!");
                default -> System.out.println("\nOpción inválida. Intente nuevamente.");
            }
        } while (option != 0);
    }
    /* Descripción: En este metodo el usuario registra los datos para ingresar al sistema la computadora
    @pre Esta instancia tiene un controller y un scanner
    @pre existe en el controller los metodos
    @post si los datos son validos, el controller habrá registrado el computador en la primera columna libre del piso e imprime el
    mensaje de la ubicación asignada.
    @post si ocurre algún problema nada se modifica y se imprime un mensaje explicando que pasó
    @param ---
    @return ---
    */
    public void registrarComputador() {
        System.out.println("\nRegistrar computador");
        String serial = leerTextoNoVacio("Serial: ");
        boolean nextWindow = leerBoolean("¿activar nextWindow? (s/n): ");
        int piso = leerEnteroEnRango("Piso (1-" + SchoolController.FLOORS + "): ",1, SchoolController.FLOORS);

        controller.setRegistroInputs(serial, nextWindow, piso);
        controller.agregarComputador();
        System.out.println(controller.getLastMessage());
    } /* Descripción: Este metodo registra los incidentes que ocurrieron con el computador, el usuario ingresa el serial
    del computador quu tuvo el incidente, el piso y la columna en la que se encuentra y la descripcion del incidente
    @pre Este metodo existe en el controller
    @pre las constantes estan definidas
    @post si todo está valido se registra con éxito y con la fecha 
    @post si hay algun error lanza un mensaje dependiendo del error
    @param ---
    @return ---
    */

    public void registrarIncidenteEnComputador() {
        System.out.println("\nRegistrar incidente");
        String serial = leerTextoNoVacio("Serial del equipo: ");
        int fila = leerEnteroEnRango(" ingrese el Piso (1-" + SchoolController.FLOORS + "): ",
                                     1, SchoolController.FLOORS);
        int columna = leerEnteroEnRango("ingrese la Columna (1-" + SchoolController.COL + "): ",
                                        1, SchoolController.COL);
        String descripcion = leerTextoNoVacio("Descripción del incidente: ");

        controller.setIncidenteInputs(serial, fila, columna, descripcion);
        controller.agregarIncidenteEnComputador();
        System.out.println(controller.getLastMessage());
    } /* Descripción: en este método permite que el usuario pueda consultar el computador que tiene más incidentesy que genere el listado
    de los computadores.
    @pre  Este metodo existe en el controller
    @post Se imprime el listado completo devuelto por el controlador
    @post Si no hay computadores, se informa al usuario
    @post Si existe un computador con más incidentes, se imprimen su serial, piso, columna y cantidad de incidentes.
    @param ---
    @return ---
    */

    public void consultarComputadorConMasIncidentes() {
        System.out.println("\nConsultar computador con más incidentes");
        controller.getComputerList();
        System.out.print(controller.getLastComputerList());
        Computer top = controller.getLastTopComputer();
        if (top == null) {
            System.out.println("No hay computadores registrados.");
        } else {
            System.out.println("-> Más incidentes: " + top.getSerialNumber()
                    + " | Piso " + controller.getLastTopFloor()
                    + ", Col " + controller.getLastTopCol()
                    + " | Incidentes: " + top.getIncidentCount());
        }
    }

    /* Descripción: en este metodo lee si no se ingresa ningun dato y queda vacío, si esto pasa se imprime un mensaje
    @pre Existe scanner
    @post Devuelve una cadena no vacía
    @param prompt mensaje a mostrar al usuario antes de leer
    @return el mensaje que la entrada esta vacía
    */
    private String leerTextoNoVacio(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Entrada vacía. Intenta de nuevo.");
        }
    } /* Descripción: En este método lee si el numero que ingreso el usuario es un entero
    @pre Existe un scanner
    @post Retorna un valor entero válido
    @param prompt mensaje a mostrar al usuario antes de leer
    @return El mensaje que debe ingresar un numero entero
    */

    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Ingresa un número entero."); }
        }
    } /* Descripción: En este método lee si el el numero esta en el ranjo de minimo y maximo 
    @pre Existe un scanner
    @post Retorna un entero dentro del rango
    @param prompt mensaje a mostrar al usuario antes de leer
    @param min
    @param max
    @return devuelve un mensaje que es que el numero está fuera del rango
    */

    private int leerEnteroEnRango(String prompt, int min, int max) {
        while (true) {
            int v = leerEntero(prompt);
            if (v >= min && v <= max) return v;
            System.out.println("Fuera de rango (" + min + " - " + max + ").");
        }
    } /* Descripción: En este método lee una respuesta de true o false, o de si o no que es s o n 
    @pre Existe un scanner
    @post Retorna un valor booleano válido
    @param prompt mensaje a mostrar al usuario
    @return muestra el mensaje que el usuario debe escribir s o n, o true o false
    */

    private boolean leerBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim().toLowerCase();
            if (s.equals("true") || s.equals("t") || s.equals("1") || s.equals("si") || s.equals("sí") || s.equals("s")) return true;
            if (s.equals("false") || s.equals("f") || s.equals("0") || s.equals("no") || s.equals("n")) return false;
            System.out.println("Escribe 's'/'n' o 'true'/'false'.");
        }
    }
}