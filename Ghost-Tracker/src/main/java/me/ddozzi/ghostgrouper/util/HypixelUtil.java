package me.ddozzi.ghostgrouper.util;

import me.ddozzi.ghostgrouper.handler.ScoreboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;

import java.util.ArrayList;
import java.util.List;

public class HypixelUtil {

    public static boolean inSkyblock = false;
    public static boolean inDwarven = false;
    private static ArrayList<String> dwarvenLoc = new ArrayList<String>();

    public static void setup() {
        dwarvenLoc.clear();
        dwarvenLoc.add("The Forge");
        dwarvenLoc.add("Forge Basin");
        dwarvenLoc.add("Palace Bridge");
        dwarvenLoc.add("Royal Palace");
        dwarvenLoc.add("Aristocrat Passage");
        dwarvenLoc.add("Hanging Court");
        dwarvenLoc.add("Divan's Gateway");
        dwarvenLoc.add("Far Reserve");
        dwarvenLoc.add("Goblin Burrows");
        dwarvenLoc.add("Miner's Guild");
        dwarvenLoc.add("Great Ice Wall");
        dwarvenLoc.add("The Mist");
        dwarvenLoc.add("C&C Minecarts Co.");
        dwarvenLoc.add("Grand Library");
        dwarvenLoc.add("Barracks of Heroes");
        dwarvenLoc.add("Dwarven Village");
        dwarvenLoc.add("Royal Quarters");
        dwarvenLoc.add("Lava Springs");
        dwarvenLoc.add("Cliffside Veins");
        dwarvenLoc.add("Rampart's Quarry");
        dwarvenLoc.add("Upper Mines");
        dwarvenLoc.add("Royal Mines");
    }

    public static boolean isOnHypixel() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
        }
        return false;
    }

    public static void checkForSkyblock() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName());
                if (scObjName.contains("SKYBLOCK")) {
                    inSkyblock = true;
                    return;
                }
            }
        }
        inSkyblock = false;
    }

    public static void checkForDwarven() {
        if (inSkyblock) {
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardHandler.cleanSB(s);
                for(String listItem : dwarvenLoc){
                    if(sCleaned.contains(listItem)){
                        inDwarven = true;
                        return;
                    }
                }
            }
        }
        inDwarven = false;
    }

}
