/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.datagen.book;

import java.util.function.BiConsumer;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;

import net.minecraft.resources.ResourceLocation;

import robaertschi.environmenttech.datagen.book.categories.FirstCategory;

public class DocsBook extends SingleBookSubProvider {
    public static final String ID = "introduction";

    public DocsBook(String modId, BiConsumer<String, String> defaultLang) {
        super(ID, modId, defaultLang);
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {

        return book.withModel(ResourceLocation.parse("modonomicon:modonomicon_green"))
                .withBookTextOffsetX(5)
                .withBookTextOffsetY(0)
                .withBookTextOffsetWidth(-5);
    }

    @Override
    protected void registerDefaultMacros() {

    }

    @Override
    protected void generateCategories() {
        var testCategory = this.add(new FirstCategory(this).generate());
    }

    @Override
    protected String bookName() {
        return "An introduction to EnvironmentTech";
    }

    @Override
    protected String bookTooltip() {
        return "A book for beginners to get started with EnvironmentTech.";
    }
}
