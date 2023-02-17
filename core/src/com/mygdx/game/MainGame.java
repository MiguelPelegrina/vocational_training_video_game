package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.TutorialScreen;

public class MainGame extends Game {
	//Variables de la instancia
	public TutorialScreen tutorialScreen;
	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;

	@Override
	public void create () {
		this.tutorialScreen = new TutorialScreen(this);
		this.gameScreen = new GameScreen(this);
		this.gameOverScreen = new GameOverScreen(this);
		setScreen(this.tutorialScreen);
	}
}
