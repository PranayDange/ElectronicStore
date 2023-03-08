package com.lcwd.electronic.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(15);
        list.add(16);
        list.add(16);
        list.add(16);
        list.add(16);
        list.add(16);

        List<Integer> collect = list.stream().map(t -> t * t).collect(Collectors.toList());
        System.out.println(collect);

        List<Integer> collect1 = list.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
        System.out.println(collect1);

        Collections.sort(list);
        System.out.println(list);

        //Web server failed to start. Port 9090 was already in use.
        //sudo lsof -i tcp:9090
        //sudo kill 53297 ----53297 is a pid number

    }
}
