package com.hcl.capstonebackend.repository;

import com.hcl.capstonebackend.domain.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Long> {
}
