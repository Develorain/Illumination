package com.develorain.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.develorain.game.Screens.PlayScreen;

public class Illumination extends Game {
	public static final String TITLE = "Illumination";
	public static final int V_WIDTH = 1080;
	public static final int V_HEIGHT = 720;
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short WHITESLOPE_BIT = 4;
	public static final short BLUESLOPE_BIT = 8;
	public static final short REDSLOPE_BIT = 16;

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
