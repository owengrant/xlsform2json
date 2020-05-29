package com.geoideas.ds.xls2json;

public class SurveyField {
    private String name;
    private String label;
    private String type;
    private String parent;
    private int index;

    public SurveyField() {
    }

    public SurveyField(String name, String label, String type) {
        this.name = name;
        this.label = label;
        this.type = type;
    }

    public SurveyField(int index, String name, String label, String type) {
        this(name, label, type);
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public SurveyField setName(String name) {
        this.name = name;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public SurveyField setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getType() {
        return type;
    }

    public SurveyField setType(String type) {
        this.type = type;
        return this;
    }

    public String getParent() {
        return parent;
    }

    public SurveyField setParent(String parent) {
        this.parent = parent;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public SurveyField setIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FormField{");
        sb.append("name='").append(name).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", parent='").append(parent).append('\'');
        sb.append(", index=").append(index);
        sb.append('}');
        return sb.toString();
    }
}
