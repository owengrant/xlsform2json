package com.geoideas.ds.xls2json;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;


public class XLSReader {
    private Form form;
    private File file;
    private XSSFWorkbook wb;

    public XLSReader(File file) {
        this.form = new Form();
        this.file = file;
    }

    public Form extractForm() throws IOException, InvalidFormatException {
        wb = new XSSFWorkbook(file);
        extractSurveySheet(wb.getSheet("survey"));
        extractChoiceSheet(wb.getSheet("choices"));
        extractSettingSheet(wb.getSheet("settings"));
        return form;
    }

    private void extractChoiceSheet(XSSFSheet sheet) {
        var listNameColumn = -1;
        var nameColumn= -1;
        var labelColunm = -1;
        var firstRow = sheet.getRow(sheet.getFirstRowNum());
        var itCell = firstRow.cellIterator();
        while(itCell.hasNext()) {
            var cell = itCell.next();
            var text = cell.getStringCellValue().toLowerCase().split("::")[0].trim();
            switch (text) {
                case "list_name", "list name" -> listNameColumn = cell.getColumnIndex();
                case "name" -> nameColumn = cell.getColumnIndex();
                case "label" -> labelColunm = cell.getColumnIndex();
            }
        }
        var rowIt = sheet.rowIterator();
        // skip first
        rowIt.next();
        while(rowIt.hasNext()) {
            form.addChoiceField(extractChoiceRow(rowIt.next(), listNameColumn, nameColumn, labelColunm));
        }
    }

    private void extractSurveySheet(XSSFSheet sheet) {
        var typeColumn = -1;
        var nameColumn= -1;
        var labelColunm = -1;
        var firstRow = sheet.getRow(sheet.getFirstRowNum());
        var itCell = firstRow.cellIterator();
        while(itCell.hasNext()) {
            var cell = itCell.next();
            var text = cell.getStringCellValue().toLowerCase().split("::")[0].trim();
            switch (text) {
                case "type" -> typeColumn = cell.getColumnIndex();
                case "name" -> nameColumn = cell.getColumnIndex();
                case "label" -> labelColunm = cell.getColumnIndex();
            }
        }
        var rowIt = sheet.rowIterator();
        // skip first
        rowIt.next();
        while(rowIt.hasNext()) {
            form.addSurveyField(extractSurveyRow(rowIt.next(), typeColumn, nameColumn, labelColunm));
        }
    }

    private void extractSettingSheet(XSSFSheet sheet) {
        var idIndex = -1;
        var titleIndex = -1;
        var versionIndex = -1;
        var urlIndex = -1;
        var keyIndex = -1;
        var languageIndex = -1;
        var firstRow = sheet.getRow(sheet.getFirstRowNum());
        var itCell = firstRow.cellIterator();
        while(itCell.hasNext()) {
            var cell = itCell.next();
            var text = cell.getStringCellValue().toLowerCase().split("::")[0].trim();
            switch (text) {
                case "form_id" -> idIndex = cell.getColumnIndex();
                case "form_title" -> titleIndex = cell.getColumnIndex();
                case "version" -> versionIndex = cell.getColumnIndex();
                case "public_key" -> keyIndex = cell.getColumnIndex();
                case "submission_url" -> urlIndex = cell.getColumnIndex();
                case "default_language" -> languageIndex = cell.getColumnIndex();
            }
        }
        var rowIt = sheet.rowIterator();
        rowIt.next();
        while(rowIt.hasNext()) {
            var row = rowIt.next();
            var field = extractSettingRow(row, idIndex, titleIndex, versionIndex, urlIndex, keyIndex, languageIndex);
            form.addSettingField(field);
        }
    }

    private SettingField extractSettingRow(Row row, int ii, int ti, int vi, int ui, int ki, int li) {
        var field = new SettingField();
        if(ii >= 0) field.setFormId(extractCell(row.getCell(ii)));
        if(li >= 0) field.setDefaultLanguage(extractCell(row.getCell(li)));
        if(ti >= 0) field.setFormTitle(extractCell(row.getCell(ti)));
        if(vi >= 0) field.setVersion(extractCell(row.getCell(vi)));
        if(ui >= 0) field.setSubmissionUrl(extractCell(row.getCell(ui)));
        if(ki >= 0) field.setPublicKey(extractCell(row.getCell(ki)));
        return field;
    }

    private ChoiceField extractChoiceRow(Row row, int listNameIndex, int nameIndex, int labelIndex) {
        var listName = extractCell(row.getCell(listNameIndex));
        var name = extractCell(row.getCell(nameIndex));
        var label = extractCell(row.getCell(labelIndex));
        return new ChoiceField(listName, name, label);
    }

    private SurveyField extractSurveyRow(Row row, int listNameIndex, int nameIndex, int labelIndex) {
        var type = extractCell(row.getCell(listNameIndex));
        var name = extractCell(row.getCell(nameIndex));
        var label = extractCell(row.getCell(labelIndex));
        return new SurveyField("/"+name, label, type);
    }

    private String extractCell(Cell cell) {
        if(cell == null) return null;
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

}
