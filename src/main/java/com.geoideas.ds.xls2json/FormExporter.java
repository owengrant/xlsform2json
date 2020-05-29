package com.geoideas.ds.xls2json;

import one.util.streamex.StreamEx;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.function.Function;

public class FormExporter {
    private Form form;
    private String fileName;

    public FormExporter(Form form, String fileName) {
        this.form = form;
        this.fileName = fileName;
    }

    public String toJson() {
        var json = new JSONObject()
                .put("survey", surveyToJson())
                .put("choices", choicesToJson())
                .put("settings", settingToJson());
        return json.toString();
    }

    private JSONObject surveyToJson() {
        Function<SurveyField, JSONObject> toJson = field ->
            new JSONObject()
                    .put("parent", field.getParent())
                    .put("name", field.getName())
                    .put("type", field.getType().startsWith("select") ? field.getType().split(" ")[0] : field.getType())
                    .put("label", field.getLabel())
                    .put("index", field.getIndex());

        var json = new JSONObject();
        form.getSurvey().forEach((key, field) -> json.put(key, toJson.apply(field)));
        return  json;
    }

    private JSONObject choicesToJson() {
        Function<ChoiceField, JSONObject> toJson = field ->
                new JSONObject()
                    .put("listName", field.getListName())
                    .put("name", field.getName())
                    .put("label", field.getLabel());
        var json = new JSONObject();
        form.getChoices().forEach((key, fields) -> {
            var items = StreamEx.of(fields).map(toJson::apply).toList();
            json.put(key, new JSONArray(items));
        });
        return json;
    }

    private JSONObject settingToJson() {
        var settings = form.getSettings().get(0);
        var id = settings.getFormId();
        var title = settings.getFormTitle();
        var formId = id == null || id.isBlank() ? fileName : id;
        var formTitle = title == null || title.isBlank() ? formId : title;
        return new JSONObject()
            .put("formId", formId)
            .put("formTitle", formTitle)
            .put("version", settings.getVersion());
    }
}
