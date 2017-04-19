package ugr.pdm.rafalex.colorsmix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Paint extends AppCompatActivity {

    private Dibujo dibujo_seleccionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dibujo_seleccionado = (Dibujo) getIntent().getExtras().getSerializable("Dibujo");
    }
}
