package ru.nsu.goncharov.task12;

public class MyLinkedList {

    final ListNode head = new ListNode("", null, null);

    void print() {
        synchronized (head) {
            ListNode helper = head.next;
            while(helper != null) {
                System.out.println(helper.value);
                helper = helper.next;
            }
        }
    }

    void add(String str) {
        synchronized (head) {
            ListNode nwNode = new ListNode(str, head, head.next);
            if (head.next != null) {
                head.next.prev = nwNode;
            }
            head.next = nwNode;
        }
    }

    void sort() {
        synchronized (head) {
            boolean flag = true;
            while(flag && head.next != null) {
                flag = false;
                ListNode cur = head.next;
                ListNode next = cur.next;

                while(next != null) {
                    if (cur.value.compareTo(next.value) > 0) {
                        flag = true;
                        next.prev = cur.prev;
                        cur.next = next.next;
                        if (cur.prev != null) {
                            cur.prev.next = next;
                        }
                        if (next.next != null) {
                            next.next.prev = cur;
                        }
                        next.next = cur;
                        cur.prev = next;

                        next = cur.next;
                    } else {
                        cur = next;
                        next = next.next;
                    }
                }
            }
        }
    }
}
