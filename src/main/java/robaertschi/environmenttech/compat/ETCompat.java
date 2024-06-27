/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
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
