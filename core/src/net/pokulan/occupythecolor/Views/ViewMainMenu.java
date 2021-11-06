package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;

import net.pokulan.occupythecolor.Button;
import net.pokulan.occupythecolor.TextBox;
import net.pokulan.occupythecolor.ViewLobby;

public class ViewMainMenu extends net.pokulan.occupythecolor.View {
    public static String NAME = "VIEW_MAIN_MENU";

    Texture background;
    Texture logo;
    Texture mute;
    Texture unmute;
    Sprite logo2;
    int submenu = 0;
    Button but_play;
    Button game_mode;
    Button but_scores;
    Button butt_character_left;
    Button butt_character_right;
    Button back;
    Button kup;
    Button butt_sound;
    TextBox player_nick;
    int timer = 0;

    SpriteBatch batch_light;
    FrameBuffer buffer_light;
    TextureRegion light_shader;

    public ViewMainMenu(SpriteBatch batch, OccupyTheColor otc){
        super(batch, otc);
        logo = new Texture("gui/logo.png");
        mute = new Texture("gui/mute.png");
        unmute = new Texture("gui/sound.png");
        logo2 = new Sprite(new Texture("gui/logo2.png"));
        buffer_light = new FrameBuffer(Pixmap.Format.RGBA8888, (int)otc.scr('x'), (int)otc.scr('y'), true);
        batch_light = new SpriteBatch();
        batch_light.enableBlending();
        light_shader = new TextureRegion(buffer_light.getColorBufferTexture());
        background = new Texture("maps/background.png");
        back = new Button("wróc", 3, 3,BUTTON_SMALL, font_medium,Color.WHITE, this);
        back.setButtonColor(Color.RED);
        kup = new Button("kup", 96, 15,BUTTON_SMALL, font_medium,Color.BLACK, this);
        kup.setButtonColor(Color.YELLOW);
        but_play = new Button("START GRY", 6, 39,BUTTON_HUGE, font_medium, this);
        but_play.setButtonColor(Color.YELLOW);
        game_mode = new Button("łatwy", 6, 53,BUTTON_BIG, font_medium, Color.WHITE, this);
        game_mode.setButtonColor(Color.LIME);
        but_scores = new Button("Tabela wyników", 6, 25,BUTTON_HUGE, font_medium,this);
        but_scores.setButtonColor(Color.YELLOW);
        player_nick = new TextBox(otc.P_name, "nazwa gracza", "gracz", 79, 54, TEXT_BOX_SMALL, otc.view_current.font_medium, 16, this);
        butt_character_left = new Button(84, 37,3, this);
        butt_character_right = new Button(115, 37,1, this);
        if(!otc.easy_mode){
            game_mode.setButtonColor(Color.RED);
            game_mode.setButtonText("trudny");
        }

        logo2.setOrigin(5, 1);
        logo2.setScale(otc.ResX, otc.ResY);
        butt_sound = new Button("", 6, 6,BUTTON_SQUARE, font_medium, this);
        butt_sound.setImage(new Texture("gui/mute.png"));

    }

