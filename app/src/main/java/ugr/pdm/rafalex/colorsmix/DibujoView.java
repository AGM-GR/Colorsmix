package ugr.pdm.rafalex.colorsmix;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DibujoView extends LinearLayout {

    private ImageView image;

    public DibujoView(Context context) {
        super(context);
        inflate(context, R.layout.dibujo_view, this);

        image = (ImageView) findViewById(R.id.image_list);
    }

    //Permite establecer el dibujo a mostrar
    public void setDibujo(Dibujo dibujo) {

        image.setImageResource(dibujo.getDibujo());
    }
}
