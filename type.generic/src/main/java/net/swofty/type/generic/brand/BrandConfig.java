package net.swofty.type.generic.brand;

import lombok.Getter;
import org.tinylog.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Brand configuration loader for Max Momentum.
 * This class loads brand identity settings from configuration/maxmomentum.yml
 * without modifying any existing systems or enums.
 * 
 * This is a purely additive architectural layer for Phase 1 of the rebrand.
 */
public class BrandConfig {
    
    @Getter
    private static String brandName = "Max Momentum";
    
    @Getter
    private static String shortName = "MM";
    
    @Getter
    private static String tagline = "build. compete. grow.";
    
    @Getter
    private static String primaryColor = "§6";
    
    @Getter
    private static String secondaryColor = "§e";
    
    @Getter
    private static boolean crackedSupported = false;
    
    @Getter
    private static boolean antiToxicityEnabled = true;
    
    @Getter
    private static boolean friendlyJoinEnabled = true;
    
    @Getter
    private static boolean animatedScoreboard = true;
    
    @Getter
    private static boolean useBrandPrefix = true;
    
    private static boolean loaded = false;
    
    static {
        loadConfiguration();
    }
    
    /**
     * Load brand configuration from maxmomentum.yml
     * Fails gracefully if file is missing or malformed
     */
    private static void loadConfiguration() {
        if (loaded) {
            return;
        }
        
        File configFile = new File("./configuration/maxmomentum.yml");
        
        if (!configFile.exists()) {
            Logger.warn("Brand configuration file not found at: " + configFile.getAbsolutePath());
            Logger.warn("Using default brand values");
            loaded = true;
            return;
        }
        
        try (FileInputStream input = new FileInputStream(configFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(input);
            
            if (config == null) {
                Logger.warn("Brand configuration file is empty, using defaults");
                loaded = true;
                return;
            }
            
            // Load brand section
            if (config.containsKey("brand")) {
                Map<String, Object> brand = (Map<String, Object>) config.get("brand");
                
                if (brand.containsKey("name")) {
                    brandName = (String) brand.get("name");
                }
                if (brand.containsKey("short_name")) {
                    shortName = (String) brand.get("short_name");
                }
                if (brand.containsKey("tagline")) {
                    tagline = (String) brand.get("tagline");
                }
                if (brand.containsKey("primary_color")) {
                    primaryColor = (String) brand.get("primary_color");
                }
                if (brand.containsKey("secondary_color")) {
                    secondaryColor = (String) brand.get("secondary_color");
                }
            }
            
            // Load network section
            if (config.containsKey("network")) {
                Map<String, Object> network = (Map<String, Object>) config.get("network");
                
                if (network.containsKey("cracked_supported")) {
                    crackedSupported = (Boolean) network.get("cracked_supported");
                }
                if (network.containsKey("anti_toxicity_enabled")) {
                    antiToxicityEnabled = (Boolean) network.get("anti_toxicity_enabled");
                }
                if (network.containsKey("friendly_join_messages")) {
                    friendlyJoinEnabled = (Boolean) network.get("friendly_join_messages");
                }
            }
            
            // Load display section
            if (config.containsKey("display")) {
                Map<String, Object> display = (Map<String, Object>) config.get("display");
                
                if (display.containsKey("animated_scoreboard")) {
                    animatedScoreboard = (Boolean) display.get("animated_scoreboard");
                }
                if (display.containsKey("use_brand_prefix")) {
                    useBrandPrefix = (Boolean) display.get("use_brand_prefix");
                }
            }
            
            Logger.info("Brand configuration loaded successfully");
            Logger.info("Brand: " + brandName + " (" + shortName + ")");
            Logger.info("Tagline: " + tagline);
            
            loaded = true;
            
        } catch (IOException e) {
            Logger.error("Failed to load brand configuration: " + e.getMessage());
            Logger.warn("Using default brand values");
            loaded = true;
        } catch (ClassCastException e) {
            Logger.error("Brand configuration has invalid format: " + e.getMessage());
            Logger.warn("Using default brand values");
            loaded = true;
        }
    }
    
    /**
     * Force reload of configuration (useful for testing)
     */
    public static void reload() {
        loaded = false;
        loadConfiguration();
    }
}
