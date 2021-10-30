package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.pokulan.occupythecolor.Button;
import net.pokulan.occupythecolor.TextBox;
import net.pokulan.occupythecolor.ViewLobby;

public class ViewMainMenu extends net.pokulan.occupythecolor.View {
    public static String NAME = "VIEW_MAIN_MENU";
    Sprite logo_2;

    int timer = 0;
    int submenu = 0;
    int join_select = 0;
    Button but_play;
    int lobby_shift = 0;
    int host_player_max = 5;

    public ViewMainMenu(SpriteBatch batch, OccupyTheColor otc){
        super(batch, otc);

        logo_2 = new Sprite(new Texture("main_menu/2.png"));
        logo_2.setScale(screen_x_multipler,screen_y_multipler);
        logo_2.setOrigin(36,16);

        but_play = new Button("START", 24, 39,BUTTON_BIG, font_medium, this);

    }

    @Override
    void render(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(submenu == 0){ // MAIN
            but_play.draw();
        }else if(submenu == 1){ // STATS

        }

        draw_sprite(logo_2, 83, 80);
        batch.end();
    }

    @Override
    void touch() {
        if(submenu == 0) {
            if(but_play.isClick()) otc.changeView(ViewLobby.NAME);
        }
        else if(submenu == 1) {

        }
    }

    @Override
    void startUp(){
        otc.player.setPos(38, 17);
    }

    @Override
    void save(){
        otc.saveInt("audioEnable", otc.audioEnable);
        otc.saveInt("player_char", otc.player.getStatic_anim());
        otc.saveString("player_name", otc.player.getName());
        otc.saveInt("player_hairs", otc.player.getHairs());
        otc.saveInt("player_eyes", otc.player.getEyes());
    }

    @Override
    void load(){

    }
}
