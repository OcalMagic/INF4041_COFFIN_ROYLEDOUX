package org.esiea.coffin_royledoux.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class SecondActivity extends ActionBarActivity {

    private static final String TAG = "Debugging";
    public static final String BIERS_UPDATE = "com.octip.cours.INF4042_11.BIERS_UPDATE";
    private RecyclerView rv_bieres=null;
    private JSONArray json;
    private AlertDialog.Builder ad = null;
    private AlertDialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView btn_hw = (TextView) findViewById(R.id.btn_hello_world);
        
        GetBiersServices.startActionBiers(this);

        rv_bieres = (RecyclerView) findViewById(R.id.rv_biere);
        rv_bieres.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        json = getBiersFromFile();
        rv_bieres.setAdapter(new BiersAdapter(json));

        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BiersUpdate(), intentFilter);

        ad = new AlertDialog.Builder(this);
        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();

            }
        });


        ad = new AlertDialog.Builder(this).setTitle("Validation").setMessage("Souahitez-vous valider cette action ?").setPositiveButton("yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),getString(R.string.hello_world),Toast.LENGTH_LONG).show();
            }
        }).setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(), getString(R.string.text), Toast.LENGTH_LONG).show();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert);
        final FrameLayout frameView = new FrameLayout(this);
        ad.setView(frameView);
        alertDialog = ad.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog, frameView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class BiersUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, getIntent().getAction());
            //Ajouter une notification
            // Mettre une notification ici
            rv_bieres.setAdapter(new BiersAdapter(getBiersFromFile()));
        }
    }

    public JSONArray getBiersFromFile() {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {
        private JSONArray biers;

        public BiersAdapter(JSONArray jsonarray) {
            this.biers = jsonarray;
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            LayoutInflater li = LayoutInflater.from(viewGroup.getContext());

            View v = li.inflate(R.layout.rv_bier, viewGroup, false);

            return new BierHolder(v);

        }

        @Override
        public void onBindViewHolder(BierHolder bierHolder, int i) {

            try{
                JSONObject jObj= biers.getJSONObject(i);
                String jS= jObj.getString("name");
                bierHolder.name.setText(jS);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return biers.length();
        }

        class BierHolder extends RecyclerView.ViewHolder {

            TextView name;


            public BierHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);



            }
        }


    }

}
