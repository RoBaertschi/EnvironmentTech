/*
 *  EnvironmentTech MC Mod
    Copyright (C) 2024 Robin Bärtschi and Contributors

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, by version 3 of the License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package robaertschi.environmenttech.compat.emi;

import java.util.List;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import org.apache.commons.compress.utils.Lists;

import net.minecraft.world.inventory.Slot;

import robaertschi.environmenttech.menu.EnvCollectorMenu;

public class EnvCollectorEmiRecipeHandler implements StandardRecipeHandler<EnvCollectorMenu> {
    @Override
    public List<Slot> getInputSources(EnvCollectorMenu handler) {
        List<Slot> list = Lists.newArrayList();
        list.add(handler.getSlot(0));
        int invStart = 2;
        for (int i = invStart; i < invStart + 36; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(EnvCollectorMenu handler) {
        return List.of(handler.getSlot(0));
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == EmiCompatPlugin.ENV_COLLECTOR_CATEGORY && recipe.supportsRecipeTree();
    }
}
