package org.yyc.qqconnect.enumClass;

public enum Mode {

    GROUP("群聊"), FRIEND("私聊");

    private final String modeName;

    Mode(String modeName) {
        this.modeName = modeName;
    }

    public String getModeName() {
        return modeName;
    }
}
