package com.example.alquranapp.model;

import java.util.List;

public class Definitions {

    String definition = "";
    String example = "";
    List<String>synonym = null;
    List<String>antonym = null;

    public List<String> getSynonym() {
        return synonym;
    }

    public void setSynonym(List<String> synonym) {
        this.synonym = synonym;
    }

    public List<String> getAntonym() {
        return antonym;
    }

    public void setAntonym(List<String> antonym) {
        this.antonym = antonym;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }


}
