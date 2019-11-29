package cn.iba8.module.generator.service;

public interface I18nService {

    void loadI18n();

    void processI18nFileOrigin();

    void compensateToLanguage();

    void compensateToCode();

}
