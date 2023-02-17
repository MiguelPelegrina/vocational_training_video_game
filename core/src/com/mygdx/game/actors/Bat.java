package com.mygdx.game.actors;

import static com.mygdx.game.extras.Utils.USER_BAT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.extras.AssetMan;

public class Bat extends BaseActor {
    // Atributos de clase
    public static final float BAT_WIDTH = 1f;
    public static final float BAT_HEIGHT = 0.4f;
    private static final float SPEED = -2f;

    // Atributos de la instancia
    private Animation<TextureRegion> animation;
    private float stateTime;
    private float randomSpeedFactor;

    /**
     * Constructor por parámetros
     * @param world
     * @param position
     */
    public Bat(World world, Vector2 position){
        super(world, position);
        this.animation = AssetMan.getInstance().getBatAnimation();

        this.randomSpeedFactor = SPEED + MathUtils.random(-0.5f, 0.5f);

        createBody(position);
        createFixture();
        // TODO
        //createCounter();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(this.body.getPosition().x - (BAT_WIDTH/2), this.body.getPosition().y - (BAT_HEIGHT/2) );
        batch.draw(this.animation.getKeyFrame(stateTime, true), getX(),getY(),BAT_WIDTH,BAT_HEIGHT);

        this.stateTime += Gdx.graphics.getDeltaTime();
    }


    // Métodos auxiliares
    private void createBody(Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        this.body = this.world.createBody(bodyDef);
        this.body.setLinearVelocity(0, randomSpeedFactor);
    }

    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(BAT_WIDTH/2, BAT_HEIGHT/2);
        this.fixture = body.createFixture(shape,8);
        this.fixture.setUserData(USER_BAT);
        shape.dispose();
    }
}
