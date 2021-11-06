package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import net.pokulan.occupythecolor.OccupyTheColor;
import net.pokulan.occupythecolor.Button;
import net.pokulan.occupythecolor.View;
import net.pokulan.occupythecolor.ViewMainMenu;

import java.util.Arrays;

public class ViewLobby extends View {

    public static String NAME = "VIEW_LOBBY";
    Texture background;
    Texture gui;
    Texture blue_bar;
    Texture red_bar;
    Texture candle_field;
    Texture boost_enable_but;
    Texture occupy_enable_but;
    int timer = 0;
    Button butt_exit;
    float scanner = 0;

    int boost_timer = 500;
    boolean boost_enable = false;
    int occupy_timer = 0;
    int player_on_field = 0;
    int timer_last = 0;

    SpriteBatch batch_light;
    FrameBuffer buffer_light;
    TextureRegion light_shader;

    public ViewLobby(SpriteBatch batch, OccupyTheColor otc) {
        super(batch, otc);
        background = new Texture("maps/background.png");
        gui = new Texture("gui.png");
        blue_bar = new Texture("blue_bar.png");
        red_bar = new Texture("red_bar.png");
        candle_field = new Texture("candle_field.png");
        boost_enable_but = new Texture("gui/boost_enable.png");
        occupy_enable_but = new Texture("gui/occupy_enable.png");

        butt_exit = new Button("EXIT", 144, 82,BUTTON_SMALL, font_medium, Color.WHITE, this);
        butt_exit.setButtonColor(Color.RED);
        buffer_light = new FrameBuffer(Pixmap.Format.RGBA8888, (int)otc.scr('x'), (int)otc.scr('y'), true);
        batch_light = new SpriteBatch();
        batch_light.enableBlending();
        light_shader = new TextureRegion(buffer_light.getColorBufferTexture());
    }

    @Override
    void render(){
        scanner = 0;
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player_on_field = 0;

        for(int i = 0; i < otc.fields.length; i++){
            otc.fields[i].draw();
            if(player_on_field == 0 && otc.fields[i].player_on(otc.player.getPos_x(), otc.player.getPos_y())) player_on_field = i + 1;
        }

        batch.begin();


        otc.view_current.draw_texture(background, 0, 0);
        otc.player.draw(timer, false, player_on_field);

        batch.end();
        buffer_light.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch_light.begin();
        otc.view_current.draw_texture(otc.dark, 0, 0, batch_light);

        batch_light.setBlendFunction(GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_COLOR);
        draw_lights();
        batch_light.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch_light.end();
        buffer_light.end();
        light_shader.setRegion(buffer_light.getColorBufferTexture());
        light_shader.flip(false, true);
        batch.begin();

        batch.draw(light_shader, 0, 0);
        draw_gui();


        butt_exit.draw();
        if(!otc.PC) draw_texture(otc.d_pad, 0, 0);

        timer ++;
        if(timer >= Integer.MAX_VALUE) timer = 0;
        otc.time_current --;
        if(boost_enable) boost_timer-=2;
        else if(boost_timer < 500) boost_timer ++;
        if(boost_timer <= 0){
            boost_timer = 0;
            boost_enable = false;
        }
        if(otc.time_current <= 0){
            end_game();
        }
        timer_last ++;
        batch.end();

    }

    @Override
    void touch() {
        otc.player.move_player(player_on_field);

        if(butt_exit.isClick()){
            otc.tapX = 0;
            otc.changeView(ViewMainMenu.NAME);
        }
        if(otc.view_current.isTap2_multi(145, 54, 158, 42) && boost_timer >= 500){
            otc.audioModule.playMagic();
            boost_enable = true;
        }
        if(otc.view_current.isTap2_multi(143, 73, 158, 56) && player_on_field > 0){
            occupy_timer ++;
            if(occupy_timer >= 100){
                occupy_timer = 0;
                if(otc.fields[player_on_field - 1].get_name().equals(otc.fields[otc.rand_color].get_name())){ // OK
                    otc.time_current += 350;
                    if(otc.time_current > otc.time_max) otc.time_current = otc.time_max;
                    otc.audioModule.playCorrect();
                    otc.rand_color = otc.x.nextInt(otc.fields.length);
                    otc.proba += 1;
                    if(timer_last > 400) otc.punkty += 20;
                    else otc.punkty += 20 + (200 - (timer_last/2));
                }else{ // ZLE
                    otc.time_current -= 200;
                    otc.audioModule.playWrong();
                }
                timer_last = 0;
            }
        }else occupy_timer = 0;
    }

