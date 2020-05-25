package com.geoideas.ds.xls2json;

public class SettingField {
    private String formId;
    private String formTitle;
    private String version;
    private String submissionUrl;
    private String publicKey;
    private String defaultLanguage;

    public SettingField() {}

    public SettingField(String formId, String formTitle, String version) {
        this.formId = formId;
        this.formTitle = formTitle;
        this.version = version;
    }

    public String getFormId() {
        return formId;
    }

    public SettingField setFormId(String formId) {
        this.formId = formId;
        return this;
    }

    public String getFormTitle() {
        return formTitle;
    }

    public SettingField setFormTitle(String formTitle) {
        this.formTitle = formTitle;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SettingField setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getSubmissionUrl() {
        return submissionUrl;
    }

    public SettingField setSubmissionUrl(String submissionUrl) {
        this.submissionUrl = submissionUrl;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public SettingField setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public SettingField setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SettingField{");
        sb.append("formId='").append(formId).append('\'');
        sb.append(", formTitle='").append(formTitle).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", submissionUrl='").append(submissionUrl).append('\'');
        sb.append(", publicKey='").append(publicKey).append('\'');
        sb.append(", defaultLanguage='").append(defaultLanguage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
