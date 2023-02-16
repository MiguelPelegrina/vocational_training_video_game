package com.mygdx.game.screens;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

/**
 * Clase que mostrará un tutorial. Utilizaremos el estado del tutorial para mostrar los diferentes
 * aspectos del juego: evitar enemigos, coleccionar manzanas y como desplazarse
 */
public class TutorialScreen extends BaseScreen{
    // Atributos de la clase
    private int state;
    private Sound sound;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public TutorialScreen(MainGame mainGame) {
        super(mainGame);
        this.state = 0;
        this.sound = AssetMan.getInstance().getHitSound();
    }


}
