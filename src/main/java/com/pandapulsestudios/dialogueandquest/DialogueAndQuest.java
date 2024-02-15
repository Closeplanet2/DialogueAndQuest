package com.pandapulsestudios.dialogueandquest;

import com.pandapulsestudios.dialogueandquest.API.DialogueTreeAPI;
import com.pandapulsestudios.dialogueandquest.Configs.DialogueTree;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class DialogueAndQuest extends JavaPlugin {
    public static HashMap<String, DialogueTree> registeredDialogueTree = new HashMap<>();
    public static HashMap<UUID, String> playersInDialogue = new HashMap<>();

    @Override
    public void onEnable() {
        DialogueTreeAPI.ReloadConfigs();
    }
}
