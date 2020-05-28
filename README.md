# xlsform2json
Library for converting XLSForms to a JSON representation

### Example

```java
    // load xlsx file
    var reader = new XLSReader(new File("../demo.xlsx"));
    // process xlsform
    var form = reader.extractForm();
    System.out.println("Survey Sheet");
    // form.getSurvey() maps to the survey sheet
    form.getSurvey().values().forEach(System.out::println);
    System.out.println("Choices Sheet");
    // forms.getChoices() maps to the choices sheet
    form.getChoices().values().forEach(list -> list.forEach(System.out::println));
    System.out.println("Settings Sheet");
    // form.getSettings() maps to the settings sheet
    form.getSettings().forEach(System.out::println);
    System.out.println("----------------------------------------------------------");
    System.out.println("Survey Sheet");
    // form.normaliseNames() returns a new form where names are modified to include parent paths
    var newForm = form.normaliseNames();
    newForm.getSurvey().values().forEach(System.out::println);
    System.out.println("----------------------------------------------------------");
    System.out.println("Choices Sheet");
    newForm.getChoices().values().forEach(list -> list.forEach(System.out::println));
    System.out.println("----------------------------------------------------------");
    // convert Form to JSON representation
    var exporter = new FormExporter(form.normaliseNames());
    System.out.println(exporter.toJson());
```
