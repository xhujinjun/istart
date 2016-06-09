package com.istart.framework.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.istart.framework.domain.Dic;
import com.istart.framework.domain.DicType;
import com.istart.framework.web.rest.dto.DicDTO;
import com.istart.framework.web.rest.search.SearchDic;

/**
 * Service Interface for managing Dic.
 */
public interface DicService {

	/**
	 * Save a dic.
	 * 
	 * @param dicDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	DicDTO save(DicDTO dicDTO);

	/**
	 * Get all the dics.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	Page<Dic> findAll(Pageable pageable);

	/**
	 * 分页动态查询字典
	 * 
	 * @param searchDic
	 * @param pageable
	 * @return
	 */
	Page<Dic> findByPageSearch(final SearchDic searchDic, Pageable pageable);

	/**
	 * Get the "id" dic.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	DicDTO findOne(Long id);

	/**
	 * Delete the "id" dic.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	void delete(Long id);

	/**
	 * Search for the dic corresponding to the query.
	 * 
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	Page<Dic> search(String query, Pageable pageable);

	Dic searchByDb(final SearchDic searchDic);

	DicType searchByDicTypeCodeAndDicCode(final String dicTypeCode, final String dicCode);

	List<Dic> searchByDicTypeCode(String dicTypeCode);
}
