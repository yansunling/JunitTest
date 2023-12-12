package com.squirrel.tmsp_hand_book_doc.machine;

import com.squirrel.tmsp_hand_book_doc.event.BookDocStatusEvent;

public class EnumTest {
    public static void main(String[] args) {
        BookDocStatusEvent back = BookDocStatusEvent.valueOf("BACK");
        System.out.println(back.getName());
    }
}
