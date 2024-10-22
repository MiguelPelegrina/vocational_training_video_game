package com.mygdx.game.extras;

import static com.mygdx.game.extras.Utils.APPLE;
import static com.mygdx.game.extras.Utils.ATLAS_MAP;
import static com.mygdx.game.extras.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.extras.Utils.BACKGROUND_IMAGE_START;
import static com.mygdx.game.extras.Utils.BAT1;
import static com.mygdx.game.extras.Utils.BAT2;
import static com.mygdx.game.extras.Utils.BAT3;
import static com.mygdx.game.extras.Utils.BAT4;
import static com.mygdx.game.extras.Utils.FLAMMIE1;
import static com.mygdx.game.extras.Utils.FLAMMIE10;
import static com.mygdx.game.extras.Utils.FLAMMIE10L;
import static com.mygdx.game.extras.Utils.FLAMMIE10R;
import static com.mygdx.game.extras.Utils.FLAMMIE1L;
import static com.mygdx.game.extras.Utils.FLAMMIE1R;
import static com.mygdx.game.extras.Utils.FLAMMIE2;
import static com.mygdx.game.extras.Utils.FLAMMIE2L;
import static com.mygdx.game.extras.Utils.FLAMMIE2R;
import static com.mygdx.game.extras.Utils.FLAMMIE3;
import static com.mygdx.game.extras.Utils.FLAMMIE3L;
import static com.mygdx.game.extras.Utils.FLAMMIE3R;
import static com.mygdx.game.extras.Utils.FLAMMIE4;
import static com.mygdx.game.extras.Utils.FLAMMIE4L;
import static com.mygdx.game.extras.Utils.FLAMMIE4R;
import static com.mygdx.game.extras.Utils.FLAMMIE5;
import static com.mygdx.game.extras.Utils.FLAMMIE5L;
import static com.mygdx.game.extras.Utils.FLAMMIE5R;
import static com.mygdx.game.extras.Utils.FLAMMIE6;
import static com.mygdx.game.extras.Utils.FLAMMIE6L;
import static com.mygdx.game.extras.Utils.FLAMMIE6R;
import static com.mygdx.game.extras.Utils.FLAMMIE7;
import static com.mygdx.game.extras.Utils.FLAMMIE7L;
import static com.mygdx.game.extras.Utils.FLAMMIE7R;
import static com.mygdx.game.extras.Utils.FLAMMIE8;
import static com.mygdx.game.extras.Utils.FLAMMIE8L;
import static com.mygdx.game.extras.Utils.FLAMMIE8R;
import static com.mygdx.game.extras.Utils.FLAMMIE9;
import static com.mygdx.game.extras.Utils.FLAMMIE9L;
import static com.mygdx.game.extras.Utils.FLAMMIE9R;
import static com.mygdx.game.extras.Utils.FONT_FNT;
import static com.mygdx.game.extras.Utils.FONT_PNG;
import static com.mygdx.game.extras.Utils.MUSIC_BG;
import static com.mygdx.game.extras.Utils.MUSIC_GAMEOVER;
import static com.mygdx.game.extras.Utils.SOUND_CHOMP;
import static com.mygdx.game.extras.Utils.SOUND_HIT;
import static com.mygdx.game.extras.Utils.SOUND_LISTEN;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Clase encargada de gestionar nuestros recursos almacenados en la carpeta assets del proyecto.
 * Permite cargar los elementos audiovisuales de forma asíncrona, eficiente y compacta, de tal
 * forma que podemos acceder a todos los elementos con una instancia de esta clase
 */
public class AssetMan {
    // Atributo de la clase
    // Nos creamos una instancia única de la propio clase, de tal forma que solo se crea un objeto
    // que gestiona los recursos para el proyecto entero
    private static final AssetMan INSTANCIA = new AssetMan();
    // Atributos de la instancia
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    /**
     * Constructor por defecto. Es privado para no permitir la instanciación no controlada de un
     * objeto (Patrón SINGLETON). Carga todos los recursos que necesitaremos posteriormente
     */
    private AssetMan() {
        // Instanciamos un objeto de la clase AssetManager
        this.assetManager = new AssetManager();
        // Cargamos el TextureAtlas que guarda las texturas que se usarán posteriormente
        this.assetManager.load(ATLAS_MAP, TextureAtlas.class);
        this.assetManager.load(MUSIC_BG, Music.class);
        this.assetManager.load(MUSIC_GAMEOVER, Music.class);
        this.assetManager.load(SOUND_HIT, Sound.class);
        this.assetManager.load(SOUND_LISTEN, Sound.class);
        this.assetManager.load(SOUND_CHOMP, Sound.class);
        // Esperamos a que todos los recursos se hayan cargado
        this.assetManager.finishLoading();

        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }

    //Métodos
    /**
     * Método de clase encargado de devolver la isntancia del AssetManager
     * @return Devuelve una instancia única de un AssetManager
     */
    public static AssetMan getInstance(){
        return INSTANCIA;
    }

