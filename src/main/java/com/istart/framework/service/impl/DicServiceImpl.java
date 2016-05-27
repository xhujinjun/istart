package com.istart.framework.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istart.framework.domain.Dic;
import com.istart.framework.domain.DicType;
import com.istart.framework.repository.DicRepository;
import com.istart.framework.repository.search.DicSearchRepository;
import com.istart.framework.service.DicService;
import com.istart.framework.web.rest.dto.DicDTO;
import com.istart.framework.web.rest.mapper.DicMapper;
import com.istart.framework.web.rest.search.SearchDic;

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

    @Override
    @Transactional(readOnly = true) 
    public Page<Dic> findByPageSearch(final SearchDic searchDic,Pageable pageable) {
        log.debug("Request to get all Dics");
        Page<Dic> result = dicRepository.findAll(this.getSpecification(searchDic), pageable);
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
        Dic dic = dicRepository.findById(id);
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
    
    /**
     * 根据字典类型代码和字典代码动态查询对应的字典（测试用）
     */
    @Transactional(readOnly = true)
    @Override
    public Dic searchByDb(final SearchDic searchDic) {
        log.debug("Request to search for a page of Dics for query {}", searchDic);
        return dicRepository.findOne(getSpecification(searchDic));
    } 
    /**
     * 动态查询字典
     * @param searchDic
     * @return
     */
    private Specification<Dic> getSpecification(final SearchDic searchDic){
    	return new Specification<Dic>() {
			@Override
			public Predicate toPredicate(Root<Dic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<Object, Object> join = root.join("dicType",JoinType.LEFT);
				
				List<Predicate> list = new ArrayList<Predicate>();
				
				String dicTypeCode = searchDic.getDicTypeCode();
				if(StringUtils.isNoneBlank(dicTypeCode)){
					Path<String> expDicTypeCode = join.get("dicTypeCode");
					list.add(cb.like(expDicTypeCode, dicTypeCode + "%"));
				}
				String dicTypeName = searchDic.getDicTypeName();
				if(StringUtils.isNoneBlank(dicTypeName)){
					Path<String> expDicTypeName = join.get("dicTypeName");
					list.add(cb.like(expDicTypeName, dicTypeName+ "%"));
				}
				
				String dicCode = searchDic.getDicCode();
				if (StringUtils.isNoneBlank(dicCode)) {
					Path<String> expDicCode = root.get("dicCode");
					list.add(cb.like(expDicCode, dicCode+ "%"));
				}
				String dicName = searchDic.getDicName();
				if (StringUtils.isNoneBlank(dicName)) {
					Path<String> expDicName = root.get("dicName");
					list.add(cb.like(expDicName, dicName+ "%"));
				}
				
				list.add(cb.equal(root.get("dataStatus"), "1"));
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
    }

	@Override
	public DicType searchByDicTypeCodeAndDicCode(String dicTypeCode, String dicCode) {
		return dicRepository.findByDicTypeCodeAndDicCode(dicTypeCode,dicCode);
	}
}
