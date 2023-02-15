package wraith.fwaystones.gui;

import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.AnvilInputGui;
import eu.pb4.sgui.api.gui.SimpleGui;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import wraith.fwaystones.FabricWaystones;
import wraith.fwaystones.block.WaystoneBlockEntity;
import wraith.fwaystones.util.Config;

public class WaystoneSettingsGui extends SimpleGui {
    private final WaystoneBlockEntity waystone;

    public WaystoneSettingsGui(ServerPlayerEntity player, WaystoneBlockEntity waystone) {
        super(ScreenHandlerType.HOPPER, player, false);
        this.setTitle(Text.translatable("polyport.waystones.settings"));
        this.waystone = waystone;
    }

    @Override
    public void onTick() {
         if (waystone.isRemoved()) {
             this.close();
         }
    }

    protected void updateDisplay() {
        int i = 0;

        if (Config.getInstance().canPlayersToggleGlobal() || Permissions.check(this.player, "waystones.set_global", 3)) {
            this.setSlot(i++, new GuiElementBuilder(waystone.isGlobal() ? Items.ENDER_EYE : Items.ENDER_PEARL)
                    .setName(Text.translatable("fwaystones.config.global"))
                    .setCallback((x, y, z) -> {
                        FabricWaystones.WAYSTONE_STORAGE.toggleGlobal(waystone.getHash());
                        PagedGui.playClickSound(this.player);
                        this.updateDisplay();
                    })
            );
        }

        this.setSlot(i++, new GuiElementBuilder(Items.SKELETON_SKULL)
                .setName(Text.translatable("fwaystones.config.tooltip.revoke_ownership").formatted(Formatting.WHITE))
                .setCallback((x, y, z) -> {
                    FabricWaystones.WAYSTONE_STORAGE.setOwner(waystone.getHash(), null);
                    PagedGui.playClickSound(this.player);
                    this.close();
                })
        );

        this.setSlot(i++, new GuiElementBuilder(Items.NAME_TAG)
                .setName(Text.translatable("polyport.waystones.rename").formatted(Formatting.WHITE))
                .setCallback((x, y, z) -> {
                    PagedGui.playClickSound(this.player);
                    openRenaming(this.player, this.waystone);
                })
        );

        this.setSlot(4, new GuiElementBuilder(Items.BARRIER)
                .setName(Text.translatable("gui.back"))
                .setCallback((x, y, z) -> {
                    PagedGui.playClickSound(this.player);
                    UniversalWaystoneGui.open(this.player, this.waystone);
                })
        );
    }

    private static void openRenaming(ServerPlayerEntity player, WaystoneBlockEntity waystone) {
        var ui = new AnvilInputGui(player, false);
        ui.setTitle(Text.translatable("polyport.waystones.rename"));
        ui.setDefaultInputValue(waystone.getWaystoneName());

        ui.setSlot(1,
                new GuiElementBuilder(Items.SLIME_BALL)
                        .setName(Text.translatable("fwaystones.config.tooltip.set_name").formatted(Formatting.GREEN))
                        .setCallback((index, clickType, actionType) -> {
                            PagedGui.playClickSound(player);
                            FabricWaystones.WAYSTONE_STORAGE.renameWaystone(waystone.getHash(), ui.getInput());
                            ui.close();
                        })
        );

        ui.setSlot(2,
                new GuiElementBuilder(Items.BARRIER)
                        .setName(Text.translatable("gui.back").formatted(Formatting.RED))
                        .setCallback((index, clickType, actionType) -> {
                            PagedGui.playClickSound(player);
                            WaystoneSettingsGui.open(player, waystone);
                        })
        );

        ui.open();
    }


    public static void open(ServerPlayerEntity user, WaystoneBlockEntity waystone) {
        var gui = new WaystoneSettingsGui(user, waystone);
        gui.updateDisplay();
        gui.open();
    }
}
