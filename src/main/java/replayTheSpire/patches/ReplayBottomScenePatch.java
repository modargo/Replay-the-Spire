package replayTheSpire.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ForestEventAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import replayTheSpire.ReplayTheSpireMod;

public class ReplayBottomScenePatch {
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "randomizeScene")
	public static class BottomSceneRandomizePatch {
		public static void Postfix(TheBottomScene __instance) {
            ReplayTheSpireMod.renderForestBG = false;//true;
        }
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "renderCombatRoomBg")
	public static class BottomSceneBGPatch {
		public static void Postfix(TheBottomScene __instance, final SpriteBatch sb) {
			if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom && ReplayTheSpireMod.renderForestBG && ReplayTheSpireMod.forestBG != null) {
				AbstractMonster fb = ForestEventAction.forest;
				if (fb != null && !fb.isDead && !fb.escaped) {
					sb.draw(ReplayTheSpireMod.forestBG, fb.drawX - ReplayTheSpireMod.forestBG.getWidth() * Settings.scale / 2.0f + fb.animX, fb.drawY + fb.animY + AbstractDungeon.sceneOffsetY, ReplayTheSpireMod.forestBG.getWidth() * Settings.scale, ReplayTheSpireMod.forestBG.getHeight() * Settings.scale, 0, 0, ReplayTheSpireMod.forestBG.getWidth(), ReplayTheSpireMod.forestBG.getHeight(), false, false);
				}
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "renderCombatRoomFg")
	public static class BottomSceneFGPatch {
		public static void Postfix(TheBottomScene __instance, final SpriteBatch sb) {
			if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom && ForestEventAction.forest != null) {
				ForestEventAction.forest.imageEventText.render(sb);
			}
		}
	}
	
}