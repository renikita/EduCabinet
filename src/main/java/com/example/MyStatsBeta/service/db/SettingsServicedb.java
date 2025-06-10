package com.example.MyStatsBeta.service.db;

import com.example.MyStatsBeta.model.Settings;
import com.example.MyStatsBeta.modelparent.User;
import com.example.MyStatsBeta.repository.SettingsRepository;
import com.example.MyStatsBeta.service.SettingsService;
import com.example.MyStatsBeta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServicedb implements SettingsService {

    @Autowired
    SettingsRepository settingsRepository;
    @Autowired
    private UserService userService;

    @Override
    public Settings save(Settings settings) {
        return settingsRepository.save(settings);
    }

    @Override
    public Settings findById(Integer id) {
        return settingsRepository.findById(id).orElse(null);
    }

    @Override
    public Settings findByUserId(Integer userId) {
        return settingsRepository.findByUserId(userId);
    }

    @Override
    public Settings updateSettings(Integer userId, Settings newSettings) {
        Settings settings = settingsRepository.findByUserId(userId);
        if (settings != null) {
            settings.setCurrentTheme(newSettings.getCurrentTheme());
            settings.setLanguage(newSettings.getLanguage());
            settings.setNotificationsEnabled(newSettings.isNotificationsEnabled());
            return settingsRepository.save(settings);
        }
        else{
            return null;
        }
    }

    @Override
    public Settings changeTheme(Integer userId, String Theme) {
        Settings settings = settingsRepository.findByUserId(userId);
        if (settings != null) {
            Settings.Theme theme = Settings.Theme.valueOf(Theme.toUpperCase());
            settings.setCurrentTheme(theme);
            return settingsRepository.save(settings);
        }
        else {
            return null;
        }
    }

    @Override
    public Settings changeLanguage(Integer userId, String language) {
        Settings settings = settingsRepository.findByUserId(userId);
        if (settings != null) {
            settings.setLanguage(language);
            return settingsRepository.save(settings);
        }
        else {
            return null;
        }
    }

    @Override
    public Settings changeNotifications(Integer userId, boolean notificationsEnabled) {
       Settings settings = settingsRepository.findByUserId(userId);
    if (settings != null) {
            settings.setNotificationsEnabled(notificationsEnabled);
            return settingsRepository.save(settings);
        }
        else {

        return null;
    }
    }

    @Override
    public Settings setDefaultSettings(Integer userId) {
        Settings settings = new Settings();
        User user = userService.findById(userId).orElse(null);
        settings.setUser(user);
        settings.setCurrentTheme(Settings.Theme.LIGHT);
        settings.setLanguage("en");
        settings.setNotificationsEnabled(true);
        return settingsRepository.save(settings);
    }
}
