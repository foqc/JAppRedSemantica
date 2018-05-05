/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.redSemantica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author foqc
 */
class CNodoRed {

    private String infoNodoRed;//informacion del  nodo red
    private CNodoRed siguienteNodoRed;
    private CRelacion inicioNodoRelacion;

    public CNodoRed() {
        siguienteNodoRed = null;
        inicioNodoRelacion = null;
    }

    public String getInfoNodoRed() {
        return infoNodoRed;
    }

    public void setInfoNodoRed(String infoNodoRed) {
        this.infoNodoRed = infoNodoRed;
    }

    public CNodoRed getSiguienteNodoRed() {
        return siguienteNodoRed;
    }

    public void setSiguienteNodoRed(CNodoRed siguienteNodoRed) {
        this.siguienteNodoRed = siguienteNodoRed;
    }

    public CRelacion getInicioNodoRelacion() {
        return inicioNodoRelacion;
    }

    public void setInicioNodoRelacion(CRelacion inicioNodoRelacion) {
        this.inicioNodoRelacion = inicioNodoRelacion;
    }

    public Boolean insertarNodoRed(CNodoRed nodoRedActual, String infoNodoRed) {
        if (nodoRedActual != null) {
            if(!esNodoRedRepetido(nodoRedActual,infoNodoRed)){
            CNodoRed nodoRedUltimo=ultimoNodoRed(nodoRedActual);
            CNodoRed nuevoNodoRed=new CNodoRed();
            nuevoNodoRed.setInfoNodoRed(infoNodoRed);  
            nodoRedUltimo.setSiguienteNodoRed(nuevoNodoRed);
            return true;
            }else return false;
        }else return false;
    }
    
    private Boolean esNodoRedRepetido(CNodoRed nodoRedActual,String infoNodoRed){
        if(nodoRedActual!=null){
            if(nodoRedActual.getInfoNodoRed().equals(infoNodoRed)){
                return true;
            }else{
                return esNodoRedRepetido(nodoRedActual.getSiguienteNodoRed(),infoNodoRed);
            }
        }
        else{
            return false;
        }
    }

    private CNodoRed ultimoNodoRed(CNodoRed nodoRedActual) {
        if (nodoRedActual.getSiguienteNodoRed()!= null) {
            return ultimoNodoRed(nodoRedActual.getSiguienteNodoRed());
        }else
        return nodoRedActual;
    }
    
    public CNodoRed buscarNodoRed(CNodoRed nodoRedActual,String infoNodoRed){
        if(nodoRedActual!=null){
            if(nodoRedActual.getInfoNodoRed().equals(infoNodoRed)){
                return nodoRedActual;
            }else{
                return buscarNodoRed(nodoRedActual.getSiguienteNodoRed(),infoNodoRed);
            }
        }
        else{
            return nodoRedActual;
        }
    }
    
        
    public Boolean insertarRelacion(CNodoRed nodoOrigen,CNodoRed nodoDestino,String infoRelacion,String tipoRelacion){
            CRelacion nodoRelacion = new CRelacion();
            nodoRelacion.setInfoRelacion(infoRelacion);
            nodoRelacion.setNodoRedDestino(nodoDestino);
            nodoRelacion.setTipo(tipoRelacion);
            if(nodoOrigen.getInicioNodoRelacion()==null){
                nodoOrigen.setInicioNodoRelacion(nodoRelacion); return true;}
            else{
                if(!existeRelacion(nodoOrigen.getInicioNodoRelacion(),nodoDestino.getInfoNodoRed(),infoRelacion)){
                    CRelacion ultimoNodoRelacion = ultimoNodoRelacion(nodoOrigen.getInicioNodoRelacion());
                    ultimoNodoRelacion.setSiguinteRelacion(nodoRelacion);
                    return true;
                }else return false;
            }
    }
    
    private Boolean existeRelacion(CRelacion nodoRelacionActual,String infoNodoDestino,String infoRelacion){
        if(nodoRelacionActual!=null){
            if(nodoRelacionActual.getInfoRelacion().equals(infoRelacion)&&nodoRelacionActual.getNodoRedDestino().getInfoNodoRed().equals(infoNodoDestino))
                return true;
            else return existeRelacion(nodoRelacionActual.getSiguinteRelacion(), infoNodoDestino,infoRelacion);
        }return false;
    }
    
    private CRelacion ultimoNodoRelacion(CRelacion nodoRelacionActual){
        if (nodoRelacionActual.getSiguinteRelacion()!= null) {
            return ultimoNodoRelacion(nodoRelacionActual.getSiguinteRelacion());
        }else
        return nodoRelacionActual;
    }
    
