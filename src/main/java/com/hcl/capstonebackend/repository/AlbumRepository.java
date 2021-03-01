package com.hcl.capstonebackend.repository;

import com.hcl.capstonebackend.domain.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    Album findByTitle(String title);
}
