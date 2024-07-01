/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.datagen.book.categories.entries;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import robaertschi.environmenttech.ET;
import robaertschi.environmenttech.level.item.ETItems;

public class EnvCollectorEntry extends EntryProvider {
    public static final String ID = "test_entry";

    public EnvCollectorEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("page1", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Your first steps!");
        this.pageText("""
                Hello and welcome to EnvironmentTech! \s
                This is a Tech Mod all about your Environment. \s
                
                This is a small Guide on how get started with the mod. \s
                But before you begin with anything, you should try to get some basic materials like Wood, Iron, Coal and a Furnace. \s
                Then you can continue with the next Page. \s
                """);


        this.page("page2", () -> BookCraftingRecipePageModel.create().withRecipeId1(ET.id("env_collector_block_item")).withText(this.context().pageText()));
        this.pageText("""
                This is the ENV Collector, he is required to get started with the mod.
                """);
    }

    @Override
    protected String entryName() {
        return "Test Entry";
    }

    @Override
    protected String entryDescription() {
        return "Test Description";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(ETItems.ENV_COLLECTOR_BLOCK_ITEM);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
