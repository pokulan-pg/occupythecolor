package net.pokulan.occupythecolor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import net.pokulan.occupythecolor.View;

public class WalkParticles {
    OccupyTheColor otc;
    Sprite[] particles = new Sprite[4];
    float[] p_x;
    float[] p_y;
    float[] p_rotation;
    static float step = 0.1f;

    WalkParticles(OccupyTheColor otc, int type){
        this.otc = otc;
        p_y =  new float[4];
        if(type == 0){
            particles = otc.particles_water;
        }
        else if(type == 1){
            particles = otc.particles_mud;
        }
        p_x = new float[4];
        p_rotation = new float[4];
        for(int i = 0; i < 4; i++){
            p_x[i] = (i * 1.5f) + 2;
            p_rotation[i] = 0;
            p_y[i] = 0;
        }
    }

    void play(int x, int y){
        for(int i = 0; i < 4; i++) {
            otc.view_current.draw_sprite(particles[i], x + p_x[i], y + p_y[i]);
            particles[i].setRotation(p_rotation[i]);
            if(i < 2) {
                p_rotation[i] -= 3;
                if (p_rotation[i] > 359) p_rotation[i] = 0;
            }else{
                p_rotation[i] += 3;
                if (p_rotation[i] < 0) p_rotation[i] = 359;
            }
        }

        p_x[0] -= step;
        p_x[1] -= step;
        p_x[2] += step;
        p_x[3] += step;

        p_y[0] = (float)(2 * Math.sin(Math.abs((p_x[0] - 2) * 2)));
        p_y[1] = (float)(4 * Math.sin(Math.abs((p_x[1] - 3.5) * 1.2)));
        p_y[2] = (float)(3 * Math.sin(Math.abs((p_x[2] - 5) * 1.2)));
        p_y[3] = (float)(2.5 * Math.sin(Math.abs((p_x[3] - 6.5) * 2)));

        if(p_x[0] < 0.5) p_x[0] = 2;
        if(p_x[1] < 0.9) p_x[1] = 3.5f;
        if(p_x[2] > 7.6) p_x[2] = 5;
        if(p_x[3] > 8) p_x[3] = 6.5f;

    }
}
