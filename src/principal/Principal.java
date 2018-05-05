/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import modelo.redSemantica.CRedSemantica;

/**
 *
 * @author foqc
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        CRedSemantica redSemantica = new CRedSemantica();
        List<String> lstPropiedades;
        InputStreamReader leer = new InputStreamReader(System.in);
        BufferedReader buff = new BufferedReader(leer);
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do {
            muestraMenu();
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    redSemantica.cargarRedSemantica("/root/Desktop/RedSemanticaMortal.txt");
                    System.out.println("****RECORRIDO***");
                    recorrerRedSemantica(redSemantica.recorrerNodosRedSemantica());
                }
                break;

                case 2: {
                    System.out.println("****RECORRIDO***");
                    recorrerRedSemantica(redSemantica.recorrerNodosRedSemantica());
                }
                break;
                case 3: {
                    System.out.println("Ingrese el dato para el nuevo nodo red");
                    if (redSemantica.insertarNodoRed(buff.readLine().toUpperCase())) {
                        System.out.println("Se ha insertado correctamente el nuevo nodo red.");
                    } else {
                        System.out.println("No se ha insertado correctamente el nuevo nodo red.");
                    }
                    System.out.println("****RECORRIDO***");
                    recorrerRedSemantica(redSemantica.recorrerNodosRedSemantica());
                }
                break;
                case 4: {
                    System.out.println("Ingrese los datos de la ralacion en el siguiente orden (info Origen - info Destino - info Relacion - Tipo Relacion)");
                    if (redSemantica.insertarRelacion(buff.readLine().toUpperCase(), buff.readLine().toUpperCase(), buff.readLine().toUpperCase(), buff.readLine().toUpperCase())) {
                        System.out.println("Se ha insertado correctamente la relacion.");
                    } else {
                        System.out.println("No se ha insertado correctamente la relacion.");
                    }
                    System.out.println("****RECORRIDO***");
                    recorrerRedSemantica(redSemantica.recorrerNodosRedSemantica());
                }
                break;
                case 5: {
                    System.out.println("Ingrese el dato a eliminar de un nodo red");
                    redSemantica.eliminarNodoRed(buff.readLine().toUpperCase());
                    System.out.println("****RECORRIDO***");
                    recorrerRedSemantica(redSemantica.recorrerNodosRedSemantica());
                }
                break;
                case 6: {
                    System.out.println("Ingrese datos en el siguiente orden: (info Origen - info Destino - relacion a eliminar)");

                    String origen = buff.readLine().toUpperCase();
                    String destino = buff.readLine().toUpperCase();
                    String relacion = buff.readLine().toUpperCase();
                    redSemantica.eliminarNodosRelacion(origen, destino, relacion);
                    System.out.println("****RECORRIDO***");
                    recorrerRedSemantica(redSemantica.recorrerNodosRedSemantica());
                }
                break;
                case 7: {
                    System.out.println("¿Las características de que entidad desea buscar?");
                    lstPropiedades = new ArrayList<>();
                    recorrerPropiedades(redSemantica.propiedadesDe(buff.readLine().toUpperCase(), lstPropiedades));
                }
                break;
                case 8: {
                    System.out.println("¿Quienes pertenecen a ...?");
                    List<String> lstQuienesSon1 = new ArrayList<>();
                    recorrerQuienesSon(redSemantica.perteneceA(buff.readLine().toUpperCase(), lstQuienesSon1));
                }
                break;

                case 0:
                    System.exit(0);
                default:
                    System.out.println("Opcion no existe!");
            }
        } while (opcion != 0);
    }

    private static void muestraMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("**************************************************************************************\n");
        menu.append("*  SELECCIONE EL UNA ACCION A REALIZAR ¿Qué desea realizar?    *\n");
        menu.append("*  1.- GENERAR RED SEMÁNTICA  2.- MOSTRAR RED SEMÁNTICA\n   3.- INERTAR NODO RED       4.- INSERTAR RELACIÓN\n   5.- ELIMINAR NODO RED      6.- ELIMINAR RELACIÓN\n   7.- CARACTERISTCAS         8.- PERTENECE 0.- SALIR   *\n");
        menu.append("**************************************************************************************\n");
        System.out.println(menu.toString());
    }

    private static void recorrerRedSemantica(List<List<String>> lstInfoRedSemantica) {
        String aux;
        if (lstInfoRedSemantica != null) {
            System.out.println("Numero nodos Red: " + lstInfoRedSemantica.size());
            for (List<String> lstInfoRedSemantica1 : lstInfoRedSemantica) {
                for (int j = 0; j < lstInfoRedSemantica1.size(); j++) {
                    aux = lstInfoRedSemantica1.get(j);
                    if (j == 0) {
                        System.out.print("Nodo Red Padre [" + aux + "] ->");
                    } else {
                        System.out.print("  (" + aux + ")->");
                    }
                }
                System.out.println("");
            }
            System.out.println("");
        } else {
            System.out.println("No se ha creado aún la red semántica!");
        }
    }

    private static void recorrerPropiedades(List<String> lstPropiedades) {
        if (!lstPropiedades.isEmpty()) {
            for (String lstPropiedad : lstPropiedades) {
                System.out.println(lstPropiedad);
            }
        } else {
            System.out.println("No existe niguna caracteriticas para mostrar!");
        }
    }

    private static void recorrerQuienesSon(List<String> lstQienesSon) {
        if (!lstQienesSon.isEmpty()) {
            for (String lstQienesSon1 : lstQienesSon) {
                System.out.println(lstQienesSon1);
            }
        } else {
            System.out.println("No existe nigun PERTENECE A... para mostrar!");
        }
    }
}
