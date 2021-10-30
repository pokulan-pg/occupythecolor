package net.pokulan.occupythecolor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.pokulan.occupythecolor.View;

public class CharSkin {
    View view;
    Animation<TextureRegion>[] run;
    Sprite[] stat;
    Sprite[] hairs;
    Sprite[] eyes;
    Sprite buffer;
    Sprite[] head;
    Texture buf;

    public CharSkin(int a, int b, int c, int d, View v){
        run = new Animation[a];
        stat = new Sprite[b];
        hairs = new Sprite[c];
        eyes = new Sprite[d];
        view = v;
        head = new Sprite[b];
        buffer = new Sprite();
    }

    public Animation<TextureRegion> getRun(int i){return run[i];}
    public Sprite getRunFrame(int i, int time, boolean dir){
        buffer = new Sprite(run[i].getKeyFrame(time).getTexture());
        buffer.setScale(view.screen_x_multipler,view.screen_y_multipler);
        buffer.setOrigin(0,0);
        buffer.flip(dir, false);
        return buffer;
    }
    public Sprite getShadow(boolean flip){
        buffer = new Sprite(view.otc.shadow_player);
        buffer.flip(flip, false);
        return buffer;
    }
    public Sprite getStatic(int i, boolean flip){
        buffer = new Sprite(stat[i]);
        buffer.flip(flip, false);
        return buffer;
    }
    public Sprite getHair(int i, boolean flip){
        buffer = new Sprite(hairs[i]);
        buffer.flip(flip, false);
        return buffer;
    }
    public Sprite getEye(int i, boolean flip){
        buffer = new Sprite(eyes[i]);
        buffer.flip(flip, false);
        return buffer;
    }
    public Sprite getHead(int i, boolean flip){
        buffer = new Sprite(head[i]);
        buffer.flip(flip, false);
        return buffer;
    }

    public void setRun(int i, Animation<TextureRegion> a){
        run[i] = a;
        run[i].setPlayMode(Animation.PlayMode.LOOP);
    }
    public void setStatic(int i, Sprite a){
        stat[i] = a;
        stat[i].setScale(view.screen_x_multipler,view.screen_y_multipler);
        stat[i].setOrigin(0,0);
    }
    public void setHead(int i, Sprite a){
        head[i] = a;
        head[i].setScale(view.screen_x_multipler,view.screen_y_multipler);
        head[i].setOrigin(0,0);
    }
    public void setHairs(int i, Sprite a){
        hairs[i] = a;
        hairs[i].setScale(view.screen_x_multipler,view.screen_y_multipler);
        hairs[i].setOrigin(0,0);
    }
    public void setEyes(int i, Sprite a){
        eyes[i] = a;
        eyes[i].setScale(view.screen_x_multipler,view.screen_y_multipler);
        eyes[i].setOrigin(0,0);
    }
}
