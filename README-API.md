```shell
gradle clean apiFeatures
````
Command to run api features with tag

```shell
gradle clean apiFeatures -Ptags="@Board"
```
Allure report commands
Place yourself on path that allure-results exists.

Command to generate report and serve it using tmp path
```shell
allure serve
```

Command to generate allure-report folder that contains html file of the report.
```shell
allure generate
```

Comand running
gradle build
gradle executeApiFeature
gradle apiFeature

**********************
Report
cd trello-api = reubicarse en la carpeta q contiene la carpeta "allure" o "allure-result"
allure serve
"allure-report" file
allure generate

upload el allure-report file
allure generate --clean