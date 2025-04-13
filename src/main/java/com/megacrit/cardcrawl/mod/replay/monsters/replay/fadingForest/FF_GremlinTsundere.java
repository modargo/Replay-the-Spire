package com.megacrit.cardcrawl.mod.replay.monsters.replay.fadingForest;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

public class FF_GremlinTsundere extends AbstractMonster
{
    public static final String ID = "FF_GremlinTsundere";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 24;
    private static final int A_2_HP_MIN = 28;
    private static final int BLOCK_AMOUNT = 7;
    private static final int A_2_BLOCK_AMOUNT = 8;
    private static final int BASH_DAMAGE = 6;
    private static final int A_2_BASH_DAMAGE = 8;
    private int blockAmt;
    private int bashDmg;
    private static final byte PROTECT = 1;
    private static final byte BASH = 2;
    
    public FF_GremlinTsundere(final float x, final float y) {
        super(FF_GremlinTsundere.NAME, "FF_GremlinTsundere", 15, 0.0f, 0.0f, 120.0f, 200.0f, null, x, y);
        this.dialogY = 60.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP_MIN);
            this.blockAmt = A_2_BLOCK_AMOUNT;
        }
        else {
            this.setHp(HP_MIN);
            this.blockAmt = BLOCK_AMOUNT;
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.bashDmg = 8;
        }
        else {
            this.bashDmg = 6;
        }
        this.damage.add(new DamageInfo(this, this.bashDmg));
        this.tint.color = FadingForestBoss.tintColor.cpy();
        this.loadAnimation("images/monsters/theBottom/femaleGremlin/skeleton.atlas", "images/monsters/theBottom/femaleGremlin/skeleton.json", 1.0f);
        this.tint.changeColor(FadingForestBoss.tintColor.cpy());
        //this.loadAnimation("images/monsters/fadingForest/femaleGremlin/skeleton.atlas", "images/monsters/fadingForest/femaleGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new GainBlockRandomMonsterAction(this, this.blockAmt));
                AbstractDungeon.actionManager.addToBottom(new GainBlockRandomMonsterAction(this, this.blockAmt));
                int aliveCount = 0;
                for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDying && !m.isEscaping) {
                        ++aliveCount;
                    }
                }
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                    break;
                }
                if (aliveCount > 1) {
                    this.setMove(FF_GremlinTsundere.MOVES[0], (byte)1, Intent.DEFEND);
                    break;
                }
                this.setMove(FF_GremlinTsundere.MOVES[1], (byte)2, Intent.ATTACK, this.damage.get(0).base);
                break;
            }
            case 2: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, FF_GremlinTsundere.MOVES[1], (byte)2, Intent.ATTACK, this.damage.get(0).base));
                break;
            }
            case 99: {
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5f, FF_GremlinTsundere.DIALOG[1], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                break;
            }
        }
    }
    
    @Override
    public void die() {
        super.die();
    }
    
    @Override
    public void escapeNext() {
        if (!this.cannotEscape && !this.escapeNext) {
            this.escapeNext = true;
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0f, FF_GremlinTsundere.DIALOG[2], false));
        }
    }
    
    @Override
    protected void getMove(final int num) {
        this.setMove(FF_GremlinTsundere.MOVES[0], (byte)1, Intent.DEFEND);
    }
    
    @Override
    public void deathReact() {
        if (this.intent != Intent.ESCAPE && !this.isDying) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0f, FF_GremlinTsundere.DIALOG[2], false));
            this.setMove((byte)99, Intent.ESCAPE);
            this.createIntent();
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinTsundere");
        NAME = "False " + FF_GremlinTsundere.monsterStrings.NAME;
        MOVES = FF_GremlinTsundere.monsterStrings.MOVES;
        DIALOG = FF_GremlinTsundere.monsterStrings.DIALOG;
    }
}
