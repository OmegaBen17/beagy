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
    boolean checkDrawTime = false;

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

            if (code == KeyEvent.VK_W) {

                display.userInterface.menuTargetNumber--;

                if (display.userInterface.menuTargetNumber < 0) {

                    display.userInterface.menuTargetNumber = 2;
                }
            }

            if (code == KeyEvent.VK_S) {

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

        // PLAY STATE
        else if (display.gameState == display.playState) {

            // MOVING
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
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

            // DEBUG
            if (code == KeyEvent.VK_T) {
                if (!checkDrawTime) {
                    checkDrawTime = true;
                } else if (checkDrawTime) {
                    checkDrawTime = false;
                }
            }

            // // OPEN MENU
            // if (code == KeyEvent.VK_ESCAPE){

            // display.gameState = display.menuState;
            // }
        }

        // PAUSE STATE
        else if (display.gameState == display.pauseState) {

            if (code == KeyEvent.VK_P) {

                display.gameState = display.playState;
            }
        }

        // DIALOGUE STATE
        else if (display.gameState == display.dialogueState) {

            if (code == KeyEvent.VK_E) {

                display.gameState = display.playState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        // if (code == KeyEvent.VK_SPACE) {
        // spacePressed = false;
        // }
    }

}
