package com.mygdx.game.actors;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extras.Utils.USER_FLAMMIE;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.extras.AssetMan;

public class Flammie extends Actor {
    // Atributos de la clase
    // Se requieren en otras clases del proyecto
    public static final int STATE_NORMAL = 0;
    public static final int STATE_DEAD = 1;
    // Solo se requieren en la propio clase
    private static final float FLAMMIE_WIDTH = 0.84f;
    private static final float FLAMMIE_HEIGHT = 0.66f;

    //Atributos de la instancia
    private int state;
    private Animation<TextureRegion> flammieAnimation;
    private Vector2 position;
    private float stateTime;
    private World world;
    private Body body;
    private Fixture fixture;

    private Sound jumpSound;
    private Sound crashSound;

    /**
     * Constructor por parámetros
     */
    public Flammie(World world, Vector2 position){
        //Inicialización de atributos
        this.position = position;
        this.world = world;
        this.stateTime = 0f;
        this.state = STATE_NORMAL;

        this.flammieAnimation = AssetMan.getInstance().getFlammieAnimation();
        //this.jumpSound = AssetMan.getInstance().getJumpSound();
        //this.crashSound = AssetMan.getInstance().getCrashSound();

        createBody();
        createFixture();
    }

    /**
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        boolean jump  = Gdx.input.isTouched();

        if(jump && this.state == STATE_NORMAL){
            int positionX = Gdx.input.getX();
            if(positionX > SCREEN_WIDTH/2){
                this.body.setLinearVelocity(1.25f,0f);
            }else{
                this.body.setLinearVelocity(-1.25f,0f);
            }
        }

        /* utilizando una variable acumuladora (con delta), cambiando la animación (el texturregion)
        después del touch, y cuando la variable acumuladora sea mayor de 1 segundo (por ejemplo)
        volver a la animación original y reiniciar la variable acumuladora a 0

        if(jump && this.state == STATE_NORMAL){
            this.jumpSound.play();
            this.body.setLinearVelocity(0,JUMP_SPEED);
        }*/
    }

    /**
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x - FLAMMIE_WIDTH/2, body.getPosition().y - FLAMMIE_HEIGHT/2);
        batch.draw(this.flammieAnimation.getKeyFrame(stateTime, true), getX(), getY(), FLAMMIE_WIDTH,FLAMMIE_HEIGHT);
        //batch.draw(this.flammieAnimation.getKeyFrame(stateTime, true), this.position.x, this.position.y, FLAMMIE_WIDTH,FLAMMIE_HEIGHT);

        stateTime += Gdx.graphics.getDeltaTime();
    }

    /**
     *
     */
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    public int getState(){
        return this.state;
    }

    /**
     * Método encargado de "matar" al personaje
     */
    public void dies(){
        state = STATE_DEAD;
        stateTime = 0f;
    }

    // Métodos auxiliares
    /**
     *
     */
    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        this.body = this.world.createBody(bodyDef);
    }

    /**
     *
     */
    private void createFixture(){
        CircleShape circle = new CircleShape();
        circle.setRadius(0.3f);

        this.fixture = this.body.createFixture(circle, 8);
        this.fixture.setUserData(USER_FLAMMIE);

        circle.dispose();
    }
}
