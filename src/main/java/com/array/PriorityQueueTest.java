package com.array;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {


    public static void main(String[] args) {

        MedianFinder finder=new MedianFinder();
        finder.addNum(1);
        finder.addNum(5);
        finder.addNum(4);
        finder.addNum(2);
        System.out.println(finder.findMedian());

    }


    static class MedianFinder {
        private PriorityQueue<Integer> small;

        private PriorityQueue<Integer> large;

        public MedianFinder() {
            small = new PriorityQueue<>(Comparator.reverseOrder()); // max heap
            large = new PriorityQueue<>(); // min heap
        }

        public void addNum(int num) {
            if (small.size() <= large.size()) {
                large.offer(num);
                small.offer(large.poll());
            } else {
                small.offer(num);
                large.offer(small.poll());
            }
        }

        public double findMedian() {
            if (small.size() > large.size()) {
                return small.peek();
            } else {
                return (small.peek() + large.peek()) / 2.0;
            }
        }

    }
}