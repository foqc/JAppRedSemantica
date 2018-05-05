/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.archivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author foqc
 */
public class CArchivo {

    /**
     *
     * En caso de querer leer linea por linea String bfRead; while ((bfRead =
     * bf.readLine()) != null) { //metodo que lee texto linea por linea //ciclo
     * mientras el archivo tenga datos temp = temp + bfRead;//guardadodel texto
     * del archivo }
     *
     * @param ruta
     * @return
     */
    public List<List<String>> leerArchivoTxt(String ruta) {

        List<String> listaNodosRed = new ArrayList<>();
        List<String> listaEnlaces = new ArrayList<>();

        List<List<String>> listaRedSemantica = new ArrayList<>();

        try {

            FileReader frRrchivo = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(frRrchivo);
            String temp = "";
            int numero;
            char caracter;
            char red = '%';// es un valor ilogico
            while ((numero = bf.read()) != -1) {
                //lee texto caracter por caracter y devuleve un codigo ascii mientras no sea el ultimo caracter
                caracter = (char) numero;
                if (caracter != '\n') {

                    if (caracter == '*') {
                        red = '*';
                        numero = bf.read();
                        caracter = (char) numero;
                    } else {
                        if (caracter == '+') {
                            red = '+';
                            numero = bf.read();
                            caracter = (char) numero;
                        }
                    }
                    if (red == '*') {//en caso que sea la lista de nodos red
                        if (caracter != '/') {
                            temp = temp + caracter;
                        } else {
                            if (caracter == '/') {
                                listaNodosRed.add(temp.toUpperCase());
                                temp = "";
                            }
                        }
                    } else {
                        if (red == '+') {//en caso que sea la lista de relaciones de nodos red
                            if (caracter != '-') {
                                temp = temp + caracter;
                            } else {
                                if (caracter == '-') {
                                    listaEnlaces.add(temp.toUpperCase());
                                    temp = "";
                                }
                            }
                        }
                    }
                }
            }
         listaRedSemantica.add(listaNodosRed);
         listaRedSemantica.add(listaEnlaces);
            
        } catch (Exception e) {
            System.err.println("No se encontro el archivo!");
        }
        return listaRedSemantica;
    }
}
