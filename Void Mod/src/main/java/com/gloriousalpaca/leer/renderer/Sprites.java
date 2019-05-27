package com.gloriousalpaca.leer.renderer;

import com.gloriousalpaca.leer.LEER;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class Sprites {
	public static TextureAtlasSprite asteroid;
	
	
	public static void register(TextureStitchEvent.Pre event){
        asteroid = event.getMap().registerSprite(new ResourceLocation(LEER.MODID+":entity/asteroidprojectile"));
    }
	
}
