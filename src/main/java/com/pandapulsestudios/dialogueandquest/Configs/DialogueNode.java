package com.pandapulsestudios.dialogueandquest.Configs;

import com.pandapulsestudios.dialogueandquest.Enums.DialogueTransitionType;
import com.pandapulsestudios.pulseconfig.Interfaces.PulseClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DialogueNode implements PulseClass {
    public UUID nodeId;
    public String nodeMessage;
    public Long timeDelay = 20L;
    public DialogueTransitionType dialogueTransitionType;
    public List<UUID> connectedNodes = new ArrayList<>();
    public List<String> nodePerms = new ArrayList<>();

    public DialogueNode(String nodeMessage, Long timeDelay, DialogueTransitionType dialogueTransitionType, String... perms){
        this.nodeId = UUID.randomUUID();
        this.nodeMessage = nodeMessage;
        this.timeDelay = timeDelay;
        this.dialogueTransitionType = dialogueTransitionType;
        nodePerms.addAll(Arrays.asList(perms));
    }

    public void AddConnectedNodes(UUID... nodeIDs){
        connectedNodes.addAll(Arrays.asList(nodeIDs));
    }
}
