package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import replayTheSpire.patches.ArrowheadPatches;

public class Arrowhead extends AbstractRelic
{
    public static final String ID = "Arrowhead";
    
    public Arrowhead() {
        super("Arrowhead", "arrowhead.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public AbstractRelic makeCopy() {
        return new Arrowhead();
    }
    
    @Override
    public void onSmith() {
    	if (ArrowheadPatches.didSecondUpgrade) {
    		AbstractDungeon.overlayMenu.cancelButton.hide();
    	}
    }
    
}
