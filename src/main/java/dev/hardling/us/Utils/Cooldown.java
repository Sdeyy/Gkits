package dev.hardling.us.Utils;

import dev.hardling.us.ReactGkits;
import dev.hardling.us.Utils.provider.files.ConfigCreator;
import org.bukkit.entity.Player;


public class Cooldown {

    private static ConfigCreator dataFile = ReactGkits.getInst().getGkitFile();

    public String getCooldown(Player p) {
        if (dataFile.contains("PLAYER." + p.getUniqueId())) {
            for (String s : dataFile.getConfigurationSection("PLAYER." + p.getUniqueId() + ".").getKeys(false)) {
                String timecooldownString = dataFile.getString("PLAYER." + p.getUniqueId() + "." + s + ".COOLDOWN");
                long timecooldown = Long.valueOf(timecooldownString);
                long millis = System.currentTimeMillis();
                long cooldown = ReactGkits.getInst().getGkitFile().getLong("KITS." + s + ".COOLDOWN.TIME"); //En Segundos

                long cooldownmil = cooldown * 1000;
                long espera = millis - timecooldown;
                long esperaDiv = espera / 1000;
                long esperatotalseg = cooldown - esperaDiv;
                long esperatotalmin = esperatotalseg / 60;
                long esperatotalhour = esperatotalmin / 60;

                if (((timecooldown + cooldownmil) > millis) && (timecooldown != 0)) {
                    if (esperatotalseg > 59) {
                        esperatotalseg = esperatotalseg - 60 * esperatotalmin;
                    }
                    String time = "";
                    if (esperatotalseg != 0) {
                        time = esperatotalseg + "s";
                    }
                    if (esperatotalmin > 59) {
                        esperatotalmin = esperatotalmin - 60 * esperatotalhour;
                    }
                    if (esperatotalmin > 0) {
                        time = esperatotalmin + "min" + " " + time;
                    }
                    if (esperatotalhour > 0) {
                        time = esperatotalhour + "h" + " " + time;
                    }
                    //Aun no se termina el cooldown
                    return time;
                } else {
                    //Ya se termino el cooldown
                    return "-1";
                }
            }
        } else {
            // No se encuentra en la data
            return "-1";
        }
        // No se encuentra en la data
        return "-1";
    }
}