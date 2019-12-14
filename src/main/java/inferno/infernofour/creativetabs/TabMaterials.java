package inferno.infernofour.creativetabs;

import inferno.infernofour.items.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabMaterials extends CreativeTabs {
    public TabMaterials(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.steelIngot);
    }
}
