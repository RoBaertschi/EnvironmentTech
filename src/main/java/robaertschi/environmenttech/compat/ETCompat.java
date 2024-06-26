package robaertschi.environmenttech.compat;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import robaertschi.environmenttech.compat.top.TopCompat;

public class ETCompat {
    public static void init(IEventBus modEventBus) {
        var mods = ModList.get();

        if (mods.isLoaded("theoneprobe")) {
            TopCompat.init(modEventBus);
        }
    }
}
