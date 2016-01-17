package com.develorain.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.develorain.game.Illumination;
import com.develorain.game.screens.PlayScreen;

public class HUD {
    public Stage stage;
    public BitmapFont font;
    private Viewport viewport;
    private int worldTimer;
    private Label countdownLabel;
    private Label timeLabel;

    public HUD(SpriteBatch batch) {
        viewport = new FitViewport(Illumination.RESOLUTION_X, Illumination.RESOLUTION_Y, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        worldTimer = 0;

        // FONT
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/unsteady oversteer.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 80;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        generator.dispose();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%d", worldTimer), new Label.LabelStyle(font, Color.WHITE));
        timeLabel = new Label("ILLUMINATION", new Label.LabelStyle(font, Color.WHITE));

        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update() {
        if (PlayScreen.currentTime >= 1) {
            worldTimer++;
            countdownLabel.setText(String.format("%d", worldTimer));
            PlayScreen.currentTime = 0;
        }
    }
}