    // Getter de los diferentes assets que se van a utilizar
    /**
     * Método encargado de devolver la imagen de fondo
     * @return Devuelve la imagen de fondo
     */
    public TextureRegion getBackgroundPlay(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }

    /**
     * Método encargado de devolver la imagen de fondo
     * @return Devuelve la imagen de fondo
     */
    public TextureRegion getBackgroundDark(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE_START);
    }

    /**
     * Método encargado de devolver las animaciones del muñeco moviéndose hacia adelante y el tiempo
     * durante el cual se muestra cada una de las imágenes
     * @return Devuelve las animaciones que simulan el movimiento del muñeco
     */
    public Animation<TextureRegion> getFlammieAnimation(){
        return new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegion(FLAMMIE1),
                textureAtlas.findRegion(FLAMMIE2),
                textureAtlas.findRegion(FLAMMIE3),
                textureAtlas.findRegion(FLAMMIE4),
                textureAtlas.findRegion(FLAMMIE5),
                textureAtlas.findRegion(FLAMMIE6),
                textureAtlas.findRegion(FLAMMIE7),
                textureAtlas.findRegion(FLAMMIE8),
                textureAtlas.findRegion(FLAMMIE9),
                textureAtlas.findRegion(FLAMMIE10));
    }

    /**
     * Método encargado de devolver las animaciones del muñeco moviéndose hacia la izquierda y el
     * tiempo durante el cual se muestra cada una de las imágenes
     * @return Devuelve las animaciones que simulan el movimiento del muñeco hacia la izquierda
     */
    public Animation<TextureRegion> getFlammieAnimationL(){
        return new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegion(FLAMMIE1L),
                textureAtlas.findRegion(FLAMMIE2L),
                textureAtlas.findRegion(FLAMMIE3L),
                textureAtlas.findRegion(FLAMMIE4L),
                textureAtlas.findRegion(FLAMMIE5L),
                textureAtlas.findRegion(FLAMMIE6L),
                textureAtlas.findRegion(FLAMMIE7L),
                textureAtlas.findRegion(FLAMMIE8L),
                textureAtlas.findRegion(FLAMMIE9L),
                textureAtlas.findRegion(FLAMMIE10L)
        );
    }

    /**
     * Método encargado de devolver las animaciones del muñeco moviéndose hacia la derecha y el
     * tiempo durante el cual se muestra cada una de las imágenes
     * @return Devuelve las animaciones que simulan el movimiento del muñeco hacia la derecha
     */
    public Animation<TextureRegion> getFlammieAnimationR(){
        return new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegion(FLAMMIE1R),
                textureAtlas.findRegion(FLAMMIE2R),
                textureAtlas.findRegion(FLAMMIE3R),
                textureAtlas.findRegion(FLAMMIE4R),
                textureAtlas.findRegion(FLAMMIE5R),
                textureAtlas.findRegion(FLAMMIE6R),
                textureAtlas.findRegion(FLAMMIE7R),
                textureAtlas.findRegion(FLAMMIE8R),
                textureAtlas.findRegion(FLAMMIE9R),
                textureAtlas.findRegion(FLAMMIE10R)
        );
    }

    /**
     * Método encargado
     * @return Devuelve las animaciones
     */
    public Animation<TextureRegion> getBatAnimation(){
        return new Animation<TextureRegion>(0.25f,
                textureAtlas.findRegion(BAT1),
                textureAtlas.findRegion(BAT2),
                textureAtlas.findRegion(BAT3),
                textureAtlas.findRegion(BAT4)
        );
    }

    public TextureRegion getApple(){
        return textureAtlas.findRegion(APPLE);
    }

    /**
     * Método encargado de devolver la música de fondo de la pantalla de juego
     * @return Devuelve la música de fondo
     */
    public Music getBGMusic(){
        return this.assetManager.get(MUSIC_BG);
    }

    /**
     * étodo encargado de devolver la música de fondo de la pantalla de game over
     * @return Devuelve la música de fondo
     */
    public Music getGOMusic(){
        return this.assetManager.get(MUSIC_GAMEOVER);
    }

    /**
     * Método encargado de devolver el sonido del choque del actor principal
     * @return Devuelve el sonido de choque con otros objetos
     */
    public Sound getHitSound(){
        return this.assetManager.get(SOUND_HIT);
    }

    /**
     * Método encargado de devolver el sonido para llamar la atención en los mensajes del tutorial
     * @return Devuelve un sonido que avisa para prestar atención
     */
    public Sound getListenSound(){
        return this.assetManager.get(SOUND_LISTEN);
    }

    /**
     * Método encargado de devolver el sonido de comer
     * @return Devuelve un sonido de comer
     */
    public Sound getChompSound(){
        return this.assetManager.get(SOUND_CHOMP);
    }

    /**
     * Método encargado de devolver la fuente principal que se va a utilizar en todo el proyecto
     * @return Devuele la fuente para escribir mensajes
     */
    public BitmapFont getFont(){
        return new BitmapFont(Gdx.files.internal(FONT_FNT), Gdx.files.internal(FONT_PNG), false);
    }
}
