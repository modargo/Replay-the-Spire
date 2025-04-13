package replayTheSpire;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.SpreadingInfection;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class ReplayTheSpireMod implements PostInitializeSubscriber, EditStringsSubscriber, EditCardsSubscriber {
	public static final Logger logger = LogManager.getLogger(ReplayTheSpireMod.class.getName());
	public static TextureAtlas powerAtlas;

	private static final String modID = "telleroftales";
	private static final String MODNAME = "Teller of Tales";
    private static final String AUTHOR = "The_Evil_Pickle, AstroPenguin642, Bakuhaku, Slimer509, Stewartisme, modargo";
    private static final String DESCRIPTION = "The Teller of Tales from Replay the Spire";
	
	public static final String BADGE_IMG = "img/replay/ModBadge.png";

	public static boolean renderForestBG = false;
	public static Texture forestBG;
	public static boolean noSkipRewardsRoom;

	public ReplayTheSpireMod() {
		BaseMod.subscribe(this);
	}

    
	public static void initialize() {
		new ReplayTheSpireMod();
		ReplayTheSpireMod.forestBG = ImageMaster.loadImage("images/monsters/fadingForest/fadingForest_bg.png");
    }

	public static ModPanel settingsPanel;

	@Override
    public void receivePostInitialize() {
		ReplayTheSpireMod.powerAtlas = new TextureAtlas(Gdx.files.internal("powers/replayPowers.atlas"));
		ReplayTheSpireMod.forestBG = ImageMaster.loadImage("images/monsters/fadingForest/fadingForest_bg.png");
		InitializeMonsters();
		
        // Mod badge
        Texture badgeTexture = new Texture(BADGE_IMG);
        settingsPanel = new ModPanel();
		
		logger.info("badge");
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
		logger.info("Events");
    }
	private void InitializeMonsters() {
        BaseMod.addMonster("Fading Forest", () -> new MonsterGroup(new AbstractMonster[] {new FadingForestBoss()}));
		BaseMod.addBoss("Exordium", "Fading Forest", "images/ui/map/boss/FableSpinner.png", "images/ui/map/bossOutline/FableSpinner.png");
	}
	
	private void editStringsByLang(String jsonPath) {
        String cardStrings = Gdx.files.internal(jsonPath + "ReplayCardStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        String powerStrings = Gdx.files.internal(jsonPath + "ReplayPowerStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

        String eventStringsF = Gdx.files.internal(jsonPath + "FadingForestEventStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStringsF);

        String uiStrings = Gdx.files.internal(jsonPath + "ReplayUIStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);

        String mStrings = Gdx.files.internal(jsonPath + "ReplayMonsterStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(MonsterStrings.class, mStrings);
	}
	
	@Override
	public void receiveEditStrings() {
		String jsonPath = "localization/";
		editStringsByLang(jsonPath);
		if (Settings.language.toString().equals("RUS")) {
			logger.info("Russian detected!");
			jsonPath = "localization/rus/";
			editStringsByLang(jsonPath);
		} else if (Settings.language.toString().equals("KOR")) {
			logger.info("Korean detected!");
			jsonPath = "localization/kor/";
			editStringsByLang(jsonPath);
		} else if (Settings.language.toString().equals("ZHS")) {
			logger.info("Chinese detected!");
			jsonPath = "localization/zhs/";
			editStringsByLang(jsonPath);
		}
	}

	@Override
	public void receiveEditCards() {
		new AutoAdd(modID)
				.packageFilter(SpreadingInfection.class)
				.setDefaultSeen(true)
				.cards();
	}
}