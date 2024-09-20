package binnie.extratrees.carpentry;

import java.util.*;

import net.minecraft.item.ItemStack;

import binnie.extratrees.api.*;

public class CarpentryInterface implements ICarpentryInterface {

    protected static Map<Integer, IDesignMaterial> woodMap = new LinkedHashMap<>();
    protected static Map<Integer, IDesign> designMap = new LinkedHashMap<>();
    protected static Map<String, IDesignCategory> designCategories = new HashMap<>();

    @Override
    public boolean registerCarpentryWood(int index, IDesignMaterial wood) {
        return wood != null && CarpentryInterface.woodMap.put(index, wood) == null;
    }

    @Override
    public int getCarpentryWoodIndex(IDesignMaterial wood) {
        for (Integer integer : CarpentryInterface.woodMap.keySet()) {
            if (CarpentryInterface.woodMap.get(integer).equals(wood)) {
                return integer;
            }
        }
        return -1;
    }

    @Override
    public IDesignMaterial getWoodMaterial(int index) {
        return CarpentryInterface.woodMap.get(index);
    }

    @Override
    public boolean registerDesign(int index, IDesign wood) {
        return wood != null && CarpentryInterface.designMap.put(index, wood) == null;
    }

    @Override
    public int getDesignIndex(IDesign wood) {
        for (Integer integer : CarpentryInterface.designMap.keySet()) {
            if (CarpentryInterface.designMap.get(integer).equals(wood)) {
                return integer;
            }
        }
        return -1;
    }

    @Override
    public IDesign getDesign(int index) {
        return CarpentryInterface.designMap.get(index);
    }

    @Override
    public ILayout getLayout(IPattern pattern, boolean inverted) {
        return Layout.get(pattern, inverted);
    }

    @Override
    public boolean registerDesignCategory(IDesignCategory category) {
        return category != null && category.getId() != null
                && CarpentryInterface.designCategories.put(category.getId(), category) == null;
    }

    @Override
    public IDesignCategory getDesignCategory(String id) {
        return CarpentryInterface.designCategories.get(id);
    }

    @Override
    public Collection<IDesignCategory> getAllDesignCategories() {
        List<IDesignCategory> categories = new ArrayList<>();
        for (IDesignCategory category : CarpentryInterface.designCategories.values()) {
            if (!category.getDesigns().isEmpty()) {
                categories.add(category);
            }
        }
        return categories;
    }

    @Override
    public List<IDesign> getSortedDesigns() {
        List<IDesign> designs = new ArrayList<>();
        for (IDesignCategory category : getAllDesignCategories()) {
            designs.addAll(category.getDesigns());
        }
        return designs;
    }

    @Override
    public IDesignMaterial getWoodMaterial(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        for (Map.Entry<Integer, IDesignMaterial> entry : CarpentryInterface.woodMap.entrySet()) {
            ItemStack key = entry.getValue().getStack();
            if (key == null) {
                continue;
            }
            if (key.isItemEqual(stack)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
