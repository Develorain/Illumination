package com.develorain.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.develorain.game.Illumination;

public class HUD {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;

    private Label countdownLabel;
    private Label timeLabel;

    public HUD(SpriteBatch sb) {
        viewport = new FitViewport(Illumination.V_WIDTH, Illumination.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        worldTimer = 200;
        timeCount = 0;

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%1d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt) {
        timeCount += dt;

        if(timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%02d", worldTimer));

            timeCount = 0;
        }

        if(worldTimer == 0) {
            Gdx.app.exit();
        }
    }
}
