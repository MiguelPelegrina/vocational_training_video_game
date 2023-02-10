package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.extras.AssetMan;

public class Bat extends Actor {
    // Atributos de clase
    private static final float BAT_WIDTH = 1f;
    private static final float BAT_HEIGHT = 0.4f;

    // Atributos de la instancia
    private Animation<TextureRegion> animation;
    private Body body;
    private Fixture fixture;
    private World world;
    private float stateTime;

    public Bat(World world, Vector2 position){
        this.world = world;
        this.animation = AssetMan.getInstance().getBatAnimation();

        createBody(position);
        createFixture();
    }

    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(this.body.getPosition().x - (BAT_WIDTH/2), this.body.getPosition().y - (BAT_HEIGHT/2) );
        batch.draw(this.animation.getKeyFrame(stateTime, true), getX(),getY(),BAT_WIDTH,BAT_HEIGHT);
    }


    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    // Métodos auxilaires
    private void createBody(Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        this.body = this.world.createBody(bodyDef);
    }

    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(BAT_WIDTH/2, BAT_HEIGHT/2);
        this.fixture = body.createFixture(shape,8);
        shape.dispose();
    }
}