    void draw_lights(){
        otc.view_current.draw_sprite(otc.candle, otc.player.getPos_x(), otc.player.getPos_y(), batch_light);
        if(boost_enable){
            for(Field field : otc.fields) {
                otc.view_current.draw_texture(candle_field, field.filed_x - 1, field.field_y - 2, batch_light);
            }
        }
    }

    void end_game(){
        if(otc.easy_mode) otc.coins += otc.punkty / 10;
        else otc.coins += otc.punkty / 6;
        save();
        otc.put_highscore(otc.player.getName(), otc.punkty);
        otc.changeView(ViewMainMenu.NAME);
    }

    void draw_gui(){
        //otc.view_current.draw_text(otc.view_current.font_tiny, "x: " + otc.player.getPos_x() + "\ny:" + otc.player.getPos_y(), 2, 87, Color.WHITE);
        //if(player_on_field > 0) otc.view_current.draw_text(otc.view_current.font_tiny, "step: " + otc.fields[player_on_field - 1].get_name() + "\nfield:" + otc.fields[otc.rand_color].get_name(), 2, 87, Color.WHITE);
        otc.view_current.draw_texture(gui, 0, 0);
        if(boost_timer >= 500) otc.view_current.draw_texture(boost_enable_but, 0, 0);
        if(player_on_field > 0) otc.view_current.draw_texture(occupy_enable_but, 0, 0);
        otc.view_current.draw_texture(blue_bar,41, 88, (int)((78.0/otc.time_max) * otc.time_current), 2);
        otc.view_current.draw_texture(red_bar,146, 45, (int)((10.0/500) * boost_timer), 2);
        otc.view_current.draw_texture(red_bar,146, 28, (int)((10.0/100) * occupy_timer), 2);
        otc.view_current.draw_text(otc.view_current.font_tiny, "próba: " + otc.proba, 42, 87, Color.BLACK);
        otc.view_current.draw_text(otc.view_current.font_tiny, "punkty:", 89, 87, Color.BLACK);
        otc.view_current.draw_text(otc.view_current.font_big, "" + otc.punkty, 102, 87, Color.BLACK);
        if(otc.easy_mode) otc.view_current.draw_text(otc.view_current.font_medium, "+" + (otc.punkty / 10) + " monet", 3, 87, Color.GOLD);
        else otc.view_current.draw_text(otc.view_current.font_medium, "+" + (otc.punkty / 6) + " monet", 3, 87, Color.GOLD);

        if(!otc.easy_mode) otc.view_current.draw_text(otc.view_current.font_medium, otc.fields[otc.rand_color].get_name(), 42, 84.5f, Color.FIREBRICK);
        else otc.view_current.draw_text(otc.view_current.font_medium, otc.fields[otc.rand_color].get_name(), 42, 84.5f, otc.fields[otc.rand_color].get_color());
    }

    @Override
    void startUp(){
         boost_timer = 500;
         boost_enable = false;
         occupy_timer = 0;
         player_on_field = 0;
         timer_last = 0;
         otc.reload_colors();
         otc.time_current = otc.time_max;
         otc.proba = 0;
         otc.punkty = 0;
         otc.timer_last = 0;
         otc.rand_color = otc.x.nextInt(otc.fields.length);
    }

    @Override
    void save(){
        otc.saveInt("coins", otc.coins);
    }

    @Override
    void load(){

    }
}
