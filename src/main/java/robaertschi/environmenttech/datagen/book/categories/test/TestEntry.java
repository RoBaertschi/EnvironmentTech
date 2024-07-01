/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.datagen.book.categories.test;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;

import com.mojang.datafixers.util.Pair;

import robaertschi.environmenttech.level.item.ETItems;

public class TestEntry extends EntryProvider {
    public static final String ID = "test_entry";

    public TestEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("page1", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Test Entry");
        this.pageText("""
                **Bold**      \s
                *Italics*     \s
                ++Underlined++
                """);


        this.page("page2", () -> BookTextPageModel.create().withText(this.context().pageText()));
        this.pageText("""
                ~~Stricken~~\s
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
