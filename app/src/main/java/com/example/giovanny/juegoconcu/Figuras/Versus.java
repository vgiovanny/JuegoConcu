package com.example.giovanny.juegoconcu.Figuras;

import android.content.Context;
import android.util.Log;

import com.example.giovanny.juegoconcu.R;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 23/04/16.
 */
public class Versus {

    private Fuego Fire;
    private ArrayList <Usuario> adversarios;

    private Usuario user;

    private int cantFuego;



    public Versus(Usuario user, ArrayList<Usuario> adversarios){
        this.user=user;

        this.adversarios =adversarios;

        cantFuego=2;

        Fire= new Fuego(cantFuego);
    }

    public void loadTexture(GL10 gl, Context ctx){
        Fire.loadTexture(gl,ctx);

        for(Usuario element: adversarios){
            element.loadTexture2(gl,ctx);
        }
    }

    public void draw(GL10 gl) {
        Fire.draw(gl);
        int vidaAT=0;
        for(Usuario element: adversarios){
            vidaAT+=element.getMurio();
            element.draw2(gl);
        }
        if(vidaAT==0 && user.getGanador().equals("nadie"))
            user.setGanador(user.getIdUsuario());
        quitaVida();
    }


    public void quitaVida(){
        int quito=0;
        int chocaron=0;
        for(int i=0;i<cantFuego;i++){
            quito+=seChocaron(Fire.getX(i),Fire.getY(i));
        }
        for(Usuario element: adversarios){
            chocaron=0;
            //quito+=seChocaron(element.getXi(),element.getYi());
            chocaron=seChocaron(element.getXi(), element.getYi());
            if(chocaron!=0){
                //rebota(element);
            }
            quito+=chocaron;
        }
        user.resVida(quito);
    }

    public void rebota(Usuario element){
        float xv=element.getXi()-user.getXi()/3;
        float yv=element.getYi()-user.getYi()/3;

        element.xi += xv;
        element.yi += yv;
        user.xi    -= xv;
        user.yi    -= yv;

    }

    public int seChocaron(float x,float y){
        float dist = (float) Math.hypot(user.getXi()-x,user.getYi()-y);
        if(dist<1.78f){
            return 1;
        }
        return 0;
    }
}
