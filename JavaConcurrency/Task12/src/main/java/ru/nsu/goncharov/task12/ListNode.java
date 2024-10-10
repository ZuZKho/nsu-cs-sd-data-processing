package ru.nsu.goncharov.task12;

public class ListNode {
    ListNode next;
    ListNode prev;
    String value;

    public ListNode(String str, ListNode prev, ListNode next) {
        this.next = next;
        this.prev = prev;
        this.value = str;
    }
}
