package object;

import entity.Entity;
import main.Display;

public class Door extends Entity{
    
    public Door(Display display) {

        super(display);
        
        name = "Door";
        down1 = setup("/objects/door", display.tileSize, display.tileSize);

        collision = true;
    }
}
