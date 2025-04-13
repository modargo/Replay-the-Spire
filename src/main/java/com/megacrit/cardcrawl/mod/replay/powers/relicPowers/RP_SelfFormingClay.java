package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class RP_SelfFormingClay extends AbstractPower
{
    public static final String POWER_ID = "Malleable";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_SelfFormingClay(final AbstractCreature owner) {
    	this(owner, 3);
    }
    
    public RP_SelfFormingClay(final AbstractCreature owner, final int amount) {
        this.name = RP_SelfFormingClay.NAME;
        this.ID = "Malleable";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/malleable.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_SelfFormingClay.DESCRIPTIONS[0] + this.amount + RP_SelfFormingClay.DESCRIPTIONS[1];
    }
    
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner, 3, this.name), 3));
            this.updateDescription();
        }
        return damageAmount;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_SelfFormingClay");
        NAME = RP_SelfFormingClay.powerStrings.NAME;
        DESCRIPTIONS = RP_SelfFormingClay.powerStrings.DESCRIPTIONS;
    }
}