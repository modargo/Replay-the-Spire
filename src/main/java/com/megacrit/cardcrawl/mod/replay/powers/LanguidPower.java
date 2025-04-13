package com.megacrit.cardcrawl.mod.replay.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import replayTheSpire.ReplayTheSpireMod;

public class LanguidPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "LanguidPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justApplied;
    private static final int EFFECTIVENESS_STRING = 25;
    
    public LanguidPower(final AbstractCreature owner, final int amount, final boolean isSourceMonster) {
        this.justApplied = false;
        this.name = LanguidPower.NAME;
        this.ID = "LanguidPower";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("weak");
        if (isSourceMonster) {
            this.justApplied = true;
        }
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        //this.priority = 99;
    }

	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "LanguidPower"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "LanguidPower", 1));
        }
    }
    
    @Override
    public void updateDescription() {
        this.description = LanguidPower.DESCRIPTIONS[0] + this.owner.name + LanguidPower.DESCRIPTIONS[1] + this.amount + LanguidPower.DESCRIPTIONS[2] + this.amount + LanguidPower.DESCRIPTIONS[3];
    }
    
    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }
        return damage - this.amount;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("LanguidPower");
        NAME = LanguidPower.powerStrings.NAME;
        DESCRIPTIONS = LanguidPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new LanguidPower(owner, amount, justApplied);
	}
}