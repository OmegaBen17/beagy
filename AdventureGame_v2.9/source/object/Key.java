package object;

import entity.Entity;
import main.Display;

public class Key extends Entity{
    
    public Key(Display display) {

        super(display);
        
        name = "Key";
        down1 = setup("/objects/key", display.tileSize, display.tileSize);
    }
}
