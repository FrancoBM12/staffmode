package com.francobm.staffmode.cache;

import org.bukkit.entity.Player;

public class PlayerCache {
    private final Player player;
    private SavedPlayer savedPlayer;
    private boolean death;
    private boolean vanish;
    private boolean chat;
    private boolean mode;
    private boolean build;
    private boolean freeze;

    public PlayerCache(Player player){
        this.player = player;
        this.build = true;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isVanish() {
        return vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public boolean isChat() {
        return chat;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public boolean isBuild() {
        return build;
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public SavedPlayer getSavedItems() {
        return savedPlayer;
    }

    public void setSavedItems(SavedPlayer savedPlayer) {
        this.savedPlayer = savedPlayer;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }
}
