package me.ddozzi.allowhubs.listener;

import me.ddozzi.allowhubs.DungeonHub;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ChatListener {
	public static Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW )
    public void onChat(ClientChatReceivedEvent event) {
        
        String unformatted = StringUtils.stripControlCodes(event.message.getUnformattedText());
        
        if (unformatted.startsWith(" ☠ You were ")) {
        	if (DungeonHub.config.autowarp == true) {
			Utils.checkForDungeons();
    			Utils.checkForSkyblock();
    			if (Utils.isOnHypixel() && Utils.inSkyblock && Utils.inDungeons) {

			
			
        	new Thread(() -> {
             try {
            	 
            	 String option = new String("");
 				

 				if (DungeonHub.config.warpchoice == 0) {
 					option = "hub";
 				} else if (DungeonHub.config.warpchoice == 1) {
 					option = "dungeon_hub";
 				} else if (DungeonHub.config.warpchoice == 2) {
 					option = "home";
 				} else if (DungeonHub.config.warpchoice > 2) {
 					System.out.println("error, out of bounds contact ddozzi");
 				} 
            	 
				Thread.sleep(DungeonHub.config.autowarpdelay);
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/l");
				Thread.sleep(DungeonHub.config.delay);
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/skyblock");
				Thread.sleep(DungeonHub.config.delay);
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp "+ option);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
        }).start();
			}
        	} else {
        		return;
        	}
       }
	}}
