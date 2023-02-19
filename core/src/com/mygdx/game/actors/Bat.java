package com.mygdx.game.actors;

import static com.mygdx.game.extras.Utils.USER_BAT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.extras.AssetMan;

/**
 * Clase de tipo actor que interactua con el actor Flammie. Se trata de un enemigo que al colisionar
 * debe terminar la ejecución de la partida actual.
 */
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
     * @param world Mundo en el que se instancia el actor
     * @param position Posición en la que se instancia el actor
     */
    public Bat(World world, Vector2 position){
        super(world, position);
        this.animation = AssetMan.getInstance().getBatAnimation();
        // Calculamos un velocidad aleatoria para cada uno de los murciélagos
        this.randomSpeedFactor = SPEED + MathUtils.random(-0.5f, 0.5f);

        createKinematicBody(position);
        this.body.setLinearVelocity(0, this.randomSpeedFactor);
        createFixture();
    }

    /**
     * Método encargado de pintar el muerciélago
     * @param batch Lote que se encarga de pintar el actor
     * @param parentAlpha Indica el nivel de transparencia del padre
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(this.body.getPosition().x - (BAT_WIDTH/2), this.body.getPosition().y - (BAT_HEIGHT/2));
        batch.draw(this.animation.getKeyFrame(stateTime, true), this.getX(), this.getY(), BAT_WIDTH, BAT_HEIGHT);
        this.stateTime += Gdx.graphics.getDeltaTime();
    }

    // Métodos auxiliares
    /**
     * Método encargado de asignar un fixture y un userData rectangular al murciélago
     */
    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(BAT_WIDTH/2.5f, BAT_HEIGHT/2.5f);
        this.fixture = body.createFixture(shape,8);
        this.fixture.setUserData(USER_BAT);
        shape.dispose();
    }
}
