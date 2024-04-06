package de.tuxbe.testtechnique.domain.mapper;

public interface MapperGeneric<K, V>{

    public K toDomain(V v);

    public V toDto(K k);
}
