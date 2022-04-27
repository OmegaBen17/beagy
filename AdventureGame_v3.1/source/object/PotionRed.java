package object;

import entity.Entity;
import main.Display;

public class PotionRed extends Entity {

    Display display;

    public PotionRed(Display display) {

        super(display);

        this.display = display;

        type = consumableObject;
        name = "Healing potion";
        description = "[ " + name + " ]\n" +
                "This potion restores your health to full.\n";
        down1 = setup("/objects/potion_red", display.tileSize, display.tileSize);
    }

    public void use(Entity entity) {

        entity.life = entity.maxLife;

        display.userInterface.addMessage("Life has been recovered!");
    }
}
