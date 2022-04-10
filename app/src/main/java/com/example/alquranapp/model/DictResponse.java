package com.example.alquranapp.model;

import java.util.List;

public class DictResponse {

    String word = "";
    List<Phoenetics> phonetics = null;
    List<Meanings>meanings = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Phoenetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phoenetics> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Meanings> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meanings> meanings) {
        this.meanings = meanings;
    }
}
