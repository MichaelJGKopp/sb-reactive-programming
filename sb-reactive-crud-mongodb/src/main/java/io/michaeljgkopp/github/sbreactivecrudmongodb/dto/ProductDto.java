package io.michaeljgkopp.github.sbreactivecrudmongodb.dto;

public record ProductDto(
    String id,
    String name,
    Integer qty,
    Double price
) {}
