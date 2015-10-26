package com.develorain.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.develorain.game.Screens.PlayScreen;

public class Illumination extends Game {
	public static final String TITLE = "Illumination";
	public static final int V_WIDTH = 1080;
	public static final int V_HEIGHT = 720;
	public static final float PPM = 100;

	public static final short CUBEY_BIT = 1;
	public static final short WHITETILE_BIT = 2;
	public static final short BLUETILE_BIT = 4;
	public static final short REDTILE_BIT = 8;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
