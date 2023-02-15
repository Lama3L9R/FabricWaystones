package wraith.fwaystones.util;

import eu.pb4.polymer.api.item.PolymerItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import wraith.fwaystones.registry.BlockRegistry;
import net.minecraft.text.Text;

public class CustomItemGroup {
    public static final ItemGroup WAYSTONE_GROUP = PolymerItemGroup.create(Utils.ID("waystones"), Text.translatable("itemGroup.fwaystones.waystones"), () -> new ItemStack(BlockRegistry.WAYSTONE));

}
