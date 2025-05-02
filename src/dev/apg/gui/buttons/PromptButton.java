package dev.apg.gui.buttons;

import dev.apg.Challenge;
import dev.apg.gui.DisplayUI;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PromptButton extends ClickableButton {
    public PromptButton() {
        super("Prompt", new Rectangle(35,35,730,105), true);
        button1 = KeyEvent.VK_UP;
        button2 = KeyEvent.VK_W;
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