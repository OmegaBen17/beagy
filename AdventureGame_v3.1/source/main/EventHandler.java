package main;

public class EventHandler {

    Display display;

    EventRectangle eventRectangle[][];

    int previousEventX;
    int previousEventY;
    boolean touchableEvent = true;

    public EventHandler(Display display) {

        this.display = display;

        eventRectangle = new EventRectangle[display.maxMapCol][display.maxMapRow];

        int col = 0;
        int row = 0;

        while (col < display.maxMapCol &&
                row < display.maxMapRow) {

            eventRectangle[col][row] = new EventRectangle();
            eventRectangle[col][row].x = 23;
            eventRectangle[col][row].y = 23;
            eventRectangle[col][row].width = 2;
            eventRectangle[col][row].height = 2;
            eventRectangle[col][row].eventRectangleDefaultX = eventRectangle[col][row].x;
            eventRectangle[col][row].eventRectangleDefaultY = eventRectangle[col][row].y;

            col++;
            if (col == display.maxMapCol) {

                col = 0;
                row++;
            }
        }

    }

    public void checkEvent() {

        // CHECK IF HERO IS AT LEAST 1 TILE FAR
        int distanceX = Math.abs(display.hero.mapX - previousEventX);
        int distanceY = Math.abs(display.hero.mapY - previousEventY);
        int distance = Math.max(distanceX, distanceY);

        if (distance > display.tileSize) {

            touchableEvent = true;
        }

        if (touchableEvent) {

            if (hitDetector(25, 23, "any")) {

                burn(25, 23, display.dialogueState);
            }
        }

    }

    public boolean hitDetector(int col, int row, String requiredDirection) {

        boolean hit = false;

        display.hero.solidArea.x = display.hero.mapX + display.hero.solidArea.x;
        display.hero.solidArea.y = display.hero.mapY + display.hero.solidArea.y;

        eventRectangle[col][row].x = col * display.tileSize + eventRectangle[col][row].x;
        eventRectangle[col][row].y = row * display.tileSize + eventRectangle[col][row].y;

        if (display.hero.solidArea.intersects(eventRectangle[col][row]) && !eventRectangle[col][row].eventDone) {

            if (display.hero.direction.contentEquals(requiredDirection) ||
                    requiredDirection.contentEquals("any")) {

                hit = true;

                previousEventX = display.hero.mapX;
                previousEventY = display.hero.mapY;
            }
        }

        display.hero.solidArea.x = display.hero.solidAreaDefaultX;
        display.hero.solidArea.y = display.hero.solidAreaDefaultY;

        eventRectangle[col][row].x = eventRectangle[col][row].eventRectangleDefaultX;
        eventRectangle[col][row].y = eventRectangle[col][row].eventRectangleDefaultY;

        return hit;
    }

    public void burn(int col, int row, int gameState) {

        display.gameState = gameState;

        display.userInterface.currentDialogue = "You have been burned by fire!";
        display.hero.life -= 1;

        // eventRectangle[col][row].eventDone = true;

        touchableEvent = false;
    }
}
