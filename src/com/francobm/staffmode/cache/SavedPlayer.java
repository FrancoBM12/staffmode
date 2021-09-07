package com.francobm.staffmode.cache;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

public class SavedPlayer {
    private ItemStack[] savedInventory;
    private ItemStack[] savedEquipment;
    private GameMode savedGameMode;
    private float savedXP;
    private int savedLevel;
    private int hungerSaved;
    private double healthSaved;
    private double maxHealthSaved;
    private boolean flyingSaved;
    private boolean allowFlightSaved;

    public SavedPlayer(ItemStack[] savedInventory, ItemStack[] savedEquipment, float savedXP, int savedLevel){
        this.savedInventory = savedInventory;
        this.savedEquipment = savedEquipment;
        this.savedXP = savedXP;
        this.savedLevel = savedLevel;
    }
    public SavedPlayer(ItemStack[] savedInventory, ItemStack[] savedEquipment, GameMode savedGameMode, float savedXP, int savedLevel, int hungerSaved, double healthSaved, double maxHealthSaved, boolean flyingSaved, boolean allowFlightSaved) {
        this.savedInventory = savedInventory;
        this.savedEquipment = savedEquipment;
        this.savedGameMode = savedGameMode;
        this.savedXP = savedXP;
        this.savedLevel = savedLevel;
        this.hungerSaved = hungerSaved;
        this.healthSaved = healthSaved;
        this.maxHealthSaved = maxHealthSaved;
        this.flyingSaved = flyingSaved;
        this.allowFlightSaved = allowFlightSaved;
    }

    public ItemStack[] getSavedInventory() {
        return savedInventory;
    }

    public void setSavedInventory(ItemStack[] savedInventory) {
        this.savedInventory = savedInventory;
    }

    public ItemStack[] getSavedEquipment() {
        return savedEquipment;
    }

    public void setSavedEquipment(ItemStack[] savedEquipment) {
        this.savedEquipment = savedEquipment;
    }

    public GameMode getSavedGameMode() {
        return savedGameMode;
    }

    public void setSavedGameMode(GameMode savedGameMode) {
        this.savedGameMode = savedGameMode;
    }

    public float getSavedXP() {
        return savedXP;
    }

    public void setSavedXP(float savedXP) {
        this.savedXP = savedXP;
    }

    public int getSavedLevel() {
        return savedLevel;
    }

    public void setSavedLevel(int savedLevel) {
        this.savedLevel = savedLevel;
    }

    public int getHungerSaved() {
        return hungerSaved;
    }

    public void setHungerSaved(int hungerSaved) {
        this.hungerSaved = hungerSaved;
    }

    public double getHealthSaved() {
        return healthSaved;
    }

    public void setHealthSaved(double lifeSaved) {
        this.healthSaved = lifeSaved;
    }

    public double getMaxHealthSaved() {
        return maxHealthSaved;
    }

    public void setMaxHealthSaved(double maxLifeSaved) {
        this.maxHealthSaved = maxLifeSaved;
    }

    public boolean isFlyingSaved() {
        return flyingSaved;
    }

    public void setFlyingSaved(boolean flyingSaved) {
        this.flyingSaved = flyingSaved;
    }

    public boolean isAllowFlightSaved() {
        return allowFlightSaved;
    }

    public void setAllowFlightSaved(boolean allowFlightSaved) {
        this.allowFlightSaved = allowFlightSaved;
    }
}
