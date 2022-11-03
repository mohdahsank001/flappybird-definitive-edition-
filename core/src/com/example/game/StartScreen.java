package com.example.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen implements Screen {

    final MainGame gameSession;
    OrthographicCamera camera;

    public StartScreen(final MainGame maingame) {

        gameSession = maingame;

        // Get the current location of the device in latitude and longitude.
        gameSession.LLI.getLastLocation();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255, 255, 255, 1);

        camera.update();
        gameSession.batch.setProjectionMatrix(camera.combined);

        // Print the welcome message.
        gameSession.batch.begin();
        gameSession.font.draw(gameSession.batch, "Welcome to Flappy Bird", 160, 320);
        gameSession.font.draw(gameSession.batch, "Tap anywhere to begin", 180, 230);
        gameSession.batch.end();

        // If touched again, enter the  main menu screen.
        if (Gdx.input.isTouched()) {
            gameSession.setScreen(new MainMenuScreen(gameSession));
            dispose();
        }
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
    }
}
