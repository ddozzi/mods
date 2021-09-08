package me.ddozzi.ghostgrouper;

import club.sk1er.mods.core.ModCoreInstaller;
import me.ddozzi.ghostgrouper.config.Config;
import me.ddozzi.ghostgrouper.config.ConfigCommand;
import me.ddozzi.ghostgrouper.util.MathUtils;
import me.ddozzi.ghostgrouper.util.Renderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

@Mod(modid = GhostGrouper.MODID, version = GhostGrouper.VERSION)
public class GhostGrouper{

    public static final GhostGrouper instance = new GhostGrouper();
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String MODID = "ghostgrouper";
    public static final String VERSION = "1.0";
    public static Config config = new Config();

    @EventHandler
    public void init(FMLInitializationEvent e) {
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        ClientCommandHandler.instance.registerCommand(new ConfigCommand());
        MinecraftForge.EVENT_BUS.register(this);
        config.preload();
    }

    @SubscribeEvent
    public void RenderWorldLast(RenderWorldLastEvent e) {

        Entity player = Minecraft.getMinecraft().thePlayer;

        Renderer.genGhostList(player.worldObj, (int) player.posX, (int) player.posZ, config.GHOST_RADIUS);
        Renderer.genGhostCoords(Renderer.ghostsInRadius);

        ArrayList<Double> avgCoords = Renderer.getAverageCoords(Renderer.ghostCoordsX, Renderer.ghostCoordsY, Renderer.ghostCoordsZ);
        ArrayList<Double> avgLTCoords = Renderer.getAverageCoords(Renderer.ghostCoordsLTX, Renderer.ghostCoordsLTY, Renderer.ghostCoordsLTZ);

        Double avgX = avgCoords.get(0);
        Double avgY = avgCoords.get(1);
        Double avgZ = avgCoords.get(2);

        Double avgXLT = avgLTCoords.get(0);
        Double avgYLT = avgLTCoords.get(1);
        Double avgZLT = avgLTCoords.get(2);

        Integer totalHP = Renderer.getTotalHPDwarven(Renderer.ghostsInRadius);
        Integer currentHP = Renderer.getCurrentHP(Renderer.ghostsInRadius);

        if (config.USER_HITBOX) {
            Renderer.renderSquareOnUser(player, new Color(MathUtils.instance.getColorFromProgress(config.USER_COLOR)), config.GHOST_RADIUS, e.partialTicks);
        }
        if (config.INDIV_HITBOXES) {
            Renderer.renderSquareOnGhost(mc.thePlayer.worldObj, new Color(MathUtils.instance.getColorFromProgress(config.HITBOX_COLOR)), (int) player.lastTickPosX, (int) player.lastTickPosZ, config.GHOST_RADIUS, e.partialTicks);
        }


        Renderer.renderSquareOnGhostClump(
                new Color(MathUtils.instance.getColorFromProgress(config.HITBOX_COLOR)),
                avgCoords.get(0), avgCoords.get(1), avgCoords.get(2),
                avgLTCoords.get(0), avgLTCoords.get(1), avgLTCoords.get(2),
                config.GHOST_RADIUS, e.partialTicks);


        Renderer.renderHP("HP: " + currentHP + " / " + totalHP, avgX, avgY, avgZ, avgXLT, avgYLT, avgZLT, e.partialTicks);


        avgCoords.clear();
        avgLTCoords.clear();

    }
}
