package com.example.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen implements Screen {

    final MainGame game;

    private Stage stage;

    Texture background;
    Image img;

    Texture[] upTextures = new Texture[7];
    Button[] buttons = new Button[7];

    FirebaseInterface FI;

    public MainMenuScreen(final MainGame maingame) {

        game = maingame;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Gdx.input.setInputProcessor(stage);

        background = new Texture("menu_bg.png");
        img = new Image(background);

        img.setX(0);
        img.setY(0);
        img.setWidth(Gdx.graphics.getWidth());
        img.setHeight(Gdx.graphics.getHeight());

        upTextures[0] = new Texture(Gdx.files.internal("button_normal.png"));
        Button.ButtonStyle style_n = new Button.ButtonStyle();
        style_n.up = new TextureRegionDrawable(new TextureRegion(upTextures[0]));
        buttons[0] = new Button(style_n);
        buttons[0].setPosition(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 25);
        buttons[0].setWidth(500);
        buttons[0].setHeight(250);
        buttons[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new FlappyBird(game));
                dispose();
            }
        });

        upTextures[1] = new Texture(Gdx.files.internal("button_motion.png"));
        Button.ButtonStyle style_m = new Button.ButtonStyle();
        style_m.up = new TextureRegionDrawable(new TextureRegion(upTextures[1]));
        buttons[1] = new Button(style_m);
        buttons[1].setPosition(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 200);
        buttons[1].setWidth(500);
        buttons[1].setHeight(250);
        buttons[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new MotionScreen(game));
                dispose();
            }
        });

        upTextures[2] = new Texture(Gdx.files.internal("button_voice.png"));
        Button.ButtonStyle style_v = new Button.ButtonStyle();
        style_v.up = new TextureRegionDrawable(new TextureRegion(upTextures[2]));
        buttons[2] = new Button(style_v);
        buttons[2].setPosition(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 375);
        buttons[2].setWidth(500);
        buttons[2].setHeight(250);
        buttons[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new VoiceScreen(game));
                dispose();

            }
        });


        if (game.bluetoothStatus == 1) {
            upTextures[3] = new Texture(Gdx.files.internal("button_bluetooth.png"));
        }
        else {
            upTextures[3] = new Texture(Gdx.files.internal("button_bluetooth1.png"));
        }

        Button.ButtonStyle style_bt = new Button.ButtonStyle();
        style_bt.up = new TextureRegionDrawable(new TextureRegion(upTextures[3]));
        buttons[3] = new Button(style_bt);
        buttons[3].setPosition(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 550);
        buttons[3].setWidth(500);
        buttons[3].setHeight(250);

        if (game.bluetoothStatus == 1) {
            buttons[3].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (game.BI.isControllerAvailable()) {
                        game.setScreen(new BluetoothScreen(game));
                    }
                    else {
                        game.BI.bluetoothUnavailableMsg();
                        game.bluetoothStatus = 3;
                        game.setScreen(new MainMenuScreen(game));
                    }
                    dispose();
                }
            });
        }
        else {
            buttons[3].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.BI.bluetoothUnavailableHandler();
                    if (game.BI.isControllerAvailable()) {
                        game.BI.bluetoothAvailableMsg();
                        game.bluetoothStatus = 1;
                        game.setScreen(new MainMenuScreen(game));
                        dispose();
                    }
                }
            });
        }

        upTextures[4] = new Texture(Gdx.files.internal("button_quit.png"));
        Button.ButtonStyle style_q = new Button.ButtonStyle();
        style_q.up = new TextureRegionDrawable(new TextureRegion(upTextures[4]));
        buttons[4] = new Button(style_q);
        buttons[4].setPosition(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 725);
        buttons[4].setWidth(500);
        buttons[4].setHeight(250);
        buttons[4].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.exit();

            }
        });

        upTextures[5] = new Texture(Gdx.files.internal("score.png"));
        Button.ButtonStyle style_s = new Button.ButtonStyle();
        style_s.up = new TextureRegionDrawable(new TextureRegion(upTextures[5]));
        buttons[5] = new Button(style_s);
        buttons[5].setPosition(20, 20);
        buttons[5].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new LeaderboardScreen(game));
                dispose();

            }
        });

        upTextures[6] = new Texture(Gdx.files.internal("background.png"));
        Button.ButtonStyle style_b = new Button.ButtonStyle();
        style_b.up = new TextureRegionDrawable(new TextureRegion(upTextures[6]));
        buttons[6] = new Button(style_b);
        buttons[6].setPosition(Gdx.graphics.getWidth() - buttons[6].getWidth(), 0);
        buttons[6].setPosition(Gdx.graphics.getWidth() - 250, 0);
        buttons[6].setWidth(240);
        buttons[6].setHeight(240);
        buttons[6].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new FlappyBird(game));
                dispose();

            }
        });

        stage.addActor(img);
        stage.addActor(buttons[0]);
        stage.addActor(buttons[1]);
        stage.addActor(buttons[2]);
        stage.addActor(buttons[3]);
        stage.addActor(buttons[4]);
        stage.addActor(buttons[5]);
        stage.addActor(buttons[6]);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);

        stage.act();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

        stage.dispose();

    }
}
