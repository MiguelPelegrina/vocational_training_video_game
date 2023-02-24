package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.TutorialScreen;

/**
 * Clase principal que gestiona las diferentes pantallas
 */
public class MainGame extends Game {
	// Atributos de la instancia
	public TutorialScreen tutorialScreen;
	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;

	/**
	 * Instanciamos todas las pantallas
	 */
	@Override
	public void create () {
		// Instanciamos las diferentes pantallas que vamos a utilizar
		this.tutorialScreen = new TutorialScreen(this);
		this.gameScreen = new GameScreen(this);
		this.gameOverScreen = new GameOverScreen(this);
		// Ponemos la pantalla de tutorial
		setScreen(this.tutorialScreen);
	}
}
