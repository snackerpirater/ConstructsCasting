package com.snackpirate.constructscasting.items.book;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.world.item.ItemStack;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.BookScreenOpener;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.item.AbstractBookItem;

public class ArtificersGuideItem extends AbstractBookItem {
	public static final BookData ARTIFICERS_GUIDE = BookLoader.registerBook(ConstructsCasting.id("artificers_guide"), false, false);
	public ArtificersGuideItem(Properties properties) {
		super(properties);
	}

	@Override
	public BookScreenOpener getBook(ItemStack stack) {
		return ARTIFICERS_GUIDE;
	}
}
