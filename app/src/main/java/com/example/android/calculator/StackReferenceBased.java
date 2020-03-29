package com.example.android.calculator;

public class StackReferenceBased {

    //Global variables
    private Object[] items;
    private int top;

    //Create initial stack with 10 null spots
    public void createStack() {
        this.items = new Object[10];
        this.top = -1;
    }

    //If the stack needs to exceed 10, this method creates a new stack with double size and sets original stack pointing at new stack
    private void DoubleCapacity() {
        Object[] newItems = new Object[2*this.items.length];
        for (int i = 0; i<this.items.length; i++){
            newItems[i] = items[i];
        }
        items = newItems;
    }

    //Checks if stack is empty
    public boolean isEmpty () {
        if (top == -1)
            return true;
        return false;
    }

    //Adds a new item to the top position
    public void push(Object newItem) {
        //if capacity is reached, call method to couple capacity
        if (top == items.length-1)
            DoubleCapacity();
        top++;
        items[top] = newItem;
    }

    //Removes the top existing item and returns that top value
    public Object pop() {
        Object top1 = items[top];
        items[top] = null;
        top--;
        return top1;
    }

    //Removes all remaining objects left in stack
    public void popAll() {
        while (top != -1) {
            items[top] = null;
            top--;
        }
    }

    //Returns value of top value in stack
    public Object peek() {
        return this.items[this.top];
    }
}
