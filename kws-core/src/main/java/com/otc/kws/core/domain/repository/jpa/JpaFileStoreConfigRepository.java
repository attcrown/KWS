package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.FileStoreConfig;

@Repository
public interface JpaFileStoreConfigRepository extends KwsJpaRepository<FileStoreConfig, String>
{

}