package com.mygdx.game.actors;

import static com.mygdx.game.extras.Utils.USER_APPLE;

/**
 * Clase auxiliar encargada de identificar CADA manzana de forma única pasándole a userData como
 * parámetro un objeto de esta clase.
 */
public class AppleIdentifier {
    // Atributo de la clase
    private static int APPLE_COUNTER = 0;
    // Atributos de la instancia
    private String name;
    private int number;

    /**
     * Constructor por parámetros
     */
    public AppleIdentifier(){
        this.name = USER_APPLE;
        this.number = APPLE_COUNTER;
        APPLE_COUNTER += 1;
    }

    /**
     * Devuelve el nombre del identificador de la clase manzana
     * @return Devuelve el nombre identificador de la clase
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el número único del identificador del objeto de la clase manzana
     * @return Devuelve el número identificador del objeto
     */
    public int getNumber() {
        return number;
    }
}
