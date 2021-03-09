package com.hcl.capstonebackend.repository;

import com.hcl.capstonebackend.domain.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long> {

    List<Song> getAllByAlbum_Id (Long album_id);
}
