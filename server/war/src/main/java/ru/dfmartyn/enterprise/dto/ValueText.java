package ru.dfmartyn.enterprise.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ValueText {

    @NotNull(message = "id не заполнено!!!")
    private Integer id;

    @Size(min = 2, message = "Для имени минимально 2 символа")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
