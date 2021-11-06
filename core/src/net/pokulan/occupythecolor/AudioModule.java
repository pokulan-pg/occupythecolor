package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import net.pokulan.occupythecolor.OccupyTheColor;


public class AudioModule {
    OccupyTheColor otc;
    Music music_current;

    Sound button;
    Sound magic;
    Sound correct;
    Sound wrong;
    Sound cash;

    int steps;
    int timer;
    Sound[] step_grass = new Sound[3];
    Sound[] step_mud = new Sound[3];
    Sound[] step_sand = new Sound[3];
    Sound[] step_water = new Sound[3];

    AudioModule(OccupyTheColor otc){
        this.otc = otc;
        steps = 0;
        timer = 0;
        button = Gdx.audio.newSound(Gdx.files.internal("gui/button.mp3"));
        magic = Gdx.audio.newSound(Gdx.files.internal("sounds/magic.ogg"));
        correct = Gdx.audio.newSound(Gdx.files.internal("sounds/correct.mp3"));
        wrong = Gdx.audio.newSound(Gdx.files.internal("sounds/wrong.mp3"));
        cash = Gdx.audio.newSound(Gdx.files.internal("sounds/sound_cash.ogg"));
        for(int i = 0; i < 3; i++){
            step_grass[i] = Gdx.audio.newSound(Gdx.files.internal("sounds/step_grass_" + i + ".ogg"));
            step_mud[i] = Gdx.audio.newSound(Gdx.files.internal("sounds/step_mud_" + i + ".ogg"));
            step_sand[i] = Gdx.audio.newSound(Gdx.files.internal("sounds/step_sand_" + i + ".ogg"));
            step_water[i] = Gdx.audio.newSound(Gdx.files.internal("sounds/step_water_" + i + ".ogg"));
        }
    }

    public void playButton(){
        button.stop();
        if(otc.audioEnable > 0) button.play();
    }
    public void playMagic(){
        button.stop();
        if(otc.audioEnable > 0) magic.play();
    }
    public void playCorrect(){
        button.stop();
        if(otc.audioEnable > 0) correct.play();
    }
    public void playWrong(){
        button.stop();
        if(otc.audioEnable > 0) wrong.play();
    }
    public void playCash(){
        button.stop();
        if(otc.audioEnable > 0) cash.play();
    }

    public void playGroundStep(){
        if(otc.audioEnable > 0 && timer > 20) {
            step_grass[steps].play();
            steps++;
            if(steps > 2) steps = 0;
            timer = 0;
        }
        timer += 1;
    }
    public void playFieldStep(){
        if(otc.audioEnable > 0 && timer > 20) {
            step_water[steps].play();
            steps++;
            if(steps > 2) steps = 0;
            timer = 0;
        }
        timer += 1;
    }
}
