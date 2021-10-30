package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.pokulan.occupythecolor.WalkParticles;
import net.pokulan.occupythecolor.View;

public class Player implements Comparable<Player>{
    View view;
    OccupyTheColor otc;
    String name;
    WalkParticles water_part;
    WalkParticles mud_part;
    int water_particles;
    int race;
    int animation, hairs, eyes;
    float pos_x, pos_y, pos_z, new_x, new_y, difx, dify;
    int health;
    float speed;
    boolean dir;
    boolean other;
    boolean move;
    float head_y;
    float head_x;
    float timeout;
    int step_timer;
    boolean step_sound;
    int[][] foot_steps;
    int id;

    public Player(int anim, View view, OccupyTheColor otc){
        water_part = new WalkParticles(otc, 0);
        mud_part = new WalkParticles(otc, 1);
        health = 100;
        pos_x = new_x = 40;
        pos_y = new_y = 40;
        pos_z = 0;
        speed = 0.35f;
        id = 0;
        animation = anim;
        name = "";
        this.view = view;
        this.otc = otc;
        race = 0;
        timeout = 0;
        difx = dify = 0;
        step_sound = false;
        dir = false;
        other = false;
        move = false;
        head_y = head_x = 0;
        foot_steps = new int[2][12];
        for(int i = 0; i < 12; i ++){
            foot_steps[0][i] = -50;
            foot_steps[1][i] = -50;
        }
        step_timer = 0;
    }
    public Player(View view, OccupyTheColor otc){
        water_part = new WalkParticles(otc, 0);
        mud_part = new WalkParticles(otc, 1);
        health = 100;
        pos_x = new_x = 40;
        pos_y = new_y = 40;
        id = 0;
        pos_z = 0;
        speed = 0.35f;
        animation = 0;
        name = "";
        this.view = view;
        race = 0;
        difx = dify = 0;
        dir = false;
        other = false;
        move = false;
        step_sound = false;
        timeout = 0;
        head_y = head_x = 0;
        foot_steps = new int[2][12];
        for(int i = 0; i < 12; i ++){
            foot_steps[0][i] = -50;
            foot_steps[1][i] = -50;
        }
        step_timer = 0;
    }

    public void reload(float x, float y, float z, int h, int anim, int hairs, int eyes, String s, int i, float t){
        health = h;
        new_x = x;
        this.hairs = hairs;
        this.eyes = eyes;
        new_y = y;
        pos_z = z;
        animation = anim;
        name = s;
        id = i;
        timeout = t;
    }

    public String pack_data(){
        return pos_x + " " + pos_y + " " + pos_z + " " + health + " " + animation + " " + hairs + " " + eyes;
    }

    public void setOther(boolean x){
        other = x;
    }

    public void draw(int timer, boolean draw_health, int field){
        boolean stat = false;
        if(!move) {
            view.draw_sprite(view.otc.skins.getShadow(dir), pos_x, pos_y - 0.8f);
            view.draw_sprite(view.otc.skins.getStatic(animation, dir), pos_x, pos_y);
            view.draw_sprite(view.otc.skins.getHead(animation, dir), pos_x, pos_y - 0.2f - (float)(Math.sin(head_x)*0.2f));
            view.draw_sprite(view.otc.skins.getEye(eyes, dir), pos_x, pos_y - 0.2f - (float)(Math.sin(head_x)*0.2f));
            view.draw_sprite(view.otc.skins.getHair(hairs, dir), pos_x, pos_y - 0.2f - (float)(Math.sin(head_x)*0.2f));
            head_x += Math.PI / 32.0f;
            if(head_x >= Math.PI*2) head_x= 0;
            if(field == 9) view.draw_texture(view.otc.shalow_shader,pos_x + 1, pos_y); // WATER SHALOW SHADER
        }else{
            view.draw_sprite(view.otc.skins.getShadow(dir), pos_x - (float)(Math.sin(head_x)*0.5f), pos_y - 0.8f);
            view.draw_sprite(view.otc.skins.getRunFrame(animation, timer, dir), pos_x, pos_y);
            view.draw_sprite(view.otc.skins.getHead(animation, dir), pos_x - (float)(Math.sin(head_x)*0.2f), pos_y - 0.5f - (float)(Math.sin(head_x)*0.5f));
            view.draw_sprite(view.otc.skins.getEye(eyes, dir), pos_x - (float)(Math.sin(head_x)*0.2f), pos_y - 0.5f - (float)(Math.sin(head_x)*0.5f));
            view.draw_sprite(view.otc.skins.getHair(hairs, dir), pos_x - (float)(Math.sin(head_x)*0.2f), pos_y - 0.5f - (float)(Math.sin(head_x)*0.5f));
            head_x += Math.PI / 8.0f;
            if(head_x >= Math.PI*2) head_x= 0;
            step_timer++;
            if(step_timer >= 8){
                step_timer = 0;
            }
            move = false;
            mud_part.play((int)pos_x, (int)pos_y);
        }
    }

    public int getStatic_anim(){return animation;}
    public int getHead(){return animation;}
    public int getHairs(){return hairs;}
    public int getEyes(){return eyes;}

    public float getPos_x() {
        return pos_x;
    }

    public float getPos_y() {
        return pos_y;
    }

    public float getPos_z() {
        return pos_z;
    }
    public void addPos_y(float i, float s) {
        pos_y += (speed * s) * i;
        move = true;
    }
    public void addPos_x(float i, float s) {
        pos_x += (speed * s) * i;
        if(i <= 0)dir = false;
        else dir = true;
        move = true;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHairs(int i){hairs = i;}
    public void setEyes(int i){eyes = i;}

    public void setPos(float pos_x, float pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        new_x = pos_x;
        new_y = pos_y;
    }

    public void setPos_z(float pos_z) {
        this.pos_z = pos_z;
    }

    public void setStatic_anim(int static_anim) {
        this.animation = static_anim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public String toString() {
        return ("" + pos_y);
    }

    public float getTimeout(){return timeout;}

    public void setTimeout(float x){timeout = x;}

    public void move_player(){
        for(int i = 0; i <2; i++) {
            if (Gdx.input.isTouched(i)) {
                if (view.isTap2(3, 65, 34, 56, i) && pos_y < otc.gameScreenY - 8) {
                    otc.player.addPos_y(1 * otc.ResY * 0.2f, speed); // UP
                    otc.audioModule.playGroundStep();
                    step_sound = false;
                } else if (view.isTap2(3, 88, 34, 79, i) && pos_y > 0) {
                    otc.player.addPos_y(-1 * otc.ResY * 0.2f, speed); // DOWN
                    otc.audioModule.playGroundStep();
                    step_sound = false;
                }

                if (view.isTap2(26, 87, 34, 56, i) && pos_x < otc.gameScreenX - 5) {
                    otc.player.addPos_x(1 * otc.ResX * 0.2f, speed); // RIGHT
                    if (step_sound)
                        otc.audioModule.playGroundStep();
                } else if (view.isTap2(3, 87, 19, 56, i) && pos_x > 0) {
                    otc.player.addPos_x(-1 * otc.ResX * 0.2f, speed); // LEFT
                    if (step_sound)
                        otc.audioModule.playGroundStep();
                }
            }
        }
    }

    @Override
    public int compareTo(Player player){return (int)(pos_y - player.pos_y);}
}