    public List<List<String>> recorrerNodosRedSemantica(CNodoRed nodoRedActual, List<List<String>> lstGeneral){
        if(nodoRedActual!=null){
            //lstGeneral almacena el nodo Red(Padres) y su sublista(hijos)
            List<String> lstRelaciones = new ArrayList<>();
                lstRelaciones=recorrerNodosEnlace(nodoRedActual.getInicioNodoRelacion(),lstRelaciones);                 
                lstRelaciones.add(0,nodoRedActual.getInfoNodoRed());
                lstGeneral.add(lstRelaciones);
            return recorrerNodosRedSemantica(nodoRedActual.getSiguienteNodoRed(),lstGeneral);
        }else return lstGeneral;
    }
    
    private List<String> recorrerNodosEnlace(CRelacion nodoRelacionActual,List<String> lstRelaciones){
        if(nodoRelacionActual!=null){
            lstRelaciones.add(nodoRelacionActual.getInfoRelacion()+" "+nodoRelacionActual.getNodoRedDestino().getInfoNodoRed());
            return recorrerNodosEnlace(nodoRelacionActual.getSiguinteRelacion(),lstRelaciones);
        }else{
            return lstRelaciones;
        }
    }
    
    public void eliminarNodoRedSemantica(CNodoRed nodoRedActual,String datoNodoEliminar){
        recorrerNodosRedRelacionesEliminar(nodoRedActual,datoNodoEliminar);// recorre la red semantica para eliminar los nodos relaciones que apunten al nodo que contenga al dato a eliminar
        eliminarNodoRed(nodoRedActual,nodoRedActual,datoNodoEliminar);//una vez que se han eliminado las relaciones hacia el nodo Red a eliminar. es momento de aliminar el nodo red en si.
    }
    
    private void recorrerNodosRedRelacionesEliminar(CNodoRed nodoRedActual,String datoNodoEliminar){
        if(nodoRedActual!=null){
            eliminarNodosEnlaceA(nodoRedActual,nodoRedActual.getInicioNodoRelacion(),datoNodoEliminar);//recorre las relaciones(sublista) del nodo red actual para eliminar los nodos relaciones que apunten al nodo que contenga al dato a eliminar
            recorrerNodosRedRelacionesEliminar(nodoRedActual.getSiguienteNodoRed(),datoNodoEliminar);
        }
    }
    
    //OJO elimina relaciones que apunten al nodo red que se va a eliminar
    private void eliminarNodosEnlaceA(CNodoRed nodoRedPadre,CRelacion nodoRelacionActual,String datoNodoEliminar){
        
        CRelacion ultimoRelacion=new CRelacion();
        if(nodoRelacionActual!=null){            
            ultimoRelacion=ultimoNodoRelacion(nodoRelacionActual);
        }
        if (nodoRelacionActual != null && nodoRelacionActual.getSiguinteRelacion()!= null) {            
            if ((nodoRelacionActual.getSiguinteRelacion().getNodoRedDestino().getInfoNodoRed().equals(datoNodoEliminar)) && !(nodoRelacionActual.getSiguinteRelacion().equals(ultimoRelacion))) //para que no me borre el ultimo elemento de la lista va el (objCNodoElemento.getSiguiente().equals(this.ultimo))
            {   
                nodoRelacionActual.setSiguinteRelacion(nodoRelacionActual.getSiguinteRelacion().getSiguinteRelacion());//elimina en entre el primer y ultimo
            } else {                
                if (nodoRelacionActual.getSiguinteRelacion().equals(ultimoRelacion) && (nodoRelacionActual.getSiguinteRelacion().getNodoRedDestino().getInfoNodoRed().equals(datoNodoEliminar))) {//elimina el ultimo elemento                    
                    nodoRelacionActual.setSiguinteRelacion(null);
                }else{                   
                    if(nodoRelacionActual.getNodoRedDestino().getInfoNodoRed().equals(datoNodoEliminar)){//si hay relacion con nodo destino que contiene el dato a eliminar                       
                        nodoRedPadre.setInicioNodoRelacion(nodoRelacionActual.getSiguinteRelacion());//elimina el primer elemento de la lista
                    }
                }
            }
            eliminarNodosEnlaceA(nodoRedPadre,nodoRelacionActual.getSiguinteRelacion(), datoNodoEliminar);
        }else{//en el caso que haya solo un elemento en la lista(eso se cumple con a condicion de abajo[nodoRedPadre.getInicioNodoRelacion()!= null&&nodoRedPadre.getInicioNodoRelacion().equals(ultimoRelacion)&&(nodoRedPadre.getInicioNodoRelacion().getNodoRedDestino().getInfoNodoRed().equals(datoEliminar)])  
            if(nodoRedPadre.getInicioNodoRelacion()!= null&&nodoRedPadre.getInicioNodoRelacion().equals(ultimoRelacion)&&(nodoRedPadre.getInicioNodoRelacion().getNodoRedDestino().getInfoNodoRed().equals(datoNodoEliminar))){
                nodoRedPadre.setInicioNodoRelacion(null);ultimoRelacion= null;}
        }
    }
    
