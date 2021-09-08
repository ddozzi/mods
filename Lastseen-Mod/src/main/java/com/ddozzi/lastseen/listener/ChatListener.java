package com.ddozzi.lastseen.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener {
	public static Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW )
    public void onChat(ClientChatReceivedEvent event) {
        
        String unformatted = StringUtils.stripControlCodes(event.message.getUnformattedText());
        
        if (unformatted.startsWith("Your new API key is ")) {
        	 String apiKey = event.message.getSiblings().get(0).getChatStyle().getChatClickEvent().getValue();
        	 
        	 try {
        		 Properties prop = new Properties();
        		
         		prop.setProperty("key", apiKey);

         		prop.store(new FileOutputStream("config/lsapikey.cfg"), null);

         		} catch (IOException ex) {
         		ex.printStackTrace();
         		}
             mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Updated your API key to: " + EnumChatFormatting.DARK_GREEN + apiKey));
        }
        	
       }
}



