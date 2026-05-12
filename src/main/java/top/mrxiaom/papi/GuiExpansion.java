package top.mrxiaom.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GuiExpansion extends PlaceholderExpansion implements Configurable {
    private String imagePlaceholder;
    private String shiftPlaceholder;
    private int reverseShiftValue;

    @Override
    public boolean register() {
        this.imagePlaceholder = getString("image-placeholder", "img_");
        this.shiftPlaceholder = getString("shift-placeholder", "img_offset_");
        this.reverseShiftValue = getBoolean("reverse-shift-value", true) ? -1 : 1;

        return super.register();
    }

    @Override
    public Map<String, Object> getDefaults() {
        Map<String, Object> defaults = new HashMap<>();
        if (isPresent("net.momirealms.craftengine.bukkit.api.CraftEngineImages")) {
            defaults.put("image-placeholder", "image_mm_");
            defaults.put("shift-placeholder", "shift_mm_");
        } else if (isPresent("dev.lone.itemsadder.api.ItemsAdder")) {
            defaults.put("image-placeholder", "img_");
            defaults.put("shift-placeholder", "img_offset_");
        }
        defaults.put("reverse-shift-value", true);
        return defaults;
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return "gui";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "MrXiaoM";
    }

    @NotNull
    @Override
    public String getVersion() {
        return "1.0.1";
    }

    public String image(@NotNull String name, @Nullable String suffix) {
        StringBuilder sb = new StringBuilder();
        image(sb, name, suffix);
        return sb.toString();
    }

    public void image(@NotNull StringBuilder sb, @NotNull String name, @Nullable String suffix) {
        sb.append("%");
        sb.append(imagePlaceholder);
        sb.append(name);
        if (suffix != null) sb.append(suffix);
        sb.append("%");
    }

    public String shift(int value) {
        StringBuilder sb = new StringBuilder();
        shift(sb, value);
        return sb.toString();
    }

    public void shift(@NotNull StringBuilder sb, int value) {
        if (value != 0) {
            sb.append("%");
            sb.append(shiftPlaceholder);
            sb.append(value);
            sb.append("%");
        }
    }

    @Override
    public String onRequest(OfflinePlayer p, @NotNull String params) {
        String[] split = params.split(",");
        if (split.length < 2) return "MISSING PARAMS";
        String name = split[0];
        try {
            int topOffset = split.length >= 3 ? (Integer.parseInt(split[2]) * reverseShiftValue) : 0;
            int bottomOffset = (Integer.parseInt(split[1]) * reverseShiftValue);
            int extraOffset = split.length >= 4 ? (Integer.parseInt(split[3]) * reverseShiftValue) : 0;
            int connectOffset = split.length >= 5 ? (Integer.parseInt(split[4]) * reverseShiftValue) : 0;
            StringBuilder sb = new StringBuilder();

            shift(sb, topOffset);
            image(sb, name, "_1");
            shift(sb, connectOffset);
            image(sb, name, "_2");

            shift(sb, bottomOffset);
            image(sb, name, "_3");
            shift(sb, connectOffset);
            image(sb, name, "_4");

            shift(sb, extraOffset);

            return PlaceholderAPI.setPlaceholders(p, sb.toString());
        } catch (NumberFormatException e) {
            return "WRONG NUMBER";
        }
    }

    private static boolean isPresent(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }
}