    private void eliminarNodoRed(CNodoRed nodoRedPrimero,CNodoRed nodoRedActual,String datoNodoEliminar){
        CNodoRed ultimoNodoRed=new CNodoRed();
        if(nodoRedActual!=null){            
            ultimoNodoRed=ultimoNodoRed(nodoRedActual);            
        }
        if (nodoRedActual != null && nodoRedActual.getSiguienteNodoRed()!= null) {
            if ((nodoRedActual.getSiguienteNodoRed().getInfoNodoRed().equals(datoNodoEliminar)) && !(nodoRedActual.getSiguienteNodoRed().equals(ultimoNodoRed))) //para que no me borre el ultimo elemento de la lista va el (objCNodoElemento.getSiguiente().equals(this.ultimo))
            {
                nodoRedActual.setSiguienteNodoRed(nodoRedActual.getSiguienteNodoRed().getSiguienteNodoRed());//elimina en entre el primer y ultimo
            } else {
                if (nodoRedActual.getSiguienteNodoRed().equals(ultimoNodoRed) && (nodoRedActual.getSiguienteNodoRed().getInfoNodoRed().equals(datoNodoEliminar))) {//elimina el ultimo elemento
                    nodoRedActual.setSiguienteNodoRed(null);
                }/*else{
                    if(nodoRedActual.getInfoNodoRed().equals(datoEliminar)){
                        nodoRedPrimero=nodoRedActual=nodoRedActual.getSiguienteNodoRed();//elimina el primer elemento de la lista
                    
                    } ESTA PARTE NO LO USO YA QUE SE DEBE ACTUALIZAR EL INICIO DE LA RED SEMANTICA Y ESE PUNTERO SE ENCUENTRA EN LA CRedSemantica y desde aqui no se puede actualizar a ese puntero
                }*/ 
            }
            eliminarNodoRed(nodoRedPrimero,nodoRedActual.getSiguienteNodoRed(), datoNodoEliminar);
        }/*else{//en el caso que haya solo un elemento en la lista  
            if(nodoRedPrimero.equals(ultimoNodoRed)&&nodoRedPrimero.getInfoNodoRed().equals(datoEliminar))                  
                    nodoRedPrimero=null; ultimoNodoRed= null;
                    ESTA PARTE TAMPOCO LO USO POR LA MISMA EXPLICACION DE ARRIBA
        }*/        
    }
    
    public CNodoRed primerNodoRedEliminar(CNodoRed nodoRedActual,String datoEliminar){
        if(nodoRedActual.getInfoNodoRed().equals(datoEliminar))
        return nodoRedActual.getSiguienteNodoRed();
        else return null;
    }
    
    public Boolean existeSoloUnNodoRed(CNodoRed nodoRedActual,String datoEliminar){
        return nodoRedActual.getSiguienteNodoRed()==null;
    }
    
