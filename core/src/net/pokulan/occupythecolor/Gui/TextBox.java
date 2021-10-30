package net.pokulan.occupythecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import net.pokulan.occupythecolor.View;

public class TextBox {
    public String title;
    public String text;
    public String hint;
    boolean editable;
    int max_letters;
    int size;
    public float x, y;
    public float w, h;
    BitmapFont font;
    Sprite sprite;
    View view;
    Color font_color;
    int timer;

    public Input.TextInputListener textListener = new Input.TextInputListener()
    {
        @Override
        public void input(String input)
        {
            text = input.replaceAll("[^a-zA-Z0-9_\\-\\.\\:]", "");
            if(text.length() > max_letters) text = text.substring(0, max_letters).replaceAll("[^a-zA-Z0-9_\\-\\.\\:]", "");
        }

        @Override
        public void canceled()
        {
            System.out.println("Aborted");
        }
    };

    public TextBox(String text, String title, String hint, float x, float y, int size, BitmapFont font, int max_letters, View view){
        this.text = text;
        this.x = x;
        this.y = y;
        this.hint = hint;
        this.title = title;
        this.max_letters = max_letters;
        this.font = font;
        this.view = view;
        this.font_color = Color.BLACK;
        this.size = size;
        if(size == 0)sprite = new Sprite(view.text_box_small);
        else if(size == 1) sprite = new Sprite(view.text_box_big);
        else if(size == 2) sprite = new Sprite(view.text_box_micro);
        h = sprite.getTexture().getHeight();
        w = sprite.getTexture().getWidth();
        timer = 0;
        editable = true;
    }

    public TextBox(String text, String title, String hint, float x, float y, int size, BitmapFont font, Color cl, int max_letters, View view){
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
        this.max_letters = max_letters;
        this.view = view;
        this.hint = hint;
        this.font_color = cl;
        this.title = title;
        this.size = size;
        if(size == 0)sprite = new Sprite(view.text_box_small);
        else if(size == 1) sprite = new Sprite(view.text_box_big);
        else if(size == 2) sprite = new Sprite(view.text_box_micro);
        h = sprite.getTexture().getHeight();
        w = sprite.getTexture().getWidth();
        timer = 0;
        editable = true;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String isClick(){
        if(editable && view.isTap((int)x, (int)(90 - y), (int)(x + w), (int)(90 - y - h))){
            view.otc.tapX = 0;
            view.otc.audioModule.playButton();
            Gdx.input.getTextInput(textListener, title, text, hint);
            return text;
        }
        else return "";
    }

    public void draw(){
        view.draw_sprite(sprite, x, y);
        font.setColor(font_color);
        if(timer < 22 || editable == false)view.draw_text(font, text, x, y + h/2 + 2.5f, w, 1, false);
        else view.draw_text(font, " " + text + "|", x, y + h/2 + 2.5f, w, 1, false);
        timer ++;
        if(timer > 45) timer = 0;
    }

    public String getText(){
        return text;
    }
    public void setText(String s){
        text = s;
    }
    public float getX(){return x;}
    public float getY(){return y;}
    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }
}
