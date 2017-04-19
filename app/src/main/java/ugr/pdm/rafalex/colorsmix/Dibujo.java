package ugr.pdm.rafalex.colorsmix;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Dibujo implements Serializable {

    int dibujo_coloreado_resource;
    int dibujo_resource;

    public Dibujo (int dibujo, int dibujo_coloreado) {

        this.dibujo_resource = dibujo;
        this.dibujo_coloreado_resource = dibujo_coloreado;
    }

    public int getDibujoColoreado() {

        return dibujo_coloreado_resource;
    }

    public int getDibujo() {

        return dibujo_resource;
    }
}
