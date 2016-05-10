package com.istart.framework.service.impl;

import com.istart.framework.service.SysresService;
import com.istart.framework.domain.Sysres;
import com.istart.framework.repository.SysresRepository;
import com.istart.framework.repository.search.SysresSearchRepository;
import com.istart.framework.web.rest.dto.SysresDTO;
import com.istart.framework.web.rest.mapper.SysresMapper;
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
 * Service Implementation for managing Sysres.
 */
@Service
@Transactional
public class SysresServiceImpl implements SysresService{

    private final Logger log = LoggerFactory.getLogger(SysresServiceImpl.class);
    
    @Inject
    private SysresRepository sysresRepository;
    
    @Inject
    private SysresMapper sysresMapper;
    
    @Inject
    private SysresSearchRepository sysresSearchRepository;
    
    /**
     * Save a sysres.
     * 
     * @param sysresDTO the entity to save
     * @return the persisted entity
     */
    public SysresDTO save(SysresDTO sysresDTO) {
        log.debug("Request to save Sysres : {}", sysresDTO);
        Sysres sysres = sysresMapper.sysresDTOToSysres(sysresDTO);
        sysres = sysresRepository.save(sysres);
        SysresDTO result = sysresMapper.sysresToSysresDTO(sysres);
        sysresSearchRepository.save(sysres);
        return result;
    }

    /**
     *  Get all the sysres.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Sysres> findAll(Pageable pageable) {
        log.debug("Request to get all Sysres");
        Page<Sysres> result = sysresRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one sysres by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SysresDTO findOne(Long id) {
        log.debug("Request to get Sysres : {}", id);
        Sysres sysres = sysresRepository.findOne(id);
        SysresDTO sysresDTO = sysresMapper.sysresToSysresDTO(sysres);
        return sysresDTO;
    }

    /**
     *  Delete the  sysres by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sysres : {}", id);
        sysresRepository.delete(id);
        sysresSearchRepository.delete(id);
    }

    /**
     * Search for the sysres corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Sysres> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sysres for query {}", query);
        return sysresSearchRepository.search(queryStringQuery(query), pageable);
    }
}
