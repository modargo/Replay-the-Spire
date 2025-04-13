package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RP_LetterOpenerPower extends AbstractPower
{
    public static final String POWER_ID = "RP_LetterOpenerPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int damage;
	
    
    public RP_LetterOpenerPower(final AbstractCreature owner) {
        this.name = RP_LetterOpenerPower.NAME;
        this.ID = "RP_LetterOpenerPower";
        this.owner = owner;
        this.amount = 3;
		this.damage = 5;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/riposte2.png");
    }
    
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = RP_LetterOpenerPower.DESCRIPTIONS[0] + this.amount + RP_LetterOpenerPower.DESCRIPTIONS[1] + this.damage + RP_LetterOpenerPower.DESCRIPTIONS[2];
        }
        else {
            this.description = RP_LetterOpenerPower.DESCRIPTIONS[0] + this.amount + RP_LetterOpenerPower.DESCRIPTIONS[3] + this.damage + RP_LetterOpenerPower.DESCRIPTIONS[2];
        }
    }
    
    @Override
    public void atStartOfTurn() {
        this.amount = 3;
    }
	
    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.damage += 5;
        this.updateDescription();
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
		if (card.type == AbstractCard.CardType.SKILL) {
			--this.amount;
			if (this.amount == 0) {
				this.flash();
				this.amount = 3;
				AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			}
			this.updateDescription();
		}
    }
	
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_LetterOpenerPower");
        NAME = RP_LetterOpenerPower.powerStrings.NAME;
        DESCRIPTIONS = RP_LetterOpenerPower.powerStrings.DESCRIPTIONS;
    }
}
