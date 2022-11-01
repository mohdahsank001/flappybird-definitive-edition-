package com.example.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.graphics.Texture;


public class FlappyBird extends ApplicationAdapter {

	SpriteBatch Abatch;
	Texture Gamebackground;

	//ShapeRenderer shapeRenderer;

	Texture GameEnds;
	Texture[] Objectsbrds;
	int flappingstate = 0;

	int gameState = 0;
	float Dropcount = 2;

	float BirdObject = 0;
	float speed = 0;
	Circle birdreach;
	int points = 0;
	int pointsobject = 0;
	BitmapFont gamefnts ;


	Texture Upperblock;
	Texture Lowerblock;
	float BlockDistance = 700;
	float BlockOfst;
	Random numbrgntr;
	float BlockSpeed = 4;
	int numberofBlocks = 4;
	float[] BlockA = new float[numberofBlocks];
	float[] Blockofst = new float[numberofBlocks];
	float Blockgapvalues;
	Rectangle[] UppersideBlocks;
	Rectangle[] LowersideBlocks;



	@Override
	public void create () {
		Abatch = new SpriteBatch();
		Gamebackground = new Texture("bg.png");
		GameEnds = new Texture("gameover.png");
		//shapeRenderer = new ShapeRenderer();

		birdreach = new Circle();
		gamefnts = new BitmapFont();
		gamefnts.setColor(Color.WHITE);
		gamefnts.getData().setScale(10);

		Objectsbrds = new Texture[2];
		Objectsbrds[0] = new Texture("bird.png");
		Objectsbrds[1] = new Texture("bird1.png");


		Upperblock = new Texture("toptube.png");
		Lowerblock = new Texture("bottomtube.png");
		BlockOfst = Gdx.graphics.getHeight() / 2 - BlockDistance / 2 - 100 ; //
		numbrgntr = new Random();
		Blockgapvalues = Gdx.graphics.getWidth() * 3 / 4;
		UppersideBlocks = new Rectangle[numberofBlocks];
		LowersideBlocks = new Rectangle[numberofBlocks];

		BeginGame();



	}

	public void BeginGame() {

		BirdObject = Gdx.graphics.getHeight() / 2 - Objectsbrds[0].getHeight() / 2;

		for (int i = 0; i < numberofBlocks; i++) {

			Blockofst[i] = (numbrgntr.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - BlockDistance - 200);

			BlockA[i] = Gdx.graphics.getWidth() / 2 - Upperblock.getWidth() / 2 + Gdx.graphics.getWidth() + i * Blockgapvalues;

			UppersideBlocks[i] = new Rectangle();
			LowersideBlocks[i] = new Rectangle();

		}

	}

	@Override
	public void render () {

		Abatch.begin();
		Abatch.draw(Gamebackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {

			if (BlockA[pointsobject] < Gdx.graphics.getWidth() / 2) {

				points++;

				Gdx.app.log("Score", String.valueOf(points));

				if (pointsobject < numberofBlocks - 1) {

					pointsobject++;

				} else {

					pointsobject = 0;

				}

			}

			if (Gdx.input.justTouched()) {

				speed = -30;

			}

			for (int i = 0; i < numberofBlocks; i++) {

				if (BlockA[i] < - Upperblock.getWidth()) {

					BlockA[i] += numberofBlocks * Blockgapvalues;
					Blockofst[i] = (numbrgntr.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - BlockDistance - 200);

				} else {

					BlockA[i] = BlockA[i] - BlockSpeed;



				}

				Abatch.draw(Upperblock, BlockA[i], Gdx.graphics.getHeight() / 2 + BlockDistance / 2 + Blockofst[i]);
				Abatch.draw(Lowerblock, BlockA[i], Gdx.graphics.getHeight() / 2 - BlockDistance / 2 - Lowerblock.getHeight() + Blockofst[i]);

				UppersideBlocks[i] = new Rectangle(BlockA[i], Gdx.graphics.getHeight() / 2 + BlockDistance / 2 + Blockofst[i], Upperblock.getWidth(), Upperblock.getHeight());
				LowersideBlocks[i] = new Rectangle(BlockA[i], Gdx.graphics.getHeight() / 2 - BlockDistance / 2 - Lowerblock.getHeight() + Blockofst[i], Lowerblock.getWidth(), Lowerblock.getHeight());
			}



			if (BirdObject > 0) {

				speed = speed + Dropcount;
				BirdObject -= speed;

			} else {

				gameState = 2;

			}

		} else if (gameState == 0) {

			if (Gdx.input.justTouched()) {
				gameState = 1 ;
			}

		} else if (gameState == 2) {

			Abatch.draw(GameEnds, Gdx.graphics.getWidth() / 2 - GameEnds.getWidth() / 2, Gdx.graphics.getHeight() / 2 - GameEnds.getHeight() / 2);

			if (Gdx.input.justTouched()) {
				gameState = 1;
				BeginGame();
				points = 0;
				pointsobject = 0;
				speed = 0;

			}

		}

		if (flappingstate == 0) {
			flappingstate = 1;
		} else {
			flappingstate = 0;
		}



		Abatch.draw(Objectsbrds[flappingstate], Gdx.graphics.getWidth() / 2 - Objectsbrds[flappingstate].getWidth() / 2, BirdObject);

		gamefnts.draw(Abatch, String.valueOf(points), 100, 200);

		birdreach.set(Gdx.graphics.getWidth() / 2, BirdObject + Objectsbrds[flappingstate].getHeight() / 2, Objectsbrds[flappingstate].getWidth() / 2);




		for (int i = 0; i < numberofBlocks; i++) {

			if (Intersector.overlaps(birdreach, UppersideBlocks[i]) || Intersector.overlaps(birdreach, LowersideBlocks[i])) {

				gameState = 2;

			}

		}

		Abatch.end();



	}


}


