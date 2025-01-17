package com.application.rest.dtos;

import com.application.rest.entities.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MakerDTO {

    private Long id;
    private String name;
    private List<Product> products = new ArrayList<>();
}
