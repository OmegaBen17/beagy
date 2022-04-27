package object;

import entity.Entity;
import main.Display;

public class Chest extends Entity{
    
    public Chest(Display display) {

        super(display);

        name = "Chest";
        down1 = setup("/objects/chest", display.tileSize, display.tileSize);

        collision = true;
    }
}
