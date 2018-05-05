/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.redSemantica;

import java.util.ArrayList;
import java.util.List;
import modelo.archivo.CArchivo;

/**
 *
 * @author foqc
 */
public class CRedSemantica {

    private CNodoRed inicioRS;//inicio red Semantica

    public CRedSemantica() {
    }

    public void cargarRedSemantica(String ruta) {
        CArchivo archivo = new CArchivo();
        List<List<String>> listaRedSemantica = archivo.leerArchivoTxt(ruta);
        if (listaRedSemantica != null) {
            for (int j = 0; j < listaRedSemantica.size(); j++) {
                for (int k = 0; k < listaRedSemantica.get(j).size(); k++) {
                    if (j == 0) {//en la primera sublista estan los nodos red                        
                            insertarNodoRed(listaRedSemantica.get(j).get(k));                        
                    } else {//en las siguientes sublistan estan los enlaces de los nodos red
                        if (k + 3 < listaRedSemantica.get(j).size()) {//asigno valor info origen(k) - info Destino(k+3) - info relacion(k+1)- tipo relacion(k+2)
                            insertarRelacion(listaRedSemantica.get(j).get(k), listaRedSemantica.get(j).get(k + 3), listaRedSemantica.get(j).get(k + 1), listaRedSemantica.get(j).get(k + 2));
                            k+=3;
                        }
                    }
                }
            }
        }
    }
    
    public Boolean insertarNodoRed(String infoNodoRed){
        if(inicioRS==null){
            inicioRS =new CNodoRed();
            inicioRS.setInfoNodoRed(infoNodoRed);
            return true;
        }else{
            return inicioRS.insertarNodoRed(inicioRS, infoNodoRed);
        }
    }
    
    public Boolean insertarRelacion(String infoNodoOrigen,String infoNodoDestino,String infoNodoRelacion,String infoTipoRelacion){
        if(inicioRS!=null){
            CNodoRed nodoOrigen=inicioRS.buscarNodoRed(inicioRS,infoNodoOrigen);
            CNodoRed nodoDestino=null;
            if(nodoOrigen!=null&&nodoOrigen.getSiguienteNodoRed()!=null)
                nodoDestino=inicioRS.buscarNodoRed(nodoOrigen.getSiguienteNodoRed(),infoNodoDestino);

            if(nodoOrigen!=null&&nodoDestino!=null)
                return inicioRS.insertarRelacion(nodoOrigen, nodoDestino, infoNodoRelacion, infoTipoRelacion);
            else return false;
        }return false;
    }

    public List<List<String>> recorrerNodosRedSemantica() {
        List<List<String>> lstGeneral=new ArrayList<>();
        if(inicioRS!=null)
            return inicioRS.recorrerNodosRedSemantica(inicioRS,lstGeneral);
        else return null;
    }
    
    public void eliminarNodoRed(String datoEliminar){
        if(!inicioRS.existeSoloUnNodoRed(inicioRS, datoEliminar)){//si NO existe un solo nodo en la red semantica
            if(inicioRS.getInfoNodoRed().equals(datoEliminar))//este es un caso particular en donde el dato a eliminar se encuentre en el primer nodo red de la red semantica
                inicioRS=inicioRS.primerNodoRedEliminar(inicioRS, datoEliminar);//actualizo el inicio de la red semantica
            else//si el dato a eliminar NO esta en la primera posicion
                inicioRS.eliminarNodoRedSemantica(inicioRS, datoEliminar);
        }else{
            if(inicioRS.getInfoNodoRed().equals(datoEliminar))
                inicioRS=null;//si existe un solo nodo en la red semantica y es el dato a eliminar actualizo el inicio de la mima
        }
    }
    
    public void eliminarNodosRelacion(String infoNodoRedOrigen,String infoNodoRedDestino,String datoEliminar){
        CNodoRed nodoOrigen=inicioRS.buscarNodoRed(inicioRS,infoNodoRedOrigen);
        CNodoRed nodoDestino=inicioRS.buscarNodoRed(inicioRS.getSiguienteNodoRed(),infoNodoRedDestino);
        if(nodoOrigen!=null&&nodoDestino!=null){
            if(nodoOrigen.getInicioNodoRelacion()!=null)
                inicioRS.eliminarEnlace(nodoOrigen, nodoDestino, nodoOrigen.getInicioNodoRelacion(), datoEliminar);
        }
    }
    
    public List<String> propiedadesDe(String infoNodoRed,List<String> lstPropiedades){
        if(inicioRS!=null){
            CNodoRed nodoBuscado=this.inicioRS.buscarNodoRed(this.inicioRS, infoNodoRed);
            if(nodoBuscado!=null)
                return this.inicioRS.propiedadesDe(nodoBuscado,nodoBuscado.getInicioNodoRelacion(), lstPropiedades);
            else return lstPropiedades;
        }else return lstPropiedades;
    }
    
    public List<String> perteneceA(String infoNodoRed,List<String> lstQuienesSon){
        if(inicioRS!=null){
            CNodoRed nodoBuscado=this.inicioRS.buscarNodoRed(this.inicioRS, infoNodoRed);
            if(nodoBuscado!=null)
                return this.inicioRS.buscaRelacionConNodoDestino(inicioRS, infoNodoRed, lstQuienesSon);
            else return lstQuienesSon;
        }else return lstQuienesSon;
            
    }
        public List<String> Quienes(String infoNodoRed,List<String> lstQuienesSon){
        if(inicioRS!=null){
            CNodoRed nodoBuscado=this.inicioRS.buscarNodoRed(this.inicioRS, infoNodoRed);
            if(nodoBuscado!=null)
                return this.inicioRS.buscaRelacionConNodoDestino(inicioRS, infoNodoRed, lstQuienesSon);
            else return lstQuienesSon;
        }else return lstQuienesSon;
            
    }

}
