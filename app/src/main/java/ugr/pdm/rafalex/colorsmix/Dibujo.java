package ugr.pdm.rafalex.colorsmix;

import java.io.Serializable;

public class Dibujo implements Serializable {

    int dibujo_coloreado_resource;
    int dibujo_resource;

    public Dibujo (int dibujo, int dibujo_coloreado) {

        this.dibujo_coloreado_resource = dibujo_coloreado;
        this.dibujo_resource = dibujo;
    }

    public int getDibujoColoreado() {

        return dibujo_coloreado_resource;
    }

    public int getDibujo() {

        return dibujo_resource;
    }
}
