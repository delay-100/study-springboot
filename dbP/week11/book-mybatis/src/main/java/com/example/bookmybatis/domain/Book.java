package com.example.bookmybatis.domain;

import lombok.Data;

public class Book {

    @Data
    public static class Create {
        private String name;
        private String publisher;
        private Integer price=0;
    }

    @Data
    public static class Update {
        private String name;
        private String publisher;
        private Integer price;
    }

    @Data
    public static class Simple {
        private Long id;
        private String name;
        private String publisher;
        private Integer price;
    }


}
