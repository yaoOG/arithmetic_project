package com.yao.offer2;

import java.util.Map;
import java.util.TreeMap;

public class Solution58 {
    class MyCalendar {

        //把时间区间的开始时间作为映射的键，把结束时间作为映射的值
        private TreeMap<Integer,Integer> events;

        public MyCalendar() {
            events = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            Map.Entry<Integer,Integer> event =  events.floorEntry(start);
            if (event != null && event.getValue() > start) {
                return false;
            }
            event =  events.ceilingEntry(start);
            if  (event != null && event.getKey() < end) {
                return false;
            }
            events.put(start,end);
            return true;
        }
    }
}
