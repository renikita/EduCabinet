package com.example.MyStatsBeta.repository;

import com.example.MyStatsBeta.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface SettingsRepository extends JpaRepository<Settings, Integer> {
    Settings findByUserId(Integer userId);
}
