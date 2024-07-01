/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.datagen.book.categories;

import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import lombok.SneakyThrows;

import java.util.List;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import org.apache.commons.lang3.tuple.Pair;

import robaertschi.environmenttech.datagen.book.categories.entries.EnvCollectorEntry;
import robaertschi.environmenttech.datagen.book.categories.entries.MDEntry;
import robaertschi.environmenttech.datagen.book.categories.test.TestEntry;
import robaertschi.environmenttech.level.item.ETItems;

public class FirstCategory extends CategoryProvider {
    public static final String ID = "the_beginning";

    public FirstCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "_____________________",
                "__________x_y________",
                "_____________________",
                "_____________________"
        };
    }

    @SneakyThrows
    @Override
    protected void generateEntries() {
//        var test = this.add();
        var collector = this.add(new EnvCollectorEntry(this).generate('x'));
        var mdEntry = this.add(new MDEntry(this,
                BookIconModel.create(ETItems.ENVIRONMENTAL_ESSENCE_ITEM),
                "essence",
                "Environmental Essence",
                "The most important resource in the mod",
                List.of(Pair.of("essence.md", "Environmental Essence"))).generate('y')).withParent(collector);
    }


    @Override
    protected String categoryName() {
        return "The Beginning";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(ETItems.ENV_COLLECTOR_BLOCK_ITEM.get());
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