    @Override
    void render(){
        otc.player.setPos(99, 34);
        if(!player_nick.getText().equals(otc.player.getName())){
            otc.player.setName(player_nick.getText());
            otc.saveString("player_name", player_nick.getText());
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for(Field field : otc.fields){
            field.draw();
        }

        batch.begin();
        otc.view_current.draw_texture(background, 0, 0);
        otc.player.draw(timer, false, 0);
        batch.end();

        buffer_light.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch_light.begin();
        otc.view_current.draw_texture(otc.dark, 0, 0, batch_light);

        batch_light.setBlendFunction(GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_COLOR);

        otc.view_current.draw_sprite(otc.candle, otc.player.getPos_x(), otc.player.getPos_y(), batch_light);

        batch_light.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch_light.end();
        buffer_light.end();
        light_shader.setRegion(buffer_light.getColorBufferTexture());
        light_shader.flip(false, true);
        batch.begin();
        batch.draw(light_shader, 0, 0);

        otc.view_current.draw_texture(logo, 0, 0);
        otc.view_current.draw_sprite(logo2, 102, 73, batch);

        if(submenu == 0){ // MAIN
            but_play.draw();
            game_mode.draw();
            but_scores.draw();
            player_nick.draw();
            butt_character_left.draw();
            butt_character_right.draw();
            butt_sound.draw();
            otc.view_current.draw_text(otc.view_current.font_medium, otc.coins + " monet", 6, 70, Color.GOLD);
            if(otc.player_sets[otc.player_set][4] == 0){
                otc.view_current.draw_text(otc.view_current.font_medium, "Koszt: " + otc.player_sets[otc.player_set][5], 92, 26, Color.GOLD);
                kup.draw();
            }
            otc.view_current.draw_text(otc.view_current.font_medium, "Szybkość: " + otc.player_sets[otc.player_set][3], 92, 31, Color.CYAN);
        }else if(submenu == 1){ // STATS
            for(int i = 0; i < otc.tabela_wynikow.length; i++){
                otc.view_current.draw_text(otc.view_current.font_medium, otc.tabela_wynikow[i].getName(), 40, 70 - i * 6, Color.WHITE);
                otc.view_current.draw_text(otc.view_current.font_big, otc.tabela_wynikow[i].getScore(), 120, 70 - i * 6, Color.WHITE);
            }
            back.draw();
        }

        batch.end();

        logo2.setRotation(MathUtils.sin(MathUtils.PI2 / 100 * (timer % 100)) * 10);

        timer ++;
        if(timer >= Integer.MAX_VALUE) timer = 0;
    }

    @Override
    void touch() {
        if(submenu == 0) { // MAIN
            player_nick.isClick();
            if(but_play.isClick()){
                otc.tapX = 0;
                otc.changeView(ViewLobby.NAME);
            }
            if(game_mode.isClick()){
                otc.tapX = 0;
                otc.easy_mode = !otc.easy_mode;
                otc.saveBoolean("easy_mode", otc.easy_mode);
                if(otc.easy_mode){
                    game_mode.setButtonColor(Color.LIME);
                    game_mode.setButtonText("łatwy");
                }else{
                    game_mode.setButtonColor(Color.RED);
                    game_mode.setButtonText("trudny");
                }
            }else if(butt_character_right.isClick()){
                otc.tapX = 0;
                if(otc.player_set < otc.player_sets.length - 1) otc.player_set ++;
                otc.player.setStatic_anim(otc.player_sets[otc.player_set][0]);
                otc.player.setHairs(otc.player_sets[otc.player_set][1]);
                otc.player.setEyes(otc.player_sets[otc.player_set][2]);
                otc.player.setSpeed(otc.player_sets[otc.player_set][3]);
                if(otc.player_sets[otc.player_set][4] == 1){
                    save();
                    but_play.setClickable(true);
                }else{
                    but_play.setClickable(false);
                    if(otc.coins >= otc.player_sets[otc.player_set][5]) kup.setClickable(true);
                    else kup.setClickable(false);
                }

            }else if(butt_character_left.isClick()){
                otc.tapX = 0;
                if(otc.player_set > 0) otc.player_set --;
                otc.player.setStatic_anim(otc.player_sets[otc.player_set][0]);
                otc.player.setHairs(otc.player_sets[otc.player_set][1]);
                otc.player.setEyes(otc.player_sets[otc.player_set][2]);
                otc.player.setSpeed(otc.player_sets[otc.player_set][3]);
                if(otc.player_sets[otc.player_set][4] == 1){
                    save();
                    but_play.setClickable(true);
                }else{
                    but_play.setClickable(false);
                    if(otc.coins >= otc.player_sets[otc.player_set][5]) kup.setClickable(true);
                    else kup.setClickable(false);
                }
            }else if(butt_sound.isClick()){
                otc.tapX = 0;
                if(otc.audioEnable == 0) {
                    otc.audioEnable = 1;
                    butt_sound.setImage(unmute);
                }
                else {
                    otc.audioEnable = 0;
                    butt_sound.setImage(mute);
                }
                save();
            }
            if(but_scores.isClick()){
                otc.tapX = 0;
                submenu = 1;
            }else if(kup.isClick()){
                otc.tapX = 0;
                otc.coins -= otc.player_sets[otc.player_set][5];
                otc.player_sets[otc.player_set][4] = 1;
                otc.saveInt("secik" + otc.player_set, 1);
                but_play.setClickable(true);
                kup.setClickable(false);

            }
        }
        else if(submenu == 1) { // TABELA SCOROW
            if(back.isClick()){
                otc.tapX = 0;
                submenu = 0;
            }
        }
    }

    @Override
    void startUp(){
        otc.player.setPos(99, 34);
        if(otc.audioEnable == 0) butt_sound.setImage(mute);
        else butt_sound.setImage(unmute);
    }

    @Override
    void save(){
        otc.saveInt("audioEnable", otc.audioEnable);
        otc.saveInt("player_set", otc.player_set);
        otc.saveString("player_name", otc.player.getName());
    }

    @Override
    void load(){

    }
}
