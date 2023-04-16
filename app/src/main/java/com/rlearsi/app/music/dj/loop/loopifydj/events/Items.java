package com.rlearsi.app.music.dj.loop.loopifydj.events;

import java.io.Serializable;

public class Items implements Serializable {

    private final int id;
    private final String name, file_name, color;
    private final boolean isLoop, isFav;

    public Items(int id, String name, String file_name, String color, boolean isLoop, boolean isFav){

        this.id             = id;
        this.name           = name;
        this.file_name      = file_name;
        this.color          = color;
        this.isLoop         = isLoop;
        this.isFav          = isFav;

    }

    public int getId(){ return this.id; }
    public String getName(){ return this.name; }
    public String getFileName() { return this.file_name; }
    public String getColor() { return this.color; }
    public boolean isLoop() { return this.isLoop; }
    public boolean isFav() { return this.isFav; }

    @Override
    public boolean equals(Object o){
        return this.id == ((Items)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}