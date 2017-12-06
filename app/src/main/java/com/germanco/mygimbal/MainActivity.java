package com.germanco.mygimbal;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    PlaceManager placeManager;
    PlaceEventListener placeEventListener;
    TextView nombreLugar, beaconID, beaconUUID, nombreBeacon, urlBeacon, rssiBeacon, bateriaBeacon, tempBeacon;
    String lugar, beaconIdentifier, beaconUuid, beaconName, beaconURL;
    int rssi;
    Beacon.BatteryLevel beaconBatteryLevel;
    int beaconTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombreLugar=(TextView)findViewById(R.id.nombreLugar);
        beaconID=(TextView)findViewById(R.id.beaconID);
        beaconUUID=(TextView)findViewById(R.id.beaconUUID);
        nombreBeacon=(TextView)findViewById(R.id.nombreBeacon);
        urlBeacon=(TextView)findViewById(R.id.urlBeacon);
        rssiBeacon=(TextView)findViewById(R.id.beaconRSSI);
        bateriaBeacon=(TextView)findViewById(R.id.beaconBateria);
        tempBeacon=(TextView)findViewById(R.id.beaconTemp);
        Gimbal.setApiKey(this.getApplication(), "096e72d5-6b75-42ef-8228-76aa932096b2");
        Gimbal.start();
        Gimbal.registerForPush("748766984715");
        Log.d("ESTADO DEL SERVICIO", String.valueOf(Gimbal.isStarted()));
        PlaceManager.getInstance().startMonitoring();
        Log.d("ESTADO DE MONITOREO", String.valueOf(PlaceManager.getInstance().isMonitoring()));
        CommunicationManager.getInstance().startReceivingCommunications();

        placeEventListener= new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                super.onVisitStart(visit);
                Log.d("EVENT LISTENER","onVisitStart()");
                lugar=visit.getPlace().getName();
                nombreLugar.setText(lugar);
            }

            @Override
            public void onVisitEnd(Visit visit) {
                super.onVisitEnd(visit);
            }

            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting, List<Visit> list) {
                super.onBeaconSighting(beaconSighting, list);
                Log.d("EVENT LISTENER","onBeaconSighting()");
                beaconIdentifier=beaconSighting.getBeacon().getIdentifier();
                beaconUuid=beaconSighting.getBeacon().getUuid();
                beaconName=beaconSighting.getBeacon().getName();
               // beaconURL=beaconSighting.getBeacon().getIconURL();
                rssi=beaconSighting.getRSSI();
                beaconBatteryLevel=beaconSighting.getBeacon().getBatteryLevel();
                beaconTemp=beaconSighting.getBeacon().getTemperature();

                beaconID.setText(beaconIdentifier);
                beaconUUID.setText(beaconUuid);
                nombreBeacon.setText(beaconName);
                urlBeacon.setText("________");
                rssiBeacon.setText(String.valueOf(rssi));
                bateriaBeacon.setText(String.valueOf(beaconBatteryLevel));
                tempBeacon.setText(String.valueOf(beaconTemp));
                System.out.println("VALORES DEL BEACON: ");
                System.out.println("Identificador: "+beaconIdentifier);
                System.out.println("UUID: "+beaconUuid);
                System.out.println("Nombre: "+beaconName);
                System.out.println("URL: "+beaconURL);
                System.out.println("RSSI: "+rssi);
                System.out.println("Nivel de bateria: "+beaconBatteryLevel);
                System.out.println("Temperatura (°F) :"+beaconTemp);


            }
        };
        PlaceManager.getInstance().addListener(placeEventListener);
    }
}
