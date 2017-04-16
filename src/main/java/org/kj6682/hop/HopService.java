package org.kj6682.hop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import static org.kj6682.hop.Hop.*;


import java.util.List;

/**
 * Created by luigi on 29/08/16.
 *
 * This class is responsible for serving the "business" of the Hop model
 * It must be protocol agnostic and bridge the model stored in a repository to the client interface
 *
 */
@Service
class HopService {


    private HopRepository hopRepository;

    @Autowired
    void setHopRepository(HopRepository hopRepository){

        this.hopRepository = hopRepository;
    }

    Hop findOne(Long id) {
        Assert.notNull(id, "A reasonable id is necessary when searching for one specific Hop");
        return hopRepository.findById(id)
                .orElse(new Hop(UNKNOWN_TITLE,
                                UNKNOWN_AUTHOR,
                                UNKNOWN_TYPE,
                                UNKNOWN_LOCATION));

    }

    List<Hop> find(String search4me, Pageable pageable) {

        if (StringUtils.isEmpty(search4me)) {
            return findAll(pageable);
        }

        if (pageable == null){
            return hopRepository.searchByAuthorOrTitle(search4me);
        }

        return hopRepository.searchByAuthorOrTitle(search4me, pageable).getContent();
    }

    List<Hop> findAll(Pageable pageable) {
        if (pageable == null){
            return hopRepository.findAll();
        }
        return hopRepository.findAll(pageable).getContent();
    }

    Page<Hop> pagedFindAll(Pageable pageable) {

        return hopRepository.findAll( pageable );
    }

    Hop insertOne(String title, String author, String type, String location) {

        Hop hop = new Hop(title, author, type, location);
        Assert.notNull(hop);

        Hop result = hopRepository.save(hop);
        Assert.notNull(result);

        return result;
    }

    void deleteOne(Long id) {
        Assert.notNull(id, "A reasonable id is necessary when searching for one specific Hop");
        hopRepository.delete(id);
    }

}//:)

