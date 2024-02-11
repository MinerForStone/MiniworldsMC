package com.minerforstone.miniworlds.registry;

import com.minerforstone.miniworlds.Miniworlds;

import java.util.ArrayList;
import java.util.List;

public class Registry<T> {
    private final List<Class<? extends T>> ts = new ArrayList<>();
    private final List<Integer> ids = new ArrayList<>();
    private final List<String> names = new ArrayList<>();

    public void register(Class<? extends T> type, int id, String name) {
        if (ts.contains(type)) {
            Miniworlds.getPlugin().getLogger().warning("Duplicate registry entry, ignoring");
            return;
        }

        ts.add(type);
        ids.add(id);
        names.add(name);
    }

    public int getId(Class<? extends T> t) throws IndexOutOfBoundsException {
        return ids.get(ts.indexOf(t));
    }

    public Class<? extends T> get(int id) throws IndexOutOfBoundsException {
        return ts.get(ids.indexOf(id));
    }

    public String getName(Class<? extends T> t) throws IndexOutOfBoundsException {
        return names.get(ts.indexOf(t));
    }

    public String getName(int id) throws IndexOutOfBoundsException {
        return names.get(ids.indexOf(id));
    }

    public int getRegistrySize() {
        return ts.size();
    }
}
