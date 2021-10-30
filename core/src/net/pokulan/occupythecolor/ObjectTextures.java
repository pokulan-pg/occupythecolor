package net.pokulan.occupythecolor;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class ObjectTextures {
    Random x;
    public Texture[] obj;
    int txt_count;
    public int shadow;
    boolean light_souce;
    int which_light;
    int which_glare;
    boolean interactive;
    String name;

    ObjectTextures(Texture o, int s){
        x = new Random();
        obj = new Texture[1];
        txt_count = 1;
        obj[0] = o;
        shadow = s;
        light_souce = false;
        which_light = 0;
    }
    ObjectTextures(Texture o, int s, int light, int glare){
        x = new Random();
        obj = new Texture[1];
        txt_count = 1;
        obj[0] = o;
        shadow = s;
        light_souce = false;
        which_light = 0;
        light_souce = true;
        which_light = light;
    }
    ObjectTextures(Texture o, int s, boolean intt){
        x = new Random();
        obj = new Texture[1];
        txt_count = 1;
        obj[0] = o;
        shadow = s;
        light_souce = false;
        which_light = 0;
        interactive = intt;
    }
    ObjectTextures(Texture o, int s, int light, int glare,  boolean intt){
        x = new Random();
        obj = new Texture[1];
        txt_count = 1;
        obj[0] = o;
        shadow = s;
        light_souce = false;
        which_light = 0;
        light_souce = true;
        which_light = light;
        interactive = intt;
    }

    public Texture getTexture(){
        return obj[x.nextInt(txt_count)];
    }
}