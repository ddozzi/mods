package me.ddozzi.ghostgrouper.util;

import me.ddozzi.ghostgrouper.GhostGrouper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static List<Entity> ghostsInRadius = new ArrayList<Entity>();

    public static ArrayList<Double> ghostCoordsX = new ArrayList<Double>();
    public static ArrayList<Double> ghostCoordsY = new ArrayList<Double>();
    public static ArrayList<Double> ghostCoordsZ = new ArrayList<Double>();

    public static ArrayList<Double> ghostCoordsLTX = new ArrayList<Double>();
    public static ArrayList<Double> ghostCoordsLTY = new ArrayList<Double>();
    public static ArrayList<Double> ghostCoordsLTZ = new ArrayList<Double>();


    public static List<Entity> getEnts(World w, int x, int z, int radius) {
        return w.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x,0,z,x+1,257,z+1).expand(radius, 1, radius));
    }

    public static void renderSquareOnGhost(World world, Color color, int x, int z, int radius, float partialTicks) {
        List<Entity> entitiesInRadius = getEnts(world, x, z, radius);

        for(Entity e : entitiesInRadius) {

            if(e instanceof EntityCreeper ) {
                RenderSquare(e, partialTicks, color.getRed(), color.getGreen() ,color.getBlue() , radius);
            }
        }
//        GL11.glEnd();
//        GL11.glPopMatrix();
//
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glDepthMask(true);

    }

    public static void renderSquareOnUser(Entity player, Color color, int radius, float partialTicks) {
        RenderSquare(player, partialTicks, color.getRed(), color.getGreen() ,color.getBlue() , radius);

    }

    public static void genGhostList(World world, int x, int z, int radius) {
        List<Entity> entitiesInRadius = getEnts(world, x, z, radius);
        ghostsInRadius.clear();
        for (Entity ent : entitiesInRadius) {
            if(ent instanceof EntityCreeper) {
                ghostsInRadius.add(ent);
            }
        }
    }

    public static void genGhostCoords(List<Entity> entityList) {
        ghostCoordsX.clear(); ghostCoordsLTX.clear();
        ghostCoordsY.clear(); ghostCoordsLTY.clear();
        ghostCoordsZ.clear(); ghostCoordsLTZ.clear();

        for(Entity ghost : entityList) {
            ghostCoordsX.add(ghost.posX);
            ghostCoordsY.add(ghost.posY);
            ghostCoordsZ.add(ghost.posZ);

            ghostCoordsLTX.add(ghost.lastTickPosX);
            ghostCoordsLTY.add(ghost.lastTickPosY);
            ghostCoordsLTZ.add(ghost.lastTickPosZ);
        }
    }

    public static ArrayList<Double> getAverageCoords(List<Double> x, List<Double> y, List<Double> z) {

        ArrayList<Double> coords = new ArrayList<Double>();

        double xAvg = MathUtils.instance.average(x);
        double yAvg = MathUtils.instance.average(y);
        double zAvg = MathUtils.instance.average(z);

        coords.add(xAvg);
        coords.add(yAvg);
        coords.add(zAvg);

        return coords;
    }

    public static void renderSquareOnGhostClump(Color color, double x, double y, double z, double ltx, double lty, double ltz, int radius, float partialTicks) {
        RenderSquareUsingCoords(ltx, lty, ltz, x, y, z, partialTicks, color.getRed(), color.getGreen(), color.getBlue(), radius);


    }

    public static void renderHP(String words, double x, double y, double z, double ltx, double lty, double ltz, float pticks) {
        if(GhostGrouper.config.DISPLAY_HEALTH) {
            Renderer.drawTextAtWorld(words,  ((Double)x).floatValue() , ((Double)y).floatValue(), ((Double)z).floatValue(), ((Double)ltx).floatValue(), ((Double)lty).floatValue(), ((Double)ltz).floatValue(), new Color(MathUtils.instance.getColorFromProgress(GhostGrouper.config.TEXT_COLOR)).getRGB(), 0.125f, true, pticks);
        }
    }

    public static int getTotalHP(List<Entity> creeperList) {
        int totalHP = 0;
        for(Entity creeper : creeperList) {
            EntityLivingBase castedCreeper = (EntityLivingBase)creeper;
            totalHP += castedCreeper.getMaxHealth();
        }
        return totalHP;
    }

    public static int getTotalHPDwarven(List<Entity> creeperList) {

        if(HypixelUtil.isOnHypixel()) {

            HypixelUtil.setup();
            HypixelUtil.checkForSkyblock();

            if(HypixelUtil.inSkyblock) {
                int totalHP = 0;
                for(Entity creeper : creeperList) {
                    totalHP ++;
                }
                return totalHP * 1000000;
            }

            return getTotalHP(creeperList);

        }

        return getTotalHP(creeperList);

    }


    public static int getCurrentHP(List<Entity> creeperList) {
        int currentHP = 0;
        for(Entity creeper : creeperList) {
            EntityLivingBase castedCreeper = (EntityLivingBase)creeper;
            currentHP += castedCreeper.getHealth();
        }
        return currentHP;
    }


    public static void RenderSquareUsingCoords(double ltx, double lty, double ltz, double x, double y, double z, float partialTicks, float red, float green, float blue, float radius) {

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1.0f);

        GL11.glColor3f(red/255f, green/255f, blue/255f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        if(GhostGrouper.config.DISABLE_DEPTH_MASK) {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
        }


        GL11.glTranslated(-Minecraft.getMinecraft().getRenderManager().viewerPosX, -Minecraft.getMinecraft().getRenderManager().viewerPosY, -Minecraft.getMinecraft().getRenderManager().viewerPosZ);

        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_LINES);

        float mx = (float) (ltx + (x - ltx) * partialTicks );
        float my = (float) (lty + (y - lty) * partialTicks + 0.6F);
        float mz = (float) (ltz + (z - ltz) * partialTicks );

        GL11.glVertex3f(mx + (radius + 0.5F), my - 0.5F, mz - (radius + 0.5F)); GL11.glVertex3f(mx - (radius + 0.5F), my - 0.5F, mz - (radius + 0.5F));
        GL11.glVertex3f(mx + (radius + 0.5F), my - 0.5F, mz + (radius + 0.5F)); GL11.glVertex3f(mx - (radius + 0.5F), my - 0.5F, mz + (radius + 0.5F));
        GL11.glVertex3f(mx + (radius + 0.5F), my - 0.5F, mz - (radius + 0.5F)); GL11.glVertex3f(mx + (radius + 0.5F), my - 0.5F, mz + (radius + 0.5F));
        GL11.glVertex3f(mx - (radius + 0.5F), my - 0.5F, mz - (radius + 0.5F)); GL11.glVertex3f(mx - (radius + 0.5F), my - 0.5F, mz + (radius + 0.5F));
