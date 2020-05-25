package com.geoideas.ds.xls2json;

import one.util.streamex.StreamEx;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.function.Function;

public class FormExporter {
    private Form form;

    public FormExporter(Form form) {
        this.form = form;
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
                    .put("name", field.getName())
                    .put("type", field.getType().startsWith("select") ? field.getType().split(" ")[0] : field.getType())
                    .put("label", field.getLabel())
                    .put("parent", field.getParent())
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
        return new JSONObject()
            .put("formId", settings.getFormId())
            .put("formTitle", settings.getFormTitle())
            .put("version", settings.getVersion());
    }
}
