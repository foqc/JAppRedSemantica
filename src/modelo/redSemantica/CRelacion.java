/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.redSemantica;

/**
 *
 * @author foqc
 */
public class CRelacion {
    
    private String infoRelacion;
    private CRelacion siguinteRelacion;//apuntador a nodo siguiente relacion
    private String tipo;//0(false) instancia ---- 1(true) propiedad****** I --  P
    private CNodoRed nodoRedDestino;

    public CRelacion() {
    }

    public String getInfoRelacion() {
        return infoRelacion;
    }

    public void setInfoRelacion(String infoRelacion) {
        this.infoRelacion = infoRelacion;
    }

    public CRelacion getSiguinteRelacion() {
        return siguinteRelacion;
    }

    public void setSiguinteRelacion(CRelacion siguinteRelacion) {
        this.siguinteRelacion = siguinteRelacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CNodoRed getNodoRedDestino() {
        return nodoRedDestino;
    }

    public void setNodoRedDestino(CNodoRed nodoRedDestino) {
        this.nodoRedDestino = nodoRedDestino;
    }
    
}
