package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.FileInfoEntity;
import com.helloworld.v1.domain.entity.FileUsage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final EntityManager em;


    public void save(FileInfoEntity file) {
        em.persist(file);
    }

    public FileInfoEntity findOne(String name) {
        return em.find(FileInfoEntity.class, name);
    }

    public List<FileInfoEntity> findByUsage(FileUsage usages){
        return em.createQuery("select f from FileInfoEntity f where f.usages = :usages", FileInfoEntity.class)
                .setParameter("usages", usages)
                .getResultList();
    }

    public void delete(FileInfoEntity fileInfo){
        em.remove(fileInfo);
    }





}