package com.develorain.game.Tools;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraUtilities {
    public static void lerpToTarget(Camera cam, Vector2 target) {
        Vector3 position = cam.position;
        position.x = cam.position.x + (target.x - cam.position.x) * 0.1f;
        position.y = cam.position.y + (target.y - cam.position.y) * 0.1f;
        cam.position.set(position);

        cam.update();
    }
}
