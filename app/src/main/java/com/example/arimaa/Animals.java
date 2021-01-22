package com.example.arimaa;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

public class Animals {
    public Bitmap image;
    public int team;
    public int id;
    public int priority;
    public Rect rect;
    public int top;
    public ArrayList<Rect> rectList = new ArrayList<>();
    public boolean currentlySelected;
    public boolean toBePushed;
    public boolean active;
    public boolean immobile;
    public int left;
    public String colour;
    public String name;
    public Animals(int id, Bitmap image, String colour, String name, int team, int left, int top, Rect rect, int priority){
     this.image = image;
     this.id = id;
     this.team = team;
     this.colour = colour;
     this.top = top;
     this.left = left;
     this.name = name;
     this.rect = rect;
     this.priority = priority;
    }

}
