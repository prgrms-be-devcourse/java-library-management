package com.programmers.app.instances;

import com.programmers.app.console.CommunicationAgent;
import com.programmers.app.console.CommunicationAgentImpl;
import com.programmers.app.console.ConsoleReader;
import com.programmers.app.mode.ModeSelector;
import com.programmers.app.mode.ModeSelectorImpl;

public class GeneralInstances {
    private final ModeSelector modeSelector;
    private final CommunicationAgent communicationAgent;

    public GeneralInstances() {
        communicationAgent = new CommunicationAgentImpl(new ConsoleReader());
        modeSelector = new ModeSelectorImpl(communicationAgent);
    }

    public CommunicationAgent getCommunicationAgent() {
        return communicationAgent;
    }

    public ModeSelector getModeSelector() {
        return modeSelector;
    }
}
