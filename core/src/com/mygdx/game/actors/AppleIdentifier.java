package com.mygdx.game.actors;

import static com.mygdx.game.extras.Utils.USER_APPLE;

/**
 * Clase auxiliar encargada de identificar CADA manzana de forma única ya que userData acepta como
 * parámetro un Objeto.
 */
public class AppleIdentifier {
    private static int APPLE_COUNTER = 0;

    private String name;
    private int number;

    public AppleIdentifier(){
        this.name = USER_APPLE;
        this.number = APPLE_COUNTER;
        APPLE_COUNTER += 1;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
