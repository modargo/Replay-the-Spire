package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class LoomingEvil
  extends AbstractCard
{
  public static final String ID = "Looming Evil";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Looming Evil");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = 3;
  private static final int POOL = 2;
  
  public LoomingEvil()
  {
    super("Looming Evil", NAME, "status/beta", "status/beta", 3, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE, 2);
	
	this.baseMagicNumber = this.cost;
    this.magicNumber = this.baseMagicNumber;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    if (!this.dontTriggerOnUseCard)
    {
	  if (p.hasRelic("Blue Candle"))
	  {
        useBlueCandle(p);
	  }
	  else
	  {
		  this.exhaust = true;
	  }
    }
    else
    {
      //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, true), 1));
      AbstractCard c = AbstractDungeon.returnTrulyRandomCard(AbstractCard.CardType.CURSE, AbstractDungeon.cardRandomRng).makeCopy();
      AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
      AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
	  if (this.cost > 0)
	  {
		upgradeBaseCost(this.cost - 1);
		this.magicNumber = this.cost;
	  }
    }
  }
  
  public void triggerOnEndOfTurnForPlayingCard()
  {
    this.dontTriggerOnUseCard = true;
    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, null));
  }
  
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
  {
    if (cardPlayable(m))
    {
      if (hasEnoughEnergy()) {
        return true;
      }
      return false;
    }
    return false;
  }
  
  public AbstractCard makeCopy()
  {
    return new LoomingEvil();
  }
  
  public void upgrade() {}
}
