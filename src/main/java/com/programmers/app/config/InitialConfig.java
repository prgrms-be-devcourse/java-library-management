package com.programmers.app.config;

import com.programmers.app.console.CommunicationAgent;
import com.programmers.app.console.ConsoleReader;
import com.programmers.app.mode.ModeSelector;

public class InitialConfig {
    private final ModeSelector modeSelector;
    private final CommunicationAgent communicationAgent;

    public InitialConfig() {
        communicationAgent = new CommunicationAgent(new ConsoleReader());
        modeSelector = new ModeSelector(communicationAgent);
    }

    public CommunicationAgent getCommunicationAgent() {
        return communicationAgent;
    }

    public ModeSelector getModeSelector() {
        return modeSelector;
    }
}
