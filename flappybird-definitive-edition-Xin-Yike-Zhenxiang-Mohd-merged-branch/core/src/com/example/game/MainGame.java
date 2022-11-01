package com.example.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {

    SpriteBatch batch;
    BitmapFont font;

    FirebaseInterface FI;

    BluetoothInterface BI;
    boolean buttonOn = false;
    int bluetoothStatus;
    
    WeatherInterface WI;
    
    public MainGame(BluetoothInterface BI, FirebaseInterface FI, WeatherInterface WI;){
        this.BI = BI;
        this.FI = FI;
        this.WI = WI;
    }

    public void create() {
        batch = new SpriteBatch();
        // Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.getData().scale(2);
        //font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.BLACK);
        this.setScreen(new StartScreen(this, FI));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void bluetoothPress() {
        buttonOn = true;
    }


    public void setBluetoothStatus(int statusCode) {
        bluetoothStatus = statusCode;
    }

}
