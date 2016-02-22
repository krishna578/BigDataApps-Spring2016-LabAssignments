package com.example.daras.getlocation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetLocation extends AppCompatActivity {

    Button GetLocation;
    TextView txtLongitude;
    TextView txtLatitude;
    Button Notify;
    String Altitude;

    LocationManager mgrLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        GetLocation = (Button)findViewById(R.id.btnCord);
        Notify = (Button)findViewById(R.id.btnNotify);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        mgrLocation = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(!isLocationEnabled()) {
            enableLocation();
        }


GetLocation.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        createLocationRequest();
    }
});

Notify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SendNotify();
    }
});

    }

    public  void SendNotify()
    {
        try
        {
            int notificationId = 1;
            NotificationCompat.Builder Notify = new NotificationCompat.Builder(this);
            Notify.setContentText("You are at Altitude " + Altitude + "Latitude " + txtLatitude.getText() + "Longitude " + txtLongitude.getText());
            Notify.setSmallIcon(R.drawable.ic_stat_name);
            Notify.setContentTitle("Details of your Location");
            NotificationManagerCompat NotifyManger = NotificationManagerCompat.from(this);
            NotifyManger.notify(notificationId, Notify.build());


        } catch (Exception ex)
        {
ex.printStackTrace();
        }

    }




    public  boolean isLocationEnabled()
    {

        if (!mgrLocation.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return false;
        }
        return true;
    }

    public void enableLocation() {
        // Build the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable Location Services");
        builder.setMessage("Enable Location to share location, share and navigate");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                // Show location settings when the user acknowledges the alert dialog
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        Dialog alertDialog = builder.create();
        //alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    protected void createLocationRequest() {
        try
        {

            Location Location = mgrLocation.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            Double Latitude =  Location.getLatitude();
            Double Longitude =  Location.getLongitude();
            Double Altitude = Location.getAltitude();
            txtLongitude.setText(Longitude.toString());
            txtLatitude.setText(Latitude.toString());
            this.Altitude =   Altitude.toString();

        }catch (SecurityException ex)
        {
            ex.printStackTrace();
        }
    }



}
