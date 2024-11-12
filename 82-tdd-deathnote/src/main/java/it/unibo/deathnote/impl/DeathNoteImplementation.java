package it.unibo.deathnote.impl;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.unibo.deathnote.api.*;

public class DeathNoteImplementation implements DeathNote {

    static private final int GETTER = 1;
    static private final int SETTER = 2;
    

    private final List<DeathPerson> deathPersons = new ArrayList<>();
    private long time;

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("the ruleNumber passed as argument is not >1 && <number of rules");
        }
        return RULES.get(ruleNumber);
    }

    private class DeathPerson {
        private final String name;
        private String cause;
        private String detailes;
        
        public DeathPerson(String name) {
            this.name = name;
            this.cause = "";
            this.detailes = "";
        }

        public String getName() {
            return this.name;
        }

        public String getCause() {
            return this.cause;
        }

        public String getDetailes() {
            return this.detailes;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public void setDetailes(String detailes) {
            this.detailes = detailes;
        }
    }

    @Override
    public void writeName(String name) {
        if (name == "") {
            throw new NullPointerException("the name can's be null");
        }
        
        deathPersons.add(new DeathPerson(name));
        time = System.nanoTime();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (cause == "") {
            throw new NullPointerException("the cause can's be an empty String");
        } else if (deathPersons.isEmpty()) { 
            throw new IllegalStateException("The DeathNote is empty");
        }
        
        if(System.nanoTime() - time < 40) {
            deathPersons.getLast().setCause(cause);
            return true;
        }

        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if (details == "") {
            throw new IllegalStateException("the details can's be an empty String");
        } if (deathPersons.isEmpty()) { 
            throw new IllegalStateException("The DeathNote is empty");
        }
        
        if(System.nanoTime() - time < 6040) {
            deathPersons.getLast().setDetailes(details);
            return true;
        }

        return false;
    }

    @Override
    public String getDeathCause(String name) {
        checkName(name);  
        for(DeathPerson elem : deathPersons) {
            if (elem.getName().equals(name)) {
                return elem.getCause();
            }
        }
        return "heart attack";
    }

    @Override
    public String getDeathDetails(String name) {
        checkName(name);
        for(DeathPerson elem : deathPersons) {
            if (elem.getName().equals(name)) {
                return elem.getDetailes();
            }
        }
        return "";
    }

    @Override
    public boolean isNameWritten(String name) {
        for(DeathPerson elem : deathPersons) {
            if (elem.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void checkName(String n) {
        if (isNameWritten(n)) {
            throw new IllegalArgumentException(n + " is not in the DeathNote");
        }
        
    }
}