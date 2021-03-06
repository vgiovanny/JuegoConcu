package com.example.giovanny.juegoconcu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.Juego.JuegoActividad;
import com.example.giovanny.juegoconcu.Sockets.Client.Client2;
import com.example.giovanny.juegoconcu.Sockets.Server.SocketServerThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SalaActividad extends AppCompatActivity {

    TextView tGuestIP;
    TextView tHostIP;
    String CurrentIP="";
    String ServidorIP="";
    String message = "";
    String idU;
    ImageView bbey;

    boolean isServer;
    static final int socketServerPORT = 8081;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_actividad);
        idU="";
        tHostIP = (TextView)findViewById(R.id.tHost);
        tGuestIP = (TextView)findViewById(R.id.tGuestIPs);
        isServer =false;
        bbey=(ImageView)findViewById(R.id.bbIma);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CurrentIP = extras.getString("CurrentIP");
            ServidorIP = extras.getString("ServidorIP");
            setHost(ServidorIP);

            if(CurrentIP.equals(ServidorIP)){//es un servidor
                Thread socketServerThread = new Thread(new SocketServerThread(this));
                socketServerThread.start();
                isServer=true;
                bbey.setImageResource(R.drawable.bb1);
            }
            else{//es un cliente
                bbey.setImageResource(R.drawable.bb3);
                Client2 myClient = new Client2(this, ServidorIP,socketServerPORT,tGuestIP);
                myClient.start();
            }
        }
    }

    public void addMessage(String message) {
        this.message += message;
    }

    public String getMessage() {
        return message;
    }

    public void LaunchGame(View view) {
        Intent intent = new Intent(this, JuegoActividad.class);
        intent.putExtra("isServer",isServer);
        intent.putExtra("ServidorIP",ServidorIP);
        intent.putExtra("idU",getIdU());
        startActivity(intent);
    }

    private void setHost(String ip){
        tHostIP.setText("Player 1 _ " + ip);
    }

    public void setGuestIP(){
        tGuestIP.setText(message);
    }


    public void Mandar(Socket socket,String mnsj) throws IOException {
        DataOutputStream dOut;
        try {
            dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeUTF(mnsj);
            dOut.flush(); // Send off the data

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
        this.idU = idU;
    }

    public String Recibir(Socket socket){
        String respuesta="";

        try {
            //Log.d("HILO","socket:Closed "+socket.isClosed()+"_conected:"+socket.isConnected());
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            respuesta=dIn.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
}
