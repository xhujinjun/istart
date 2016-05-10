package com.istart.framework.service.impl;

import com.istart.framework.service.VersionService;
import com.istart.framework.domain.Version;
import com.istart.framework.repository.VersionRepository;
import com.istart.framework.repository.search.VersionSearchRepository;
import com.istart.framework.web.rest.dto.VersionDTO;
import com.istart.framework.web.rest.mapper.VersionMapper;
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
 * Service Implementation for managing Version.
 */
@Service
@Transactional
public class VersionServiceImpl implements VersionService{

    private final Logger log = LoggerFactory.getLogger(VersionServiceImpl.class);
    
    @Inject
    private VersionRepository versionRepository;
    
    @Inject
    private VersionMapper versionMapper;
    
    @Inject
    private VersionSearchRepository versionSearchRepository;
    
    /**
     * Save a version.
     * 
     * @param versionDTO the entity to save
     * @return the persisted entity
     */
    public VersionDTO save(VersionDTO versionDTO) {
        log.debug("Request to save Version : {}", versionDTO);
        Version version = versionMapper.versionDTOToVersion(versionDTO);
        version = versionRepository.save(version);
        VersionDTO result = versionMapper.versionToVersionDTO(version);
        versionSearchRepository.save(version);
        return result;
    }

    /**
     *  Get all the versions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Version> findAll(Pageable pageable) {
        log.debug("Request to get all Versions");
        Page<Version> result = versionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one version by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public VersionDTO findOne(Long id) {
        log.debug("Request to get Version : {}", id);
        Version version = versionRepository.findOne(id);
        VersionDTO versionDTO = versionMapper.versionToVersionDTO(version);
        return versionDTO;
    }

    /**
     *  Delete the  version by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Version : {}", id);
        versionRepository.delete(id);
        versionSearchRepository.delete(id);
    }

    /**
     * Search for the version corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Version> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Versions for query {}", query);
        return versionSearchRepository.search(queryStringQuery(query), pageable);
    }
}
