package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.pokulan.occupythecolor.OccupyTheColor;
import net.pokulan.occupythecolor.Button;
import net.pokulan.occupythecolor.View;
import net.pokulan.occupythecolor.ViewMainMenu;

import java.util.Arrays;

public class ViewLobby extends View {

    public static String NAME = "VIEW_LOBBY";
    Texture background;
    int timer = 0;
    Button butt_exit;
    float scanner = 0;

    public ViewLobby(SpriteBatch batch, OccupyTheColor otc) {
        super(batch, otc);
        background = new Texture("maps/background.png");

        butt_exit = new Button("EXIT", 144, 82,BUTTON_SMALL, font_medium, Color.WHITE, this);
        butt_exit.setButtonColor(Color.RED);
    }

    @Override
    void render(){
        scanner = 0;
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        otc.view_current.draw_texture(background, 0, 0);
        otc.player.draw(timer, false, 0);

        butt_exit.draw();
        if(!otc.PC) draw_texture(otc.d_pad, 0, 0);

        timer ++;
        if(timer >= Integer.MAX_VALUE) timer = 0;
        batch.end();

    }

    @Override
    void touch() {
        otc.player.move_player();

        if(butt_exit.isClick()){
            otc.tapX = 0;
            otc.changeView(ViewMainMenu.NAME);

        }
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
