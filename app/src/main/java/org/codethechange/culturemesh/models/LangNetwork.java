package org.codethechange.culturemesh.models;

/**
 * Created by nathaniel on 11/10/17.
 */

public class LangNetwork extends Network {
    private Language lang;

    public LangNetwork(Post[] posts, Event[] events, Location nearLocation, Language lang) {
        super(posts, events, nearLocation);
        this.lang = lang;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }
}
