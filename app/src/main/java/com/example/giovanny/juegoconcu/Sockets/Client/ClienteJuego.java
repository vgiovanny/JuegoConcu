package com.example.giovanny.juegoconcu.Sockets.Client;

import android.util.Log;

import com.example.giovanny.juegoconcu.Figuras.Usuario;
import com.example.giovanny.juegoconcu.Sockets.HiloConexion;
import com.example.giovanny.juegoconcu.Juego.JuegoActividad;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by giovanny on 28/04/16.
 */
public class ClienteJuego extends Thread {

    String dstAddress;
    int dstPort;
    String response = "";
    JuegoActividad activity;
    Usuario user;
    ArrayList<Usuario> adversarios;
    HiloConexion hc;

    public ClienteJuego(JuegoActividad activity , String addr, int port,Usuario user,ArrayList<Usuario> adversarios) {
        dstAddress = addr;
        dstPort = port;
        this.activity=activity;
        this.user=user;
        this.adversarios=adversarios;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);
            String aux=activity.Recibir(socket);

            //Log.d("gioTo", "Recibi" + aux);

            hc=new HiloConexion(socket,activity,user,adversarios);
            hc.start();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {

            }
        }

    }

    public void onDestroy(){
        hc.onDestroy();
    }
}
