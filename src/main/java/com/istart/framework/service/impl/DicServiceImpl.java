package com.istart.framework.service.impl;

import com.istart.framework.service.DicService;
import com.istart.framework.domain.Dic;
import com.istart.framework.repository.DicRepository;
import com.istart.framework.repository.search.DicSearchRepository;
import com.istart.framework.web.rest.dto.DicDTO;
import com.istart.framework.web.rest.mapper.DicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Dic.
 */
@Service
@Transactional
public class DicServiceImpl implements DicService{

    private final Logger log = LoggerFactory.getLogger(DicServiceImpl.class);
    
    @Inject
    private DicRepository dicRepository;
    
    @Inject
    private DicMapper dicMapper;
    
    @Inject
    private DicSearchRepository dicSearchRepository;
    
    /**
     * Save a dic.
     * 
     * @param dicDTO the entity to save
     * @return the persisted entity
     */
    public DicDTO save(DicDTO dicDTO) {
        log.debug("Request to save Dic : {}", dicDTO);
        Dic dic = dicMapper.dicDTOToDic(dicDTO);
        dic = dicRepository.save(dic);
        DicDTO result = dicMapper.dicToDicDTO(dic);
        dicSearchRepository.save(dic);
        return result;
    }

    /**
     *  Get all the dics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Dic> findAll(Pageable pageable) {
        log.debug("Request to get all Dics");
        Page<Dic> result = dicRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one dic by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DicDTO findOne(Long id) {
        log.debug("Request to get Dic : {}", id);
        Dic dic = dicRepository.findOne(id);
        DicDTO dicDTO = dicMapper.dicToDicDTO(dic);
        return dicDTO;
    }

    /**
     *  Delete the  dic by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Dic : {}", id);
        dicRepository.delete(id);
        dicSearchRepository.delete(id);
    }

    /**
     * Search for the dic corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Dic> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dics for query {}", query);
        return dicSearchRepository.search(queryStringQuery(query), pageable);
    }
}
