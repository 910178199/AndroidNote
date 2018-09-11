package com.tools.apps.wallpaper.DesignPattern;

import com.tools.apps.wallpaper.DesignPattern.builder.Person;

public class test {

    private void Test(){
        /**
         * 建造者模式
         */
        new Person.Builder(1,"")
                .address("")
                .age(1)
                .desc("")
                .build();
    }
    
}
