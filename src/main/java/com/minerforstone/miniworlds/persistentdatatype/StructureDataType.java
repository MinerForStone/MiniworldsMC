package com.minerforstone.miniworlds.persistentdatatype;

import com.minerforstone.miniworlds.world.structures.Structure;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class StructureDataType implements PersistentDataType {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Structure> getComplexType() {
        return Structure.class;
    }

    @NotNull
    @Override
    public Object toPrimitive(@NotNull Object complex, @NotNull PersistentDataAdapterContext context) {
        return null;
    }

    @NotNull
    @Override
    public Object fromPrimitive(@NotNull Object primitive, @NotNull PersistentDataAdapterContext context) {
        return null;
    }
}
