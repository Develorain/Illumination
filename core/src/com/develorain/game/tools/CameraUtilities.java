package com.develorain.game.tools;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.develorain.game.Illumination.PPM;

public class CameraUtilities {
    public static void lerpToTarget(Camera cam, Vector2 target) {
        Vector3 position = cam.position;
        position.x = cam.position.x + (target.x - cam.position.x) * 0.1f;
        position.y = cam.position.y + (target.y - cam.position.y) * 0.1f;
        cam.position.set(position);

        cam.update();
    }

    // add interpolated camera shake so it looks more natural
    public static void shake(Camera cam) {
        Vector3 position = cam.position;
        float random = (float) Math.random() * 3 - 3;
        position.x = cam.position.x + random / PPM;
        float random1 = (float) Math.random() * 3 - 3;
        position.y = cam.position.y + random1 / PPM;
        cam.update();
    }

    public static void boundary(Camera cam, float startX, float startY, float width, float height) {
        Vector3 position = cam.position;

        if (position.x < startX) {
            position.x = startX;
        }

        if (position.y < startY) {
            position.y = startY;
        }

        if (position.x > width - startX) {
            position.x = width - startX;
        }

        if (position.y > height - startY) {
            position.y = height - startY;
        }

        cam.position.set(position);

        cam.update();
    }
}