    //OJO elimina relacion dada la info de la relaciona eliminar(datoEliminar)
    public void eliminarEnlace(CNodoRed nodoRedPadre,CNodoRed nodoRedDestino,CRelacion nodoRelacionActual,String datoEnlaceEliminar){
        CRelacion ultimoRelacion=new CRelacion();
        if(nodoRelacionActual!=null){            
            ultimoRelacion=ultimoNodoRelacion(nodoRelacionActual);
        }
        
        if (nodoRelacionActual != null && nodoRelacionActual.getSiguinteRelacion()!= null) {
            if ((nodoRelacionActual.getSiguinteRelacion().getInfoRelacion().equals(datoEnlaceEliminar)) && !(nodoRelacionActual.getSiguinteRelacion().equals(ultimoRelacion))&&(nodoRelacionActual.getSiguinteRelacion().getNodoRedDestino().equals(nodoRedDestino))) //para que no me borre el ultimo elemento de la lista va el (objCNodoElemento.getSiguiente().equals(this.ultimo))
            {
                nodoRelacionActual.setSiguinteRelacion(nodoRelacionActual.getSiguinteRelacion().getSiguinteRelacion());//elimina en entre el primer y ultimo
            } else {
                if (nodoRelacionActual.getSiguinteRelacion().equals(ultimoRelacion) && (nodoRelacionActual.getSiguinteRelacion().getInfoRelacion().equals(datoEnlaceEliminar))&&(nodoRelacionActual.getSiguinteRelacion().getNodoRedDestino().equals(nodoRedDestino))) {//elimina el ultimo elemento
                    nodoRelacionActual.setSiguinteRelacion(null);
                }else{
                    if(nodoRelacionActual.getInfoRelacion().equals(datoEnlaceEliminar)&&(nodoRelacionActual.getNodoRedDestino().equals(nodoRedDestino)))
                        nodoRedPadre.setInicioNodoRelacion(nodoRelacionActual.getSiguinteRelacion());//elimina el primer elemento de la lista
                }
            }
            eliminarEnlace(nodoRedPadre,nodoRedDestino,nodoRelacionActual.getSiguinteRelacion(), datoEnlaceEliminar);
        }else{//en el caso que haya solo un elemento en la lista  
            if(nodoRedPadre.getInicioNodoRelacion()!= null&&nodoRedPadre.getInicioNodoRelacion().equals(ultimoRelacion)&&nodoRedPadre.getInicioNodoRelacion().getInfoRelacion().equals(datoEnlaceEliminar)&&(nodoRedPadre.getInicioNodoRelacion().getNodoRedDestino().equals(nodoRedDestino))){                  
                     nodoRedPadre.setInicioNodoRelacion(null);ultimoRelacion= null;
            }
        }
    }
    
    //nodo red propiedades se usa para comparar relaciones de instancia y de propiedad y al final de prioridad a la relacion de propiedad
    //nodoRedPropiedades me sirve para poder recorrer todas las relaciones del NODO RED Deseado, ver metododevolverRelacionesDe().
    public List<String> propiedadesDe(CNodoRed nodoRedPropiedadesDe,CRelacion nodoRelacionActual,List<String> lstPropiedades){
        if(nodoRelacionActual!=null){
            if(nodoRelacionActual.getTipo().equals("I")){
                lstPropiedades.add(nodoRelacionActual.getInfoRelacion()+" "+nodoRelacionActual.getNodoRedDestino().getInfoNodoRed());
                propiedadesDeIstancia(nodoRedPropiedadesDe,nodoRelacionActual.getNodoRedDestino().getInicioNodoRelacion(),lstPropiedades);
                return propiedadesDe(nodoRedPropiedadesDe,nodoRelacionActual.getSiguinteRelacion(), lstPropiedades);
            }else{
                lstPropiedades.add(nodoRelacionActual.getInfoRelacion()+" "+nodoRelacionActual.getNodoRedDestino().getInfoNodoRed());
                return propiedadesDe(nodoRedPropiedadesDe,nodoRelacionActual.getSiguinteRelacion(), lstPropiedades);
            }
        }
        return lstPropiedades;
    }
    
    
    private List<String> propiedadesDeIstancia(CNodoRed nodoRedPropiedadesDe,CRelacion nodoRelacionActual,List<String> lstPropiedades){
        if(nodoRelacionActual!=null){ 
            if(!existePropiedadEnLista(nodoRedPropiedadesDe.getInicioNodoRelacion(),nodoRelacionActual.getInfoRelacion())&&!existeRepetidoEnListaPropiedades(nodoRedPropiedadesDe.getInicioNodoRelacion(), nodoRelacionActual.getInfoRelacion()+" "+nodoRelacionActual.getNodoRedDestino().getInfoNodoRed())){
            lstPropiedades.add(nodoRelacionActual.getInfoRelacion()+" "+nodoRelacionActual.getNodoRedDestino().getInfoNodoRed());
                
            if(nodoRelacionActual.getTipo().equals("I"))
                    return propiedadesDeIstancia(nodoRedPropiedadesDe, nodoRelacionActual.getNodoRedDestino().getInicioNodoRelacion(), lstPropiedades);
                else
                    return propiedadesDeIstancia(nodoRedPropiedadesDe,nodoRelacionActual.getSiguinteRelacion(), lstPropiedades);
            }else{
                return propiedadesDeIstancia(nodoRedPropiedadesDe,nodoRelacionActual.getSiguinteRelacion(), lstPropiedades);
            }
        }else return lstPropiedades;
    }
    //metodo determina si existe una relacion de tipo propiedad del nodo red requerido(caracteristicas de JUAN), comparando la relacion a insertar con la lista de relaciones del nodo requerido. 
    //ejemplo> (es).equals(es) y ademas se de tipo propiedad(P)// aqui comparo solo la relacion 
    private Boolean existePropiedadEnLista(CRelacion relacionActual, String relacionActualAInsertar){
        
        if(relacionActual!=null){
            if(relacionActual.getInfoRelacion().equals(relacionActualAInsertar)&&relacionActual.getTipo().equals("P"))
                return true;
            else
                return existePropiedadEnLista(relacionActual.getSiguinteRelacion(),relacionActualAInsertar);
        }return false;
    }
    //metodo que determina si existe en las relaciones del nodo requerido una relacion que apunte al mismo nodo red destino al que se va a insertar
    //ejemplo> (es RAZA HUMANA).equals(es RAZA HUMANA)//aqui compara la relacion mas al nodo que apunta
    private Boolean existeRepetidoEnListaPropiedades(CRelacion relacionActual, String relacionActualAInsertar){
        
        if(relacionActual!=null){
            String rel=relacionActual.getInfoRelacion()+" "+relacionActual.getNodoRedDestino().getInfoNodoRed();
            if(rel.equals(relacionActualAInsertar))
                return true;
            else
                return existeRepetidoEnListaPropiedades(relacionActual.getSiguinteRelacion(),relacionActualAInsertar);
        }return false;
    }
    
