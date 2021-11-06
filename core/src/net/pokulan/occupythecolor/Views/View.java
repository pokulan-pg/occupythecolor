package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.pokulan.occupythecolor.OccupyTheColor;

public class View {
    public OccupyTheColor otc;
    SpriteBatch batch;
    static BitmapFont font_micro;
    static BitmapFont font_tiny;
    static BitmapFont font_medium;
    static BitmapFont font_big;
    static BitmapFont font_hand_tiny;
    static BitmapFont font_hand_medium;
    float screen_ratio;
    float game_ratio;
    int screen_x_shift, screen_y_shift;
    int screen_x_multipler, screen_y_multipler;
    public Sprite button_small;
    public Sprite button_big;
    public Sprite button_huge;
    public Sprite button_square;
    public Sprite button_arrow;
    public Sprite button_arrow_l;
    public Sprite text_box_small;
    public Sprite text_box_big;
    public Sprite text_box_micro;
    int BUTTON_SMALL = 0;
    int BUTTON_BIG = 1;
    int BUTTON_SQUARE = 3;
    int BUTTON_HUGE = 2;
    int TEXT_BOX_SMALL = 0;
    int TEXT_BOX_BIG = 1;
    int TEXT_BOX_MICRO = 2;
    Texture red;
    Texture green;


    public View(SpriteBatch batch, OccupyTheColor otc){
        this.batch = batch;
        this.otc = otc;

        red = new Texture("gui/red.png");
        green = new Texture("gui/green.png");
        otc.shadow_player = new Sprite(new Texture("characters/shadow.png"));

        game_ratio = (float)otc.gameScreenX/otc.gameScreenY;
        screen_ratio = otc.scr('x')/otc.scr('y');
        if(screen_ratio > game_ratio){
            screen_x_multipler = (int)otc.ResY;
            screen_y_multipler = (int)otc.ResY;
            screen_x_shift = (int)((otc.scr('x')-(otc.gameScreenX * screen_x_multipler))/2.0f);
            screen_y_shift = 0;
        }
        else if(screen_ratio == game_ratio){
            screen_x_shift = 0;
            screen_y_shift = 0;
            screen_x_multipler = (int)otc.ResX;
            screen_y_multipler = (int)otc.ResY;
        }
        else if(screen_ratio < game_ratio){
            screen_x_multipler = (int)otc.ResX;
            screen_y_multipler = (int)otc.ResX;
            screen_x_shift = 0;
            screen_y_shift = (int)((otc.scr('y')-(otc.gameScreenY * screen_y_multipler))/2.0f);
        }
        otc.ResX = screen_x_multipler;
        otc.ResY = screen_y_multipler;
        otc.screen_x_shift = screen_x_shift;
        otc.screen_y_shift = screen_y_shift;
        otc.shadow_player.setOrigin(0,0);
        otc.shadow_player.setScale(screen_x_multipler,screen_y_multipler);

        font_micro = new BitmapFont(Gdx.files.internal("czcionka.fnt"));
        font_micro.setColor(Color.BLACK);
        font_micro.getData().setScale(0.1f * screen_x_multipler,  0.1f * screen_y_multipler);
        font_tiny = new BitmapFont(Gdx.files.internal("czcionka.fnt"));
        font_tiny.setColor(Color.BLACK);
        font_tiny.getData().setScale(0.125f * screen_x_multipler,  0.125f * screen_y_multipler);
        font_medium = new BitmapFont(Gdx.files.internal("czcionka.fnt"));
        font_medium.setColor(Color.BLACK);
        font_medium.getData().setScale(0.18f * screen_x_multipler,  0.18f * screen_y_multipler);
        font_big = new BitmapFont(Gdx.files.internal("czcionka.fnt"));
        font_big.setColor(Color.BLACK);
        font_big.getData().setScale(0.3f * screen_x_multipler,  0.3f * screen_y_multipler);
        font_hand_tiny = new BitmapFont(Gdx.files.internal("gazeta.fnt"));
        font_hand_tiny.setColor(Color.DARK_GRAY);
        font_hand_tiny.getData().setScale(0.125f * screen_x_multipler,  0.125f * screen_y_multipler);
        font_hand_medium = new BitmapFont(Gdx.files.internal("gazeta.fnt"));
        font_hand_medium.setColor(Color.DARK_GRAY);
        font_hand_medium.getData().setScale(0.18f * screen_x_multipler,  0.18f * screen_y_multipler);

        button_arrow = new Sprite(new Texture("gui/button_arrow.png"));
        button_arrow.setScale(screen_x_multipler);
        button_arrow.setOrigin(0, 0);
        button_arrow_l = new Sprite(new Texture("gui/button_arrow_l.png"));
        button_arrow_l.setScale(screen_x_multipler);
        button_arrow_l.setOrigin(0, 0);
        button_small = new Sprite(new Texture("gui/button_small.png"));
        button_small.setScale(screen_x_multipler);
        button_small.setOrigin(0, 0);
        button_big = new Sprite(new Texture("gui/button_big.png"));
        button_big.setScale(screen_x_multipler);
        button_big.setOrigin(0, 0);
        button_huge = new Sprite(new Texture("gui/button_huge.png"));
        button_huge.setScale(screen_x_multipler);
        button_huge.setOrigin(0, 0);
        button_square = new Sprite(new Texture("gui/button_square.png"));
        button_square.setScale(screen_x_multipler);
        button_square.setOrigin(0, 0);
        text_box_small = new Sprite(new Texture("gui/text_box_small.png"));
        text_box_small.setScale(screen_x_multipler);
        text_box_small.setOrigin(0, 0);
        text_box_big = new Sprite(new Texture("gui/text_box_big.png"));
        text_box_big.setScale(screen_x_multipler);
        text_box_big.setOrigin(0, 0);
        text_box_micro = new Sprite(new Texture("gui/text_box_micro.png"));
        text_box_micro.setScale(screen_x_multipler);
        text_box_micro.setOrigin(0, 0);
    }

