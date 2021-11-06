package net.pokulan.occupythecolor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Field {
    OccupyTheColor otc;
    private ShapeRenderer shapeRenderer;
    FieldColor color;
    int filed_x;
    int field_y;
    int width;
    int height;

    Field(int x, int y, OccupyTheColor otc){
        width = 19;
        height = 6;
        filed_x = x;
        field_y = y;
        this.otc = otc;
        color = new FieldColor();
        shapeRenderer = new ShapeRenderer();
    }

    public void set_color(FieldColor c){color = c;}
    public String get_name(){return color.get_name();}
    public Color get_color(){return color.get_color();}
    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color.get_color());
        shapeRenderer.rect((filed_x * otc.view_current.screen_x_multipler) + otc.view_current.screen_x_shift,
                (field_y * otc.view_current.screen_y_multipler) + otc.view_current.screen_y_shift,
                width * otc.view_current.screen_x_multipler, height* otc.view_current.screen_y_multipler);
        shapeRenderer.end();
    }
    public boolean player_on(float x, float y){
        if(x + 4 >= filed_x && x + 4 < filed_x + width && y > field_y && y < field_y + height) return true;
        else return false;
    }
}
