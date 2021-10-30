package net.pokulan.occupythecolor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import net.pokulan.occupythecolor.View;

public class Button {
    public String text;
    int size;
    public float x, y;
    public float w, h;
    boolean clickable;
    BitmapFont font;
    Sprite sprite;
    View view;
    Texture image;
    Color color;
    Color font_color;

    public Button(String text, float x, float y, int size, BitmapFont font, View view){
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
        this.view = view;
        this.font_color = Color.BLACK;
        clickable = true;
        this.size = size;
        if(size == 0)sprite = new Sprite(view.button_small);
        else if(size == 1) sprite = new Sprite(view.button_big);
        else if(size == 2) sprite = new Sprite(view.button_huge);
        else if(size == 3) sprite = new Sprite(view.button_square);
        color = new Color(1, 1, 1, 1);
        h = sprite.getTexture().getHeight();
        w = sprite.getTexture().getWidth();
    }
    public Button(String text, float x, float y, int size, BitmapFont font, Color cl, View view){
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
        this.view = view;
        this.font_color = cl;
        clickable = true;
        this.size = size;
        if(size == 0)sprite = new Sprite(view.button_small);
        else if(size == 1) sprite = new Sprite(view.button_big);
        else if(size == 2) sprite = new Sprite(view.button_huge);
        else if(size == 3) sprite = new Sprite(view.button_square);
        color = new Color(1, 1, 1, 1);
        h = sprite.getTexture().getHeight();
        w = sprite.getTexture().getWidth();
    }
    public Button(float x, float y, int dir, View view){
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
        this.font_color = Color.BLACK;
        this.view = view;
        this.size = 4;
        clickable = true;
        if(dir == 0) sprite = new Sprite(view.button_arrow);
        else if(dir == 1) sprite = new Sprite(view.button_arrow_l);
        else if(dir == 2) {
            sprite = new Sprite(view.button_arrow);
            sprite.flip(false, true);
        }
        else if(dir == 3){
            sprite = new Sprite(view.button_arrow_l);
            sprite.flip(true, false);
        }
        color = new Color(1, 1, 1, 1);
        h = sprite.getTexture().getHeight();
        w = sprite.getTexture().getWidth();
    }

    public void setImage(Texture tx){
        image = tx;
    }

    public void setButtonColor(Color cl){
        color = cl;
        sprite.setColor(cl);
    }

    public void draw(){
        if(view.isTap2((int)x, (int)(90 - y), (int)(x + w), (int)(90 - y - h))){
            sprite.setColor(Color.DARK_GRAY);
        }else if(clickable) sprite.setColor(color);
        view.draw_sprite(sprite, x, y);
        if(size == 3) view.draw_texture(image, (float)(x + 2), (float)(y + 2));
        if(size != 4) {
            font.setColor(font_color);
            view.draw_text(font, text, x, y + h / 2 + 2f, w, 1, false);
        }
    }

    public boolean isClick(){
        if(!clickable) return false;
        if(view.isTap((int)x, (int)(90 - y), (int)(x + w), (int)(90 - y - h))){
            view.otc.audioModule.playButton();
            return true;
        }
        else return false;
    }

    public boolean isClickable(){return clickable;}
    public void setClickable(boolean x){
        clickable = x;
        if(!x){
            sprite.setColor(Color.DARK_GRAY);
        }else sprite.setColor(color);
    }
    public float getX(){return x;}
    public float getY(){return y;}
    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }
}
