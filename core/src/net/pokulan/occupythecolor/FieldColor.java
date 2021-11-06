package net.pokulan.occupythecolor;

import com.badlogic.gdx.graphics.Color;

public class FieldColor {
    Color color;
    String name;

    FieldColor(){
    }

    FieldColor(Color c, String name){
        color = c;
        this.name = name;
    }

    public Color get_color(){return color;}
    public String get_name(){return name;};
}
