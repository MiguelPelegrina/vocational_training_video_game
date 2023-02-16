package com.mygdx.game.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {
    protected World world;
    protected Vector2 position;
    protected Body body;
    protected Fixture fixture;

}
