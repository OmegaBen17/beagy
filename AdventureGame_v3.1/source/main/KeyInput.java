package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    Display display;

    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean ePressed = false;
    public boolean spacePressed = false;

    // DEBUG
    boolean showDebugText = false;

    public KeyInput(Display display) {

        this.display = display;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // MENU STATE
        if (display.gameState == display.menuState) {

            menuState(code);
        }

        // PLAY STATE
        else if (display.gameState == display.playState) {

            playState(code);
        }

        // PAUSE STATE
        else if (display.gameState == display.pauseState) {

            pauseState(code);
        }

        // DIALOGUE STATE
        else if (display.gameState == display.dialogueState) {

            dialogueState(code);
        }

        // CHARACTER STATE
        else if (display.gameState == display.characterState) {

            characterState(code);
        }
    }

    public void menuState(int code) {

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {

            display.userInterface.menuTargetNumber--;

            if (display.userInterface.menuTargetNumber < 0) {

                display.userInterface.menuTargetNumber = 2;
            }
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {

            display.userInterface.menuTargetNumber++;

            if (display.userInterface.menuTargetNumber > 2) {

                display.userInterface.menuTargetNumber = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {

            if (display.userInterface.menuTargetNumber == 0) {

                display.gameState = display.playState;
            }
            if (display.userInterface.menuTargetNumber == 1) {

                // NOT IMPLEMENTED YET
            }
            if (display.userInterface.menuTargetNumber == 2) {

                System.exit(0);
            }
        }
    }

    public void playState(int code) {

        // MOVING
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_P) {
            display.gameState = display.pauseState;
        }

        if (code == KeyEvent.VK_E) {
            ePressed = true;
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if (code == KeyEvent.VK_I) {
            display.gameState = display.characterState;
        }

        // DEBUG
        if (code == KeyEvent.VK_T) {
            if (!showDebugText) {
                showDebugText = true;
            } else if (showDebugText) {
                showDebugText = false;
            }
        }
        if (code == KeyEvent.VK_R) {

            display.tileManager.loadMap("/maps/worldmap_template2.txt");
        }

        // // OPEN MENU
        // if (code == KeyEvent.VK_ESCAPE){

        // display.gameState = display.menuState;
        // }
    }

    public void pauseState(int code) {

        if (code == KeyEvent.VK_P) {

            display.gameState = display.playState;
        }
    }

    public void dialogueState(int code) {

        if (code == KeyEvent.VK_E) {

            display.gameState = display.playState;
        }
    }

    public void characterState(int code) {

        if (code == KeyEvent.VK_I) {

            display.gameState = display.playState;
        }

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {

            if (display.userInterface.slotRow > 0) {

                display.userInterface.slotRow--;
            }
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {

            if (display.userInterface.slotCol > 0) {

                display.userInterface.slotCol--;
            }
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {

            if (display.userInterface.slotRow < 5) {

                display.userInterface.slotRow++;
            }
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {

            if (display.userInterface.slotCol < 6) {

                display.userInterface.slotCol++;
            }
        }

        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_E) {

            display.hero.selectItem();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        // if (code == KeyEvent.VK_SPACE) {
        // spacePressed = false;
        // }
    }

}
