package ugr.pdm.rafalex.colorsmix;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class LevelsList extends AppCompatActivity {

    //Datos para dialogos
    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    //Dibujos
    private ArrayList<Dibujo> dibujos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_list);

        //Inicializamos el array de dibujos
        dibujos.add(new Dibujo(R.drawable.cerdo));
        dibujos.add(new Dibujo(R.drawable.conejo));
        dibujos.add(new Dibujo(R.drawable.leon));
        dibujos.add(new Dibujo(R.drawable.kiwi));

        //Crea los dialogos
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.help_dialog, null);

        helpDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setView(dialogView)
                .setTitle(R.string.menu_help)
                .setNeutralButton(R.string.ok_button,null);

        menuDialog = helpDialog.create();
    }

    //Función Resume de la actividad
    @Override
    public void onResume() {

        super.onResume();

        // Establece la vista, el adaptador y la funcion al hacer click de los elementos de la lista
        ListView pairedListView = (ListView) findViewById(R.id.level_selector);
        pairedListView.setAdapter(new DibujoAdapter(dibujos));
        pairedListView.setOnItemClickListener(DibujoClickListener);

    }


    // OnItemClickListener para los dispositivos de la lista
    private AdapterView.OnItemClickListener DibujoClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {

        }
    };

    //Función onCreateOptionMenu, para añadir el estilo de nuestro action_bar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu); // set your file name
        return super.onCreateOptionsMenu(menu);
    }

    //Función onOptionItemSelected, para definir el funcionamiento de las opciones del action_bar
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == R.id.help) {

            menuDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
