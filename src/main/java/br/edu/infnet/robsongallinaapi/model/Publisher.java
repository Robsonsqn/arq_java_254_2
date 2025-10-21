package br.edu.infnet.robsongallinaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {
    private Long id;
    private String name;
    private String country;
}