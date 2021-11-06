package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.pokulan.occupythecolor.View;
import net.pokulan.occupythecolor.ViewMainMenu;

public class ViewSplash extends View {

    Texture splash;
    int splash_timer = 0;
    public static String NAME = "VIEW_SPLASH_SCREEN";

    public ViewSplash(SpriteBatch batch, OccupyTheColor otc){
        super(batch, otc);
        splash = new Texture("splash.png");
    }

    @Override
    void render(){
        String db = "XM: "+screen_x_multipler + " YM: "+screen_y_multipler + "\n"+"ResX: "+otc.ResX+" ResY: "+otc.ResY+ "\n"+"ShiX: "+screen_x_shift+" ShiY: "+screen_y_shift;
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        draw_texture(splash, 0, 0);
        draw_debug(db, Color.BLACK);
        batch.end();

        splash_timer ++;
        if(splash_timer > 95){
            splash_timer = 0;
            otc.changeView(ViewMainMenu.NAME);
            splash.dispose();
        }
    }

    @Override
    void touch() {

    }

    @Override
    void startUp(){

    }

    @Override
    void save(){

    }

    @Override
    void load(){

    }
}
