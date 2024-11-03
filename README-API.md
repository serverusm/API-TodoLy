```shell
gradle clean apiFeatures
````
Command to run api features with tag

```shell
gradle clean apiFeatures -Ptags="@Project"
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

Command running
```shell
gradle build
gradle executeApiFeature
gradle apiFeature
```

**********************
Command Report Run
cd todoLy-api = reubicarse en la carpeta q contiene la carpeta "allure" o "allure-result"
```shell
allure serve
"allure-report" file
allure generate
```

upload el allure-report file
```shell
allure generate --clean
```