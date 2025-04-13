package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class RP_GiryaPower extends AbstractPower
{
    public static final String POWER_ID = "RP_GiryaPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_GiryaPower(final AbstractCreature owner) {
        this.name = RP_GiryaPower.NAME;
        this.ID = "RP_GiryaPower";
        this.owner = owner;
        this.amount = 3;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/formOfBull.png");
        //this.loadRegion("unawakened");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_GiryaPower.DESCRIPTIONS[0] + this.amount + RP_GiryaPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.amount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
			this.amount--;
			this.updateDescription();
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_GiryaPower");
        NAME = RP_GiryaPower.powerStrings.NAME;
        DESCRIPTIONS = RP_GiryaPower.powerStrings.DESCRIPTIONS;
    }
}
