package com.geoideas.ds.xls2json;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.util.*;
import java.util.stream.Collectors;

public class Form {
    private Map<String,SurveyField> survey;
    private Map<String, List<ChoiceField>> choices;
    private List<SettingField> settings;

    public Form() {
        survey = new HashMap<>();
        choices = new HashMap<>();
        settings = new ArrayList<>();
    }

    public SurveyField addSurveyField(SurveyField field) {
        var type = field.getType();
        if(type == null || type.isBlank() || type.equals("/null")) return null;
        field.setIndex(survey.size());
        if(type.toLowerCase().startsWith("end")) {
            field.setName(type.toLowerCase()+":"+field.getIndex());
        }
        survey.put(field.getName(), field);
        return field;
    }

    public ChoiceField addChoiceField(ChoiceField field) {
        var listName = field.getListName();
        if(listName == null || listName.isBlank() || listName.equals("/null")) return null;
        var sf = StreamEx.ofValues(survey).filter(f -> f.getType().startsWith("select"))
                .findFirst(f -> f.getType().endsWith(" "+listName));
        if(sf.isEmpty()) return null;
        var realListName = sf.get().getName();
        if(!choices.containsKey(realListName)) {
            choices.put(realListName, new ArrayList<>());
        }
        choices.get(realListName).add(field);
        return field;
    }

    public SettingField addSettingField(SettingField field) {
        if(emptySettingsField(field)) return null;
        settings.add(field);
        return field;
    }

    public Form normaliseNames() {
        var form =  new Form();
        form.survey =  completeSurvey();
        form.choices = completeChoices(form);
        form.settings = settings;
        return form;
    }

    private boolean emptySettingsField(SettingField field) {
        var id = field.getFormId();
        var title = field.getFormTitle();
        var version = field.getVersion();
        var key = field.getPublicKey();
        var lang = field.getDefaultLanguage();
        var url = field.getSubmissionUrl();
        return isEmpty(id, title, version, key, lang, url);
    }

    private boolean isEmpty(String... list) {
        for (var input: list)
            if(!(input == null || input.isBlank() || input.equals("/null")))
                return false;
        return true;
    }

    private HashMap<String, SurveyField> completeSurvey() {
        var sorted = survey.values()
                .stream()
                .sorted((f1, f2) -> f1.getIndex() - f2.getIndex())
                .collect(Collectors.toList());
        Queue<SurveyField> groups = new LinkedList<>();
        var newSurvey = new HashMap<String, SurveyField>();
        for(var i = 0; i < sorted.size(); i++) {
            var field = sorted.get(i);
            var newField = new SurveyField(field.getName(), field.getLabel(), field.getType());
            newField.setIndex(field.getIndex());
            if(!groups.isEmpty()) {
                var group = groups.peek();
                newField.setParent(group.getName())
                        .setName(newField.getParent()+newField.getName());
            }
            newSurvey.put(newField.getName(), newField);
            var type = newField.getType().toLowerCase();
            if(type.startsWith("begin")) {
                groups.add(newField);
            } else if(type.startsWith("end repeat") || type.startsWith("end group")) {
                groups.remove();
            }
        }
        return newSurvey;
    }

    private HashMap<String, List<ChoiceField>> completeChoices(Form form) {
        var newChoices = new HashMap<String, List<ChoiceField>>();
        EntryStream.of(choices)
                .forEach( entry -> {
                    var opName = extractNameFromSurvey(form, entry.getKey());
                    opName.ifPresent(name -> {
                        var fields = StreamEx.of(entry.getValue()).map(cField ->
                                new ChoiceField(name, cField.getName(), cField.getLabel())
                        ).toList();
                        newChoices.put(name, fields);
                    });
                });
        return newChoices;
    }

    private Optional<String> extractNameFromSurvey(Form form, String listName) {
        return StreamEx.ofKeys(form.survey).filter(name -> name.endsWith(listName)).findFirst();
    }

    public Map<String, SurveyField> getSurvey() {
        return survey;
    }

    public Map<String, List<ChoiceField>> getChoices() {
        return choices;
    }

    public List<SettingField> getSettings() {
        return settings;
    }

    public Form setSurvey(Map<String, SurveyField> survey) {
        this.survey = survey;
        return this;
    }

    public Form setChoices(Map<String, List<ChoiceField>> choices) {
        this.choices = choices;
        return this;
    }

    public Form setSettings(List<SettingField> settings) {
        this.settings = settings;
        return this;
    }
}
