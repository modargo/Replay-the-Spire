package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "nextRoomTransition", paramtypez = {})
public class ReplayNextRoomPatch {
	public static void Prefix() {
		RenderHandPatch.plsDontRenderHand = false;
	}
}