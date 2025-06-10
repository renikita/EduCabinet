package com.example.MyStatsBeta.service;

import com.example.MyStatsBeta.model.Settings;
import org.springframework.stereotype.Service;

@Service
public interface SettingsService {

    Settings save(Settings settings);
    Settings findById(Integer id);
    Settings findByUserId(Integer userId);
    Settings updateSettings(Integer userId, Settings newSettings);
    Settings changeTheme(Integer userId, String theme);
    Settings changeLanguage(Integer userId, String language);
    Settings changeNotifications(Integer userId, boolean notificationsEnabled);
    //set default settings for new user
    Settings setDefaultSettings(Integer userId);


}
