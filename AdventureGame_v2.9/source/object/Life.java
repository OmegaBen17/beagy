package object;

import entity.Entity;
import main.Display;

public class Life extends Entity {

    public Life(Display display) {

        super(display);
        
        name = "Life";
        bufferedImage1 = setup("/objects/life_full", display.tileSize, display.tileSize);
        bufferedImage2 = setup("/objects/life_blank", display.tileSize, display.tileSize);
    }
}
