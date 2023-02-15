package wraith.fwaystones.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class VoidTotem extends LocalVoidItem {

    public VoidTotem(Settings settings) {
        super(settings);
        this.canTeleport = false;
        this.translationName = "void_totem";
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.TOTEM_OF_UNDYING;
    }
}
