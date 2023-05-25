package com.otc.kws.core.domain.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.entity.BaseKwsEntity;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.repository.jpa.KwsJpaRepository;

public abstract class RepositoryKwsService <R extends KwsJpaRepository<E, ID>, E extends BaseKwsEntity, ID extends Serializable>
{
	protected abstract R getRepository();
	protected abstract ID getId(E entity);
	
	
	public List<E> findAll()
	{
		return getRepository().findAll();
	}
	
	@Async
	public CompletableFuture<List<E>> findAllAsync()
	{
		return CompletableFuture.completedFuture(findAll());
	}
	
	
	public Page<E> findAll(Pageable pageable)
	{
		return getRepository().findAll(pageable);
	}
	
	@Async
	public CompletableFuture<Page<E>> findAllAsync(Pageable pageable)
	{
		return CompletableFuture.completedFuture(findAll(pageable));
	}
	
	
	public List<E> findByIds(Collection<ID> ids)
	{
		return getRepository().findAllById(ids);
	}
	
	@Async
	public CompletableFuture<List<E>> findByIdsAsync(Collection<ID> ids)
	{
		return CompletableFuture.completedFuture(findByIds(ids));
	}
	
	
	public E findById(ID id)
	{
		return getRepository().findById(id).orElse(null);
	}
	
	@Async
	public CompletableFuture<E> findByIdAsync(ID id)
	{
		return CompletableFuture.completedFuture(findById(id));
	}
	
	
	public long count()
	{
		return getRepository().count();
	}
	
	@Async
	public CompletableFuture<Long> countAsync()
	{
		return CompletableFuture.completedFuture(count());
	}
	
	
	@Transactional
	public E create(E entity) throws DuplicateEntityException
	{
		if(findById(getId(entity)) != null) {
			throw new DuplicateEntityException("Duplication Entity For Id ["+getId(entity)+"]");
		}
		return getRepository().save(entity);
	}
	
	@Async
	@Transactional
	public CompletableFuture<E> createAsync(E entity) throws DuplicateEntityException
	{
		return CompletableFuture.completedFuture(create(entity));
	}
	
	
	@Transactional
	public E update(E entity) throws EntityNotFoundException
	{
		if(getId(entity) == null || findById(getId(entity)) == null) {
			throw new EntityNotFoundException("Entity With Id ["+getId(entity)+"] Not Found");
		}
		return getRepository().save(entity);
	}
	
	@Async
	@Transactional
	public CompletableFuture<E> updateAsync(E entity) throws EntityNotFoundException
	{
		return CompletableFuture.completedFuture(update(entity));
	}
	
	
	@Transactional
	public E removeById(ID id) throws EntityNotFoundException
	{
		E removedEntity = findById(id);
		if(removedEntity == null) {
			throw new EntityNotFoundException("Entity With Id ["+id+"] Not Found");
		}
		getRepository().delete(removedEntity);
		return removedEntity;
	}
	
	@Async
	@Transactional
	public CompletableFuture<E> removeByIdAsync(ID id) throws EntityNotFoundException
	{
		return CompletableFuture.completedFuture(removeById(id));
	}
}