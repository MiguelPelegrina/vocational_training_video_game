package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.extras.AssetMan;

public class Apple extends BaseActor {
    // Atributos de la clase
    private static final float APPLE_WIDTH = 0.5f;
    private static final float APPLE_HEIGHT = 0.5f;
    private static final float APPLE_FIXTURE_RADIUS = 0.15f;
    private static final float SPEED = 1f;

    // Atributos de la instancia
    private TextureRegion appleTR;

    public Apple(World world, Vector2 position){
        this.appleTR = AssetMan.getInstance().getApple();
        this.world = world;
        this.position = position;

    }
}