    public void draw_animation(Animation<TextureRegion> anim, float x, float y, float w, float h, int timer){
        batch.draw(anim.getKeyFrame(timer), (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift,
                w * screen_x_multipler, h * screen_y_multipler);
    }
    public void draw_animation(Animation<TextureRegion> anim, float x, float y, int timer){
        batch.draw(anim.getKeyFrame(timer), (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift,
                anim.getKeyFrame(timer).getRegionWidth() * screen_x_multipler, anim.getKeyFrame(timer).getRegionHeight() * screen_y_multipler);
    }

    public void draw_texture(Texture txt, float x, float y, float w, float h){
        batch.draw(txt, (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift,
                w * screen_x_multipler, h * screen_y_multipler);
    }
    public void draw_texture(Texture txt, float x, float y){
        batch.draw(txt, (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift,
                txt.getWidth() * screen_x_multipler, txt.getHeight() * screen_y_multipler);
    }

    public void draw_texture(Texture txt, float x, float y, SpriteBatch b){
        b.draw(txt, (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift,
                txt.getWidth() * screen_x_multipler, txt.getHeight() * screen_y_multipler);
    }

    public void draw_text(BitmapFont font, String txt, float x, float y){
        font.draw(batch, txt, (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift);
    }

    public void draw_text(BitmapFont font, String txt, float x, float y, float w, int lh, boolean nl){
        font.draw(batch, txt, (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift, w* screen_x_multipler, lh, nl);
    }

    public void draw_text(BitmapFont font, String txt, float x, float y, Color cl){
        Color cl2 = font.getColor();
        font.setColor(cl);
        font.draw(batch, txt, (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift);
        font.setColor(cl2);
    }

    public void draw_text(BitmapFont font, String txt, float x, float y, float w, int lh, boolean nl, Color cl){
        Color cl2 = font.getColor();
        font.setColor(cl);
        font.draw(batch, txt, (x * screen_x_multipler) + screen_x_shift, (y * screen_y_multipler) + screen_y_shift, w* screen_x_multipler, lh, nl);
        font.setColor(cl2);
    }

    public void draw_debug(String txt){
        if(otc.DEBUG)font_tiny.draw(batch, txt, (1 * screen_x_multipler) + screen_x_shift, (88 * screen_y_multipler) + screen_y_shift);
    }

    public void draw_debug(String txt, Color cl){
        if(otc.DEBUG) {
            Color cl2 = font_tiny.getColor();
            font_tiny.setColor(cl);
            font_tiny.draw(batch, txt, (1 * screen_x_multipler) + screen_x_shift, ((otc.gameScreenY-1) * screen_y_multipler) + screen_y_shift);
            font_tiny.setColor(cl2);
        }
    }

    public boolean isTap(int x1, int y1, int x2, int y2){
        if((otc.tapX - screen_x_shift) >= x1*screen_x_multipler && (otc.tapX - screen_x_shift) <= x2*screen_x_multipler && (otc.tapY - screen_y_shift) >= (y2*screen_y_multipler) && ((otc.tapY - screen_y_shift) <= y1*screen_y_multipler) ) {
            return true;
        }

        return false;
    }
    public boolean isTap_multi(int x1, int y1, int x2, int y2){
        if((otc.tapX - screen_x_shift) >= x1*screen_x_multipler && (otc.tapX - screen_x_shift) <= x2*screen_x_multipler && (otc.tapY - screen_y_shift) >= (y2*screen_y_multipler) && ((otc.tapY - screen_y_shift) <= y1*screen_y_multipler) ) {
            return true;
        }
        return false;
    }
    public boolean isTap2(int x1, int y1, int x2, int y2){
        if((Gdx.input.getX()- screen_x_shift) >= x1*screen_x_multipler && (Gdx.input.getX()- screen_x_shift) <= x2*screen_x_multipler && (Gdx.input.getY() - screen_y_shift) >= (y2*screen_y_multipler) && (Gdx.input.getY() - screen_y_shift) <= y1*screen_y_multipler && Gdx.input.isTouched()) return true;
        return false;
    }
    public boolean isTap2(int x1, int y1, int x2, int y2, int i){
        if((Gdx.input.getX(i)- screen_x_shift) >= x1*screen_x_multipler && (Gdx.input.getX(i)- screen_x_shift) <= x2*screen_x_multipler && (Gdx.input.getY(i) - screen_y_shift) >= (y2*screen_y_multipler) && (Gdx.input.getY(i) - screen_y_shift) <= y1*screen_y_multipler && Gdx.input.isTouched(i)) return true;
        return false;
    }
    public boolean isTap2_multi(int x1, int y1, int x2, int y2){
        for(int i = 0; i <2; i++) {
            if (Gdx.input.isTouched(i)) {
                if ((Gdx.input.getX(i) - screen_x_shift) >= x1 * screen_x_multipler && (Gdx.input.getX(i) - screen_x_shift) <= x2 * screen_x_multipler && (Gdx.input.getY(i) - screen_y_shift) >= (y2 * screen_y_multipler) && (Gdx.input.getY(i) - screen_y_shift) <= y1 * screen_y_multipler && Gdx.input.isTouched(i))
                    return true;
            }
        }
        return false;
    }

    public void draw_sprite(Sprite spr,float x, float y){
        spr.setPosition((x) * screen_x_multipler + screen_x_shift,y * screen_y_multipler + screen_y_shift);
        spr.draw(batch);
    }

    public void draw_sprite(Sprite spr,float x, float y, SpriteBatch b){
        spr.setPosition((x) * screen_x_multipler + screen_x_shift,y * screen_y_multipler + screen_y_shift);
        spr.draw(b);
    }

    public void draw_sprite(Sprite spr){
        spr.draw(batch);
    }

    void render(){

    }

    void touch(){

    }

    void startUp(){

    }

    void load(){

    }

    void save(){

    }
}
