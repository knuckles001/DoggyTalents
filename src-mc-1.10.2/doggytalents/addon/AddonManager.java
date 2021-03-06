package doggytalents.addon;

import doggytalents.addon.biomesoplenty.BiomesOPlentyAddon;
import doggytalents.addon.botania.BotaniaAddon;
import doggytalents.addon.forestry.ForestryAddon;
import doggytalents.addon.itemphysic.ItemPhysicAddon;
import doggytalents.addon.natura.NaturaAddon;
import doggytalents.addon.terraqueous.TerraqueousAddon;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventBus;

/**
 * @author ProPercivalalb
 */
public class AddonManager {

	private static final EventBus EVENT_BUS	= new EventBus();
	
	public static void registerAddons() {
		EVENT_BUS.register(new ForestryAddon());
		EVENT_BUS.register(new ItemPhysicAddon());
		EVENT_BUS.register(new BiomesOPlentyAddon());
		EVENT_BUS.register(new TerraqueousAddon());
		EVENT_BUS.register(new NaturaAddon());
		EVENT_BUS.register(new BotaniaAddon());
	}

	public static void runRegisteredAddons(Configuration config) {
		EVENT_BUS.post(new AddonEvent.Pre(config));
		EVENT_BUS.post(new AddonEvent.Init(config));
		EVENT_BUS.post(new AddonEvent.Post(config));
	}
	
	public static boolean areModsLoaded(String... modIds) {
		for(String modId : modIds)
			if(!Loader.isModLoaded(modId))
				return false;
		return true;
	}
}
