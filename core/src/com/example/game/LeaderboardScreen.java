package com.example.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardScreen implements Screen {

    final MainGame gameSession;

    private Stage stage;

    Texture ScreenPicture;
    Image img;

    Map<String, Object> leaderboard = new HashMap<>();

    public LeaderboardScreen(final MainGame maingame){

        gameSession = maingame;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Gdx.input.setInputProcessor(stage);

        ScreenPicture = new Texture("menu_bg.png");
        img = new Image(ScreenPicture);

        img.setX(0);
        img.setY(0);
        img.setWidth(Gdx.graphics.getWidth());
        img.setHeight(Gdx.graphics.getHeight());

        gameSession.FI.readData();
        leaderboard = gameSession.FI.transferData();

    }

    @Override
    public void render(float delta) {
        gameSession.batch.begin();

        stage.draw();
        gameSession.batch.draw(ScreenPicture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gameSession.font.draw(gameSession.batch, "Rank 1", 160, 1300);
        gameSession.font.draw(gameSession.batch, String.valueOf(leaderboard.get("1")), 800, 1300);
        gameSession.font.draw(gameSession.batch, "Rank 2", 160, 1100);
        gameSession.font.draw(gameSession.batch, String.valueOf(leaderboard.get("2")), 800, 1100);
        gameSession.font.draw(gameSession.batch, "Rank 3", 160, 900);
        gameSession.font.draw(gameSession.batch, String.valueOf(leaderboard.get("3")), 800, 900);
        gameSession.font.draw(gameSession.batch, "Rank 4", 160, 700);
        gameSession.font.draw(gameSession.batch, String.valueOf(leaderboard.get("4")), 800, 700);
        gameSession.font.draw(gameSession.batch, "Rank 5", 160, 500);
        gameSession.font.draw(gameSession.batch, String.valueOf(leaderboard.get("5")), 800, 500);
        gameSession.batch.end();

        if (Gdx.input.justTouched()) {
				/*
				gameState = 1;
				startGame();
				score = 0;
				scoringTube = 0;
				velocity = 0;
				*/
            gameSession.setScreen(new MainMenuScreen(gameSession));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
