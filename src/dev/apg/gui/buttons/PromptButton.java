package dev.apg.gui.buttons;

import dev.apg.Challenge;
import dev.apg.gui.DisplayUI;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PromptButton extends ClickableButton {
    public PromptButton() {
        super("Prompt", true);
        xPoints = new int[]{83, 457, 717, 672, 519, 149};
        yPoints = new int[]{32, 40, 20, 191, 185, 199};
        buttonDimensions = new Polygon(xPoints,yPoints, 6);
        key1 = KeyEvent.VK_UP;
        key2 = KeyEvent.VK_W;
    }
    @Override
    protected void doTheThing() {
        int max = Challenge.challengesList.size() - 1;
        if(Challenge.challengeIndex++ >= max) {
            Challenge.challengeIndex = 0;
            System.out.println(Challenge.challengeIndex);
        }
        DisplayUI.promptButtonText = currentChallengeName;
        gui.refresh();

    }
}