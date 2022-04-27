package object;

import entity.Entity;
import main.Display;

public class Boots extends Entity {

    public Boots(Display display) {

        super(display);

        name = "Boots";
        description = "[ " + name + " ]\n" +
                "These boots increase your speed.";
        down1 = setup("/objects/boots", display.tileSize, display.tileSize);
    }
}
