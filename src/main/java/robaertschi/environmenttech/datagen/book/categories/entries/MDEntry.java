/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.datagen.book.categories.entries;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import org.apache.commons.lang3.tuple.Pair;

import static robaertschi.environmenttech.ET.MODID;

public class MDEntry extends EntryProvider {
    public final String id;
    public final BookIconModel iconModel;
    public final String name;
    public final String description;
    public final List<Page> pages;
    public final List<BookPageModel<?>> otherPages;


    public MDEntry(CategoryProviderBase parent,
                   BookIconModel iconModel,
                   String id,
                   String name,
                   String description,
                   List<Pair<String, String>> pages
    ) throws IOException {
        this(parent, iconModel, id, name, description, pages, List.of());
    }

    /**
     * The markdown files should be in assets/environmenttech/entries/$id/[name]
     * @param parent Parent
     * @param id ID for the entry
     * @param pages A Pair, first is the markdown file name, second is the page title.
     * @throws IOException When transferring from the file to a String fails.
     */
    public MDEntry(CategoryProviderBase parent,
                   BookIconModel iconModel,
                   String id,
                   String name,
                   String description,
                   List<Pair<String, String>> pages,
                   List<BookPageModel<?>> otherPages
    ) throws IOException {
        super(parent);
        this.id = id;
        this.iconModel = iconModel;
        this.name = name;
        this.description = description;
        this.pages = new ArrayList<>();
        this.otherPages = otherPages;

        for (var page : pages) {
//            ResourceLocation.fromNamespaceAndPath(MODID, page.getLeft()).withPrefix("entries/"+id+"/")
            try(var stream = getClass().getProtectionDomain().getClassLoader().getResourceAsStream("assets/"+MODID+"/entries/" + id + "/" + page.getLeft())) {
                assert stream != null;
                var outputStream = new ByteArrayOutputStream();
                stream.transferTo(outputStream);
                this.pages.add(new Page(page.getLeft(), page.getRight(), outputStream.toString()));
            }
        }

    }

    @Override
    protected void generatePages() {
        for (var page : pages) {
            this.page(page.filename(), () -> BookTextPageModel.create()
                    .withText(this.context.pageText())
                    .withTitle(this.context.pageTitle()));

            this.pageText(page.md());
            this.pageTitle(page.title());
        }
    }

    @Override
    protected String entryName() {
        return name;
    }

    @Override
    protected String entryDescription() {
        return description;
    }

    @Override
    protected com.mojang.datafixers.util.Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return iconModel;
    }

    @Override
    protected String entryId() {
        return id;
    }

    public record Page(String filename, String title, String md) {}
}
