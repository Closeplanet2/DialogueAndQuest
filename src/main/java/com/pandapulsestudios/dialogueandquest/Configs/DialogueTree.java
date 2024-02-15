package com.pandapulsestudios.dialogueandquest.Configs;

import com.pandapulsestudios.dialogueandquest.API.DialogueTreeAPI;
import com.pandapulsestudios.dialogueandquest.DialogueAndQuest;
import com.pandapulsestudios.dialogueandquest.Enums.DialogueSkipType;
import com.pandapulsestudios.dialogueandquest.Enums.DialogueTransitionType;
import com.pandapulsestudios.dialogueandquest.Enums.DialogueViewpointType;
import com.pandapulsestudios.pulseconfig.APIS.ConfigAPI;
import com.pandapulsestudios.pulseconfig.Interfaces.IgnoreSave;
import com.pandapulsestudios.pulseconfig.Interfaces.PulseConfig;
import com.pandapulsestudios.pulsecore.FileSystem.DirAPI;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DialogueTree implements PulseConfig {

    public static HashMap<String, DialogueTree> LoadAll(){
        var data = new HashMap<String, DialogueTree>();
        var configPath = ConfigAPI.ReturnConfigPath(DialogueAndQuest.class, DialogueTree.class);
        for(var file : DirAPI.ReturnAllDirectoryPaths(new File(configPath), false)){
            var fileName = file.getName().replace(".yml", "");
            var dialogueTree = new DialogueTree(fileName);
            dialogueTree.Load(DialogueAndQuest.class, false, false);
            data.put(dialogueTree.dialogueTreeName, dialogueTree);
        }
        return data;
    }

    @Override
    public String documentID() { return dialogueTreeName; }

    @IgnoreSave
    public String dialogueTreeName = "";
    public UUID startNode;
    public DialogueSkipType dialogueSkipType = DialogueSkipType.Time;
    public DialogueViewpointType dialogueViewpointType = DialogueViewpointType.Player;
    public int radiusTrigger = -1;
    public List<DialogueNode> npcNodes = new ArrayList<>();
    public List<DialogueNode> playerNodes = new ArrayList<>();

    public DialogueTree(String dialogueTreeName){
        this.dialogueTreeName = dialogueTreeName;
        this.startNode = UUID.randomUUID();
    }

    public DialogueTree(String dialogueTreeName, UUID startNode, DialogueSkipType dialogueSkipType, DialogueViewpointType dialogueViewpointType, int radiusTrigger, List<DialogueNode> npcNodes, List<DialogueNode> playerNodes){
        this.dialogueTreeName = dialogueTreeName;
        this.startNode = startNode;
        this.dialogueSkipType = dialogueSkipType;
        this.dialogueViewpointType = dialogueViewpointType;
        this.radiusTrigger = radiusTrigger;
        this.npcNodes = npcNodes;
        this.playerNodes = playerNodes;
    }

    public void StartDialogueTree(Player player){

    }

    public static DialogueTreeBuilder DialogueTreeBuilder(){ return new DialogueTreeBuilder(); }
    public static class DialogueTreeBuilder{
        private UUID startNode;
        private DialogueSkipType dialogueSkipType = DialogueSkipType.Time;
        private DialogueViewpointType dialogueViewpointType = DialogueViewpointType.Player;
        private int radiusTrigger = -1;
        private List<DialogueNode> npcNodes = new ArrayList<>();
        private List<DialogueNode> playerNodes = new ArrayList<>();

        public DialogueTreeBuilder startNode(UUID startNode){
            this.startNode = startNode;
            return this;
        }

        public DialogueTreeBuilder dialogueSkipType(DialogueSkipType dialogueSkipType){
            this.dialogueSkipType = dialogueSkipType;
            return this;
        }

        public DialogueTreeBuilder dialogueViewpointType(DialogueViewpointType dialogueViewpointType){
            this.dialogueViewpointType = dialogueViewpointType;
            return this;
        }

        public DialogueTreeBuilder radiusTrigger(int radiusTrigger){
            this.radiusTrigger = radiusTrigger;
            return this;
        }

        public DialogueTreeBuilder addNPCNode(DialogueNode dialogueNode){
            npcNodes.add(dialogueNode);
            return this;
        }

        public DialogueTreeBuilder addPlayerNode(DialogueNode dialogueNode){
            playerNodes.add(dialogueNode);
            return this;
        }

        public DialogueTree Create(String dialogueTreeName, boolean override){
            return DialogueTreeAPI.Create(override, dialogueTreeName, startNode, dialogueSkipType, dialogueViewpointType, radiusTrigger, npcNodes, playerNodes);
        }
    }
}
