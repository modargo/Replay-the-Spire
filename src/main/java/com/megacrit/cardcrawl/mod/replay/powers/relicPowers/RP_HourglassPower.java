package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RP_HourglassPower extends AbstractPower
{
    public static final String POWER_ID = "RP_HourglassPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_HourglassPower(final AbstractCreature owner) {
		this(owner, 3);
	}
    public RP_HourglassPower(final AbstractCreature owner, final int newAmount) {
        this.name = RP_HourglassPower.NAME;
        this.ID = "RP_HourglassPower";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
		this.img = ImageMaster.loadImage("images/powers/32/countdown.png");
        //this.loadRegion("time");
    }
    
    @Override
    public void updateDescription() {
    	if (this.owner.isPlayer) {
    		this.description = RP_HourglassPower.DESCRIPTIONS[0] + this.amount + RP_HourglassPower.DESCRIPTIONS[1];
    	} else {
    		this.description = RP_HourglassPower.DESCRIPTIONS[2] + this.owner.name + RP_HourglassPower.DESCRIPTIONS[3] + this.amount + RP_HourglassPower.DESCRIPTIONS[4];
    	}
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            if (this.owner.isPlayer) {
            	AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            } else {
            	AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_HourglassPower");
        NAME = RP_HourglassPower.powerStrings.NAME;
        DESCRIPTIONS = RP_HourglassPower.powerStrings.DESCRIPTIONS;
    }
}
