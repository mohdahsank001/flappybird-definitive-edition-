package com.example.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

public class VoiceScreen implements Screen {

    final MainGame game;
    SpriteBatch batch;
    Texture background;

    Texture gameover;

    Texture[] birds;
    int flapState = 0;
    float birdY = 0;
    float velocity = 0;
    Circle birdCircle;
    int score = 0;
    int scoringTube = 0;
    BitmapFont font;

    int gameState = 0;
    float gravity = 2;

    Texture topTube;
    Texture bottomTube;
    float gap = 400;
    float maxTubeOffset;
    Random randomGenerator;
    float tubeVelocity = 4;
    int numberOfTubes = 4;
    float[] tubeX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTubes;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;
	
    FirebaseInterface FI;
	
    int uploadCount;

    double volume = 0;
    final AudioRecorder recorder;
    final short[] samples;

    public VoiceScreen(final MainGame maingame, FirebaseInterface FI) {

        game = maingame;
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        gameover = new Texture("gameover.png");
        //shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);

        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird1.png");


        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
        topTubeRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];

        recorder = Gdx.audio.newAudioRecorder(44100, true);
        samples = new short[44100];
        //recorder.read(samples, 0, samples.length);
        new Thread(new Runnable() {
            @Override
            public void run() {

                Boolean isGetVoiceRun = true;

                while (isGetVoiceRun) {

                    recorder.read(samples, 0, samples.length);
                    long v = 0;

                    for (int i = 0; i < samples.length; i++) {
                        v += samples[i] * samples[i];
                    }

                    double mean = v / (double) samples.length;
                    volume = 10 * Math.log10(mean);
                }
            }
        }).start();

	this.FI = FI;
        uploadCount = 0;

        startGame();
    }

    public void startGame() {

        birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

        for (int i = 0; i < numberOfTubes; i++) {

            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();

        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {

            if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {

                score++;

                Gdx.app.log("Score", String.valueOf(score));

                if (scoringTube < numberOfTubes - 1) {

                    scoringTube++;

                } else {

                    scoringTube = 0;

                }

            }

            if (volume > 40) {

                velocity = -5;

            } else {

                velocity = 5;

            }

            for (int i = 0; i < numberOfTubes; i++) {

                if (tubeX[i] < - topTube.getWidth()) {

                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

                } else {

                    tubeX[i] = tubeX[i] - tubeVelocity;



                }

                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

                topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
                bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
            }



            if (birdY > 0) {

                velocity = velocity + gravity;
                birdY -= velocity;

            } else {

                gameState = 2;

            }

        } else if (gameState == 0) {

            if (volume>0) {

                gameState = 1;

            }

        } else if (gameState == 2) {

	    FI.updateData(score, uploadCount);
            uploadCount = 1;
		
            batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2);

            if (Gdx.input.justTouched()) {

				/*
				gameState = 1;
				startGame();
				score = 0;
				scoringTube = 0;
				velocity = 0;
				*/
                recorder.dispose();
                game.setScreen(new MainMenuScreen(game, FI));
                dispose();
            }

        }

        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }



        batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);

        font.draw(batch, String.valueOf(score), 100, 200);

        birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

        for (int i = 0; i < numberOfTubes; i++) {

            if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {

                gameState = 2;

            }

        }

        batch.end();

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
