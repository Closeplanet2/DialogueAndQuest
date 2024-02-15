package com.pandapulsestudios.dialogueandquest.API;

import com.pandapulsestudios.dialogueandquest.Configs.DialogueNode;
import com.pandapulsestudios.dialogueandquest.Configs.DialogueTree;
import com.pandapulsestudios.dialogueandquest.DialogueAndQuest;
import com.pandapulsestudios.dialogueandquest.Enums.DialogueSkipType;
import com.pandapulsestudios.dialogueandquest.Enums.DialogueViewpointType;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class DialogueTreeAPI {
    public static void ReloadConfigs(){
        DialogueAndQuest.registeredDialogueTree = DialogueTree.LoadAll();
    }

    public static DialogueTree Create(boolean override, String dialogueTreeName, UUID startNode, DialogueSkipType dialogueSkipType, DialogueViewpointType dialogueViewpointType, int radiusTrigger, List<DialogueNode> npcNodes, List<DialogueNode> playerNodes){
        var storedQuest = DialogueTreeAPI.Return(dialogueTreeName);
        if(storedQuest != null && !override) return storedQuest;
        var dialogueTree = new DialogueTree(dialogueTreeName, startNode, dialogueSkipType, dialogueViewpointType, radiusTrigger, npcNodes, playerNodes);
        dialogueTree.Save(DialogueAndQuest.class, false, false, false);
        DialogueAndQuest.registeredDialogueTree.put(dialogueTree.dialogueTreeName, dialogueTree);
        return dialogueTree;
    }

    public static DialogueTree Return(String dialogueTreeName){
        return DialogueAndQuest.registeredDialogueTree.getOrDefault(dialogueTreeName, null);
    }

    public static boolean CanPlayerStartDialogueTree(Player player){
        return !DialogueAndQuest.playersInDialogue.containsKey(player.getUniqueId());
    }

    public static void StartDialogueTree(String dialogueTreeName, Player... players){
        var dialogueTree = Return(dialogueTreeName);
        if(dialogueTree == null) return;
        for(var player : players){
            if(!CanPlayerStartDialogueTree(player)) continue;
            DialogueAndQuest.playersInDialogue.put(player.getUniqueId(), dialogueTree.dialogueTreeName);
            dialogueTree.StartDialogueTree(player);
        }
    }
}