    public List<String> buscaRelacionConNodoDestino(CNodoRed nodoRedActual,String infoNodoRedDestino,List<String> relaciones){
        if(nodoRedActual!=null&&!nodoRedActual.getInfoNodoRed().equals(infoNodoRedDestino)){
            if(existeRelacionConNodoDestino(nodoRedActual.getInicioNodoRelacion(),infoNodoRedDestino,relaciones)){
                relaciones.add(nodoRedActual.getInfoNodoRed());
            }
            return buscaRelacionConNodoDestino(nodoRedActual.getSiguienteNodoRed(), infoNodoRedDestino,relaciones);
            
        }return relaciones;
    }
    
    private Boolean existeRelacionConNodoDestino(CRelacion relacionActual,String infoNodoRedDestino,List<String> relaciones){
        if(relacionActual!=null){
            if(relacionActual.getNodoRedDestino().getInfoNodoRed().equals(infoNodoRedDestino)&&relacionActual.getTipo().equals("I"))
                return true;
            else{               
                if(relacionActual.getTipo().equals("I")){
                    if(existeRelacionConNodoDestino(relacionActual.getNodoRedDestino().getInicioNodoRelacion(), infoNodoRedDestino,relaciones))
                        return true;
                }
                    return(existeRelacionConNodoDestino(relacionActual.getSiguinteRelacion(), infoNodoRedDestino,relaciones));
                    
            }            
        }return false;
    }
    
    public List<String> buscaRelacionConNodoDestino1(CNodoRed nodoRedActual,String infoNodoRedDestino,List<String> relaciones,List<String> rel){
        if(nodoRedActual!=null&&!nodoRedActual.getInfoNodoRed().equals(infoNodoRedDestino)){
            if(existeRelacionConNodoDestino(nodoRedActual.getInicioNodoRelacion(),infoNodoRedDestino,relaciones)){
                relaciones.add(nodoRedActual.getInfoNodoRed());
            }
            return buscaRelacionConNodoDestino1(nodoRedActual.getSiguienteNodoRed(), infoNodoRedDestino,relaciones,rel);
            
        }return relaciones;
    }
    
    private Boolean existeRelacionConNodoDestino1(CRelacion relacionActual,String infoNodoRedDestino,List<String> relaciones,List<String> rel){
        if(relacionActual!=null){
            if(relacionActual.getNodoRedDestino().getInfoNodoRed().equals(infoNodoRedDestino))
                return true;
            else{
                    if(existeRelacionConNodoDestino(relacionActual.getNodoRedDestino().getInicioNodoRelacion(), infoNodoRedDestino,relaciones))
                        return true;

                    return(existeRelacionConNodoDestino(relacionActual.getSiguinteRelacion(), infoNodoRedDestino,relaciones));
                    
            }            
        }return false;
    }
}
    

