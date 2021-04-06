package com.example.backtracking;

public class Node {
    int v;
    char c;
    public Node(char character, int value) {
        this.c = character;
        this.v = value;
    }
    public void setData(char c, int v) {
        this.c = c;
        this.v = v;
    }

    public char getChar() {
        return this.c;
    }

    public int getValue() {
        return this.v;
    }
}
