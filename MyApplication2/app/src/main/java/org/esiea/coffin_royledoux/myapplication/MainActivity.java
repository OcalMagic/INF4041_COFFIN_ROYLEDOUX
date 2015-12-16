package org.esiea.coffin_royledoux.myapplication;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_hw = (TextView)findViewById(R.id.tv_hello_world);
        Button btn_hw = (Button)findViewById(R.id.btn_hello_world);

        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tv_hw.setText(dayOfMonth+" "+monthOfYear+" "+year);
            }
        }, 2015, 10, 5);

        //tv_hw.setText(getString(R.string.hello_world));
        String now = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);
        tv_hw.setText(getString(R.string.hello_world) + " " + now);
        tv_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_test();
                dpd.show();
            }
        });


        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.msg), Toast.LENGTH_LONG).show();
                intent_test();
            }
        });

    }

    private void intent_test(){
        Intent tIntent = new Intent(this, SecondActivity.class);
        startActivity(tIntent);
    }
    private void notification_test() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("My notification")
                .setContentText("Hello World !");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.toast_me){
            Toast.makeText(getApplicationContext(), R.string.hello_world, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
