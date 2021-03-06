package com.antbox.rfidmachine.dto;

/**
 * Created by DK on 17/5/10.
 */
public class GoodsDto {
    private Long id;

    private String name;

    public GoodsDto(){

    }

    public GoodsDto(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return  name;
    }
}
