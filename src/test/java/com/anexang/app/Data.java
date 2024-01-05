package com.anexang.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Sets;

import com.anexang.app.domain.entity.Employe;

public class Data {
    private Employe employe1 = new Employe(
            1, "Andres",
            "Again",
            "andres@gmail.com",
            "123456", "wewe1", "qweqw", Sets.newHashSet(), "aadad", new Date());

    private List<Employe> data = new ArrayList<>();

    public List<Employe> getData() {
        data.add(employe1);
        return data;
    }

}
