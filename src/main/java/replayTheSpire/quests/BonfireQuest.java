package replayTheSpire.quests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.Durian;

import infinitespire.InfiniteSpire;
import infinitespire.abstracts.Quest;
import infinitespire.abstracts.Quest.QuestRarity;
import infinitespire.abstracts.Quest.QuestType;
import replayTheSpire.ReplayTheSpireMod;

public class BonfireQuest extends Quest{
    private static final Color COLOR;
    private static final int REWARD_AMOUNT = 1;

	public BonfireQuest() {
		super(BonfireQuest.class.getName(), BonfireQuest.COLOR, 1, QuestType.BLUE, (ReplayTheSpireMod.SETTING_ROOMS_BONFIRE.value <= 20) ? QuestRarity.SPECIAL : (QuestRarity.RARE));
	}
	
    @Override
    public Texture getTexture() {
        Texture texture = InfiniteSpire.getTexture("images/ui/replay/quest/bonfire.png");
        return texture;
    }

	@Override
	public Quest createNew() {
		return this;
	}

	@Override
	public Quest getCopy() {
		return new BonfireQuest();
	}

	@Override
	public String getRewardString() {
		return this.voidShardStrings.TEXT[2] + REWARD_AMOUNT + this.voidShardStrings.TEXT[4];
	}

	@Override
	public String getTitle() {
		return "Visit a Bonfire room.";
	}

	@Override
	public void giveReward() {
		InfiniteSpire.gainVoidShards(REWARD_AMOUNT);
	}

    static {
        COLOR = new Color(0.25f, 0.25f, 1.0f, 1.0f);
    }
}
