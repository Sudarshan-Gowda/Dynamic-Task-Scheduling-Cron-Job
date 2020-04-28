package com.star.sud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.star.sud.model.TConfiguration;

public interface ConfigurationRepository extends JpaRepository<TConfiguration, String> {

}
