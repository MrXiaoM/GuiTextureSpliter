package top.mrxiaom.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class GuiExpansion extends PlaceholderExpansion {
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
                sb.append("%img_offset_").append(topOffset).append("%");
            }
            sb.append("%img_").append(name).append("_1%");
            if (connectOffset != 0) {
                sb.append("%img_offset_").append(connectOffset).append("%");
            }
            sb.append("%img_").append(name).append("_2%");
            if (bottomOffset != 0) {
                sb.append("%img_offset_").append(bottomOffset).append("%");
            }
            sb.append("%img_").append(name).append("_3%");
            if (connectOffset != 0) {
                sb.append("%img_offset_").append(connectOffset).append("%");
            }
            sb.append("%img_").append(name).append("_4%");
            if (extraOffset != 0) {
                sb.append("%img_offset_").append(extraOffset).append("%");
            }
            return PlaceholderAPI.setPlaceholders(p, sb.toString());
        } catch (NumberFormatException e) {
            return "WRONG NUMBER";
        }
    }
}
