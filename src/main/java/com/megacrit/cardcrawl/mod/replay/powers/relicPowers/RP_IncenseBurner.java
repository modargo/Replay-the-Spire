package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;

public class RP_IncenseBurner extends AbstractPower
{
    public static final String POWER_ID = "RP_IncenseBurner";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_IncenseBurner(final AbstractCreature owner) {
        this.name = RP_IncenseBurner.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 6;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/ghost.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_IncenseBurner.DESCRIPTIONS[0];
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.amount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
			this.amount--;
			if (this.amount <= 0) {
				this.amount = 6;
	            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new IntangiblePower(this.owner, 1), 1));
			}
			this.updateDescription();
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = RP_IncenseBurner.powerStrings.NAME;
        DESCRIPTIONS = RP_IncenseBurner.powerStrings.DESCRIPTIONS;
    }
}
