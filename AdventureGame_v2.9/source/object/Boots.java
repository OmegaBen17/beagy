package object;

import entity.Entity;
import main.Display;

public class Boots extends Entity{
    
    public Boots(Display display) {

        super(display);

        name = "Boots";
        down1 = setup("/objects/boots", display.tileSize, display.tileSize);
    }
}
