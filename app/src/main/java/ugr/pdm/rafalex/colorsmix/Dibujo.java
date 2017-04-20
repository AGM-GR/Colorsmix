package ugr.pdm.rafalex.colorsmix;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Dibujo implements Serializable {

    private int dibujo_coloreado_resource;
    private int dibujo_resource;
    private ArrayList<Integer> trozos = new ArrayList<Integer>();
    private ArrayList<Integer> colores = new ArrayList<Integer>();

    public Dibujo (int dibujo, int dibujo_coloreado, ArrayList<Integer> trozos, ArrayList<Integer> colores) {

        this.dibujo_resource = dibujo;
        this.dibujo_coloreado_resource = dibujo_coloreado;
        this.trozos = trozos;
        this.colores = colores;
    }

    public int getDibujoColoreado() {

        return dibujo_coloreado_resource;
    }

    public int getDibujo() {

        return dibujo_resource;
    }

    public ArrayList<Integer> getTrozos() {

        return trozos;
    }

    public ArrayList<Integer> getColores() {

        return colores;
    }

}
