package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.extras.AssetMan;

public class Apple extends BaseActor {
    // Atributos de la clase
    public static final float APPLE_WIDTH = 0.5f;
    public static final float APPLE_HEIGHT = 0.5f;
    private static final float APPLE_FIXTURE_RADIUS = 0.225f;
    private static final float SPEED = -1f;


    // Atributos de la instancia
    private TextureRegion appleTR;
    private int state;
    private AppleIdentifier identifier;

    public Apple(World world, Vector2 position){
        super(world, position);
        this.appleTR = AssetMan.getInstance().getApple();
        this.state = STATE_ALIVE;

        this.identifier = new AppleIdentifier();

        createBody();
        createCircularFixture(APPLE_FIXTURE_RADIUS, this.identifier);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(this.body.getPosition().x - (APPLE_WIDTH/2), this.body.getPosition().y - (APPLE_HEIGHT/2));
        batch.draw(this.appleTR, this.getX(),this.getY(),APPLE_WIDTH,APPLE_HEIGHT);
    }

    public int getState(){
        return this.state;
    }

    public void getsEaten(){
        state = STATE_DEAD;
    }

    public AppleIdentifier getIdentifier(){
        return this.identifier;
    }

    // Métodos auxiliares
    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        this.body = this.world.createBody(bodyDef);
        this.body.setLinearVelocity(0, SPEED);
    }
}
