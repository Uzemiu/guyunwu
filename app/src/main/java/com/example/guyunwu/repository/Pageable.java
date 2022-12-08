package com.example.guyunwu.repository;

import org.xutils.db.Selector;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pageable {

    private int page;

    private int size;

    private List<Selector.OrderBy> orderByList;

    public Pageable(){
        page = 1;
        size = 10;
        orderByList = new ArrayList<>(1);
        orderByList.add(new Selector.OrderBy("id", true));
    }

    public int getOffset(){
        return (page - 1) * size;
    }
}
