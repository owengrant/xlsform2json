package com.geoideas.ds.xls2json;

public class ChoiceField {
    private String listName;
    private String name;
    private String label;

    public ChoiceField() {
    }

    public ChoiceField(String listName, String name, String label) {
        this.listName = listName;
        this.name = name;
        this.label = label;
    }

    public String getListName() {
        return listName;
    }

    public ChoiceField setListName(String listName) {
        this.listName = listName;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChoiceField setName(String name) {
        this.name = name;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ChoiceField setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChoiceField{");
        sb.append("listName='").append(listName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
