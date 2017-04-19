package ugr.pdm.rafalex.colorsmix;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class DibujoAdapter extends BaseAdapter {

    //ArrayList con todos los Dibujos a mostrar
    private ArrayList<Dibujo> dibujos;

    public DibujoAdapter(ArrayList<Dibujo> dibujos) {
        this.dibujos = dibujos;

        //Cada vez que cambiamos los elementos debemos noficarlo
        notifyDataSetChanged();
    }

    //Devuelve el numero de elementos
    public int getCount() {
        return dibujos.size();
    }

    //Devuelve el elemento de una posición
    public Object getItem(int position) {
        return dibujos.get(position);
    }

    //Devulve el ID del elemento (Generalmente no se usa)
    public long getItemId(int position) {
        return position;
    }

    //Devuelve la vista de un elemento
    public View getView(int position, View convertView, ViewGroup parent) {

        //Si el contentView ya tiene un device, lo reutilizaremos con los nuevos datos
        // Si no crearemos uno nuevo
        DibujoView view;
        if (convertView == null)
            view = new DibujoView(parent.getContext());
        else
            view = (DibujoView) convertView;

        //Asignamos los valores del Device a mostrar
        view.setDibujo(dibujos.get(position));

        return view;
    }
}
