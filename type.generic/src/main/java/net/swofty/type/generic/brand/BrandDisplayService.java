package net.swofty.type.generic.brand;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

/**
 * Brand display service for Max Momentum.
 * Centralizes all player-facing branding without modifying existing systems.
 * 
 * This service is mode-agnostic and does not reference ServerType enums
 * or assume any specific game mode context.
 * 
 * Phase 1: Infrastructure only - not yet integrated into existing code.
 */
public class BrandDisplayService {
    
    /**
     * Get the formatted server name with brand colors
     * @return Formatted server name as Component
     */
    public static Component getFormattedServerName() {
        String primary = BrandConfig.getPrimaryColor();
        String secondary = BrandConfig.getSecondaryColor();
        String name = BrandConfig.getBrandName();
        
        return Component.text(primary + name);
    }
    
    /**
     * Get the formatted MOTD (Message of the Day)
     * @return Formatted MOTD as Component
     */
    public static Component getFormattedMotd() {
        String primary = BrandConfig.getPrimaryColor();
        String secondary = BrandConfig.getSecondaryColor();
        String name = BrandConfig.getBrandName();
        String tagline = BrandConfig.getTagline();
        
        return Component.text(primary + name + " §7| " + secondary + tagline);
    }
    
    /**
     * Get the scoreboard title with brand formatting
     * @return Formatted scoreboard title as Component
     */
    public static Component getScoreboardTitle() {
        String primary = BrandConfig.getPrimaryColor();
        String shortName = BrandConfig.getShortName();
        
        if (BrandConfig.isUseBrandPrefix()) {
            return Component.text(primary + "§l" + shortName);
        } else {
            return Component.text(primary + shortName);
        }
    }
    
    /**
     * Get the tab list header with brand formatting
     * @return Formatted tab header as Component
     */
    public static Component getTabHeader() {
        String primary = BrandConfig.getPrimaryColor();
        String secondary = BrandConfig.getSecondaryColor();
        String name = BrandConfig.getBrandName();
        String tagline = BrandConfig.getTagline();
        
        return Component.text("\n" + primary + "§l" + name + "\n" + 
                             secondary + tagline + "\n");
    }
    
    /**
     * Get the tab list footer with brand formatting
     * @return Formatted tab footer as Component
     */
    public static Component getTabFooter() {
        String secondary = BrandConfig.getSecondaryColor();
        
        return Component.text("\n" + secondary + "Play. Build. Compete.\n");
    }
    
    /**
     * Format text with primary brand color
     * @param input Text to format
     * @return Formatted text as Component
     */
    public static Component formatWithPrimaryColor(String input) {
        String primary = BrandConfig.getPrimaryColor();
        return Component.text(primary + input);
    }
    
    /**
     * Format text with secondary brand color
     * @param input Text to format
     * @return Formatted text as Component
     */
    public static Component formatWithSecondaryColor(String input) {
        String secondary = BrandConfig.getSecondaryColor();
        return Component.text(secondary + input);
    }
    
    /**
     * Get a formatted prefix for chat messages
     * @return Formatted prefix as Component
     */
    public static Component getChatPrefix() {
        if (BrandConfig.isUseBrandPrefix()) {
            String primary = BrandConfig.getPrimaryColor();
            String shortName = BrandConfig.getShortName();
            return Component.text(primary + "[" + shortName + "] §7");
        } else {
            return Component.empty();
        }
    }
    
    /**
     * Get a formatted join message
     * @param playerName Name of the player joining
     * @return Formatted join message as Component
     */
    public static Component getJoinMessage(String playerName) {
        if (BrandConfig.isFriendlyJoinEnabled()) {
            String secondary = BrandConfig.getSecondaryColor();
            return Component.text(secondary + "§l+ §r" + secondary + playerName);
        } else {
            return Component.text("§e" + playerName + " §ejoined the game");
        }
    }
    
    /**
     * Get a formatted leave message
     * @param playerName Name of the player leaving
     * @return Formatted leave message as Component
     */
    public static Component getLeaveMessage(String playerName) {
        if (BrandConfig.isFriendlyJoinEnabled()) {
            String secondary = BrandConfig.getSecondaryColor();
            return Component.text(secondary + "§l- §r" + secondary + playerName);
        } else {
            return Component.text("§e" + playerName + " §eleft the game");
        }
    }
    
    /**
     * Get the brand name as plain text (for logging, etc.)
     * @return Brand name string
     */
    public static String getBrandNamePlain() {
        return BrandConfig.getBrandName();
    }
    
    /**
     * Get the short name as plain text
     * @return Short name string
     */
    public static String getShortNamePlain() {
        return BrandConfig.getShortName();
    }
    
    /**
     * Get the tagline as plain text
     * @return Tagline string
     */
    public static String getTaglinePlain() {
        return BrandConfig.getTagline();
    }
    
    /**
     * Format a server name with mode-specific context
     * This method is mode-agnostic and does not reference ServerType
     * 
     * @param modeName The name of the game mode (e.g., "Hub", "Island", "BedWars")
     * @return Formatted server name as Component
     */
    public static Component getFormattedServerNameWithMode(String modeName) {
        String primary = BrandConfig.getPrimaryColor();
        String secondary = BrandConfig.getSecondaryColor();
        String name = BrandConfig.getBrandName();
        
        return Component.text(primary + name + " §7| " + secondary + modeName);
    }
    
    /**
     * Get a formatted error message with brand styling
     * @param message Error message text
     * @return Formatted error message as Component
     */
    public static Component getErrorMessage(String message) {
        return Component.text("§c§l! §r§c" + message);
    }
    
    /**
     * Get a formatted success message with brand styling
     * @param message Success message text
     * @return Formatted success message as Component
     */
    public static Component getSuccessMessage(String message) {
        String secondary = BrandConfig.getSecondaryColor();
        return Component.text(secondary + "§l✓ §r" + secondary + message);
    }
    
    /**
     * Get a formatted info message with brand styling
     * @param message Info message text
     * @return Formatted info message as Component
     */
    public static Component getInfoMessage(String message) {
        String primary = BrandConfig.getPrimaryColor();
        return Component.text(primary + "§lℹ §r§7" + message);
    }
}
