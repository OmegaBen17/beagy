package object;

import entity.Entity;
import main.Display;

public class Key extends Entity {

    public Key(Display display) {

        super(display);

        name = "Key";
        description = "[ " + name + " ]\n" +
                "This is a key to a door or a chest.\n" +
                "Maybe you can open something?";
        down1 = setup("/objects/key", display.tileSize, display.tileSize);
    }
}
