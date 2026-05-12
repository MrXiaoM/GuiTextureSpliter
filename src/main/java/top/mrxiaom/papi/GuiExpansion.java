package top.mrxiaom.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GuiExpansion extends PlaceholderExpansion implements Configurable {
    private String imagePlaceholder;
    private String shiftPlaceholder;

    @Override
    public boolean register() {
        this.imagePlaceholder = getString("image-placeholder", "img_");
        this.shiftPlaceholder = getString("shift-placeholder", "img_offset_");

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
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, @NotNull String params) {
        String[] split = params.split(",");
        if (split.length < 2) return "MISSING PARAMS";
        String name = split[0];
        try {
            int topOffset = split.length >= 3 ? -Integer.parseInt(split[2]) : 0;
            int bottomOffset = -Integer.parseInt(split[1]);
            int extraOffset = split.length >= 4 ? -Integer.parseInt(split[3]) : 0;
            int connectOffset = split.length >= 5 ? -Integer.parseInt(split[4]) : 0;
            StringBuilder sb = new StringBuilder();
            if (topOffset != 0) {
                sb.append("%").append(shiftPlaceholder).append(topOffset).append("%");
            }
            sb.append("%").append(imagePlaceholder).append(name).append("_1%");
            if (connectOffset != 0) {
                sb.append("%").append(shiftPlaceholder).append(connectOffset).append("%");
            }
            sb.append("%").append(imagePlaceholder).append(name).append("_2%");
            if (bottomOffset != 0) {
                sb.append("%").append(shiftPlaceholder).append(bottomOffset).append("%");
            }
            sb.append("%").append(imagePlaceholder).append(name).append("_3%");
            if (connectOffset != 0) {
                sb.append("%").append(shiftPlaceholder).append(connectOffset).append("%");
            }
            sb.append("%").append(imagePlaceholder).append(name).append("_4%");
            if (extraOffset != 0) {
                sb.append("%").append(shiftPlaceholder).append(extraOffset).append("%");
            }
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