//
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);

    }

    
    public static void RenderSquare(Entity entity, float partialTicks, float red, float green, float blue, float radius) {

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1.0f);

        GL11.glColor3f(red/255f, green/255f, blue/255f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        if(GhostGrouper.config.DISABLE_DEPTH_MASK) {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
        }

        GL11.glTranslated(-Minecraft.getMinecraft().getRenderManager().viewerPosX, -Minecraft.getMinecraft().getRenderManager().viewerPosY, -Minecraft.getMinecraft().getRenderManager().viewerPosZ);

        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_LINES);

        float mx = (float) (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks );
        float my = (float) (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks + 0.6F);
        float mz = (float) (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks );

        GL11.glVertex3f(mx + (radius + 0.5F), my - 0.4F, mz - (radius + 0.5F)); GL11.glVertex3f(mx - (radius + 0.5F), my - 0.4F, mz - (radius + 0.5F));
        GL11.glVertex3f(mx + (radius + 0.5F), my - 0.4F, mz + (radius + 0.5F)); GL11.glVertex3f(mx - (radius + 0.5F), my - 0.4F, mz + (radius + 0.5F));
        GL11.glVertex3f(mx + (radius + 0.5F), my - 0.4F, mz - (radius + 0.5F)); GL11.glVertex3f(mx + (radius + 0.5F), my - 0.4F, mz + (radius + 0.5F));
        GL11.glVertex3f(mx - (radius + 0.5F), my - 0.4F, mz - (radius + 0.5F)); GL11.glVertex3f(mx - (radius + 0.5F), my - 0.4F, mz + (radius + 0.5F));
////
//        GL11.glEnd();
//        GL11.glPopMatrix();
//
////        GL11.glEnable(GL11.GL_TEXTURE_2D);
////        GL11.glEnable(GL11.GL_DEPTH_TEST);
////        GL11.glDepthMask(true);
    }

    public static void drawTextAtWorld(String text, float x, float y, float z, float ltx, float lty, float ltz, int color, float scale, boolean renderBlackBox, float partialTicks) {
        float lScale = scale;

        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        GlStateManager.color(1f, 1f, 1f, 0.5f);
        GlStateManager.pushMatrix();

        float mx = (ltx + (x - ltx) * partialTicks );
        float my = (lty + (y - lty) * partialTicks + 2.6F);
        float mz = (ltz + (z - ltz) * partialTicks );

        GlStateManager.translate(mx, my, mz);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-lScale, -lScale, lScale);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false); GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int textWidth = fontRenderer.getStringWidth(text);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        if (renderBlackBox) {
            double j = textWidth / 2;
            GlStateManager.disableTexture2D();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(-j - 1, -1, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(-j - 1, 8, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(j + 1, 8, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(j + 1, -1, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }

        GlStateManager.enableBlend();
        GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        fontRenderer.drawString(text, -textWidth / 2, 0, color);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }


}
