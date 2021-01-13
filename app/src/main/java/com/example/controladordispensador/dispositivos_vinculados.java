package com.example.controladordispensador;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import android.os.AsyncTask;
public class dispositivos_vinculados extends ListActivity {

    private ArrayAdapter<String> mArrayAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket btSocket;
    private ArrayList<BluetoothDevice> btDeviceArray = new ArrayList<BluetoothDevice>();
    private ConnectAsyncTask connectAsyncTask;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_vinculados);

        //inicia BT
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        setListAdapter(mArrayAdapter);

        // Instance AsyncTask
        connectAsyncTask = new ConnectAsyncTask();

        //Get Bluettoth Adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Check smartphone support Bluetooth
        if(mBluetoothAdapter == null){
            //Device does not support Bluetooth
            Toast.makeText(getApplicationContext(), "El dispositivo no es compatible con bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Check Bluetooth enabled
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        // Queryng paried devices
        Set<BluetoothDevice> dispositivosEmparejados = mBluetoothAdapter.getBondedDevices();
        if(dispositivosEmparejados.size() > 0){
            for(BluetoothDevice dispositivo : dispositivosEmparejados){
                mArrayAdapter.add(dispositivo.getName() + "\n" + dispositivo.getAddress());
                btDeviceArray.add(dispositivo);
            }
        }

        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(conectar);
    }

    protected void onListItemClick(ListView l, View view, int position, long id){
        BluetoothDevice device = btDeviceArray.get(position);
        connectAsyncTask.execute(device);
    }

    private View.OnClickListener conectar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),"Conexion en proceso",Toast.LENGTH_SHORT).show();
            //Jalamos los datos a enviar a Arduino
            OutputStream mmOutStream = null;
            try {
                if(btSocket.isConnected()){
                    mmOutStream = btSocket.getOutputStream();
                    mmOutStream.write(new String("1").getBytes());
                }
            } catch (IOException e) { }
        }
    };

    private class ConnectAsyncTask extends AsyncTask<BluetoothDevice, Integer, BluetoothSocket>{

        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;

        @Override
        protected BluetoothSocket doInBackground(BluetoothDevice... device) {
            mmDevice = device[0];
            try {
                String mmUUID = "00001101-0000-1000-8000-00805F9B34FB";
                mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(mmUUID));
                mmSocket.connect();
            } catch (Exception e) { }

            return mmSocket;
        }

        @Override
        protected void onPostExecute(BluetoothSocket result) {
            btSocket = result;
        }
    }
}