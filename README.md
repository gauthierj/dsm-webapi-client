# dsm-webapi-client
A java based Synology DSM Webapi client.

Warning : WORK IN PROGRESS

# Build

Simply run: `mvn clean install`

## Integration Tests

Tests requires an actual Synology DSM server running with some existing account, share and folder structure.
Note that integration tests are not run by default. Running the tests requires `it` profile to be activated: `mvn clean install -Pit`

The following Maven properties are used by the integration tests, and can be overriden in Maven `settings.xml` :

| Property                   | Default value     |
|----------------------------|-------------------|
| `test.dsm.webapi.scheme`   | http              |
| `test.dsm.webapi.host`     | diskstation.local |
| `test.dsm.webapi.port`     | 5000              |
| `test.dsm.webapi.username` | dsm-webapi-it     |
| `test.dsm.webapi.password` | dsm-webapi-it     |
| `test.dsm.webapi.session`  | filestation       |
| `test.dsm.webapi.timeZone` | Europe/Brussels   |

# Usage

## Maven dependency

Add the `dsm-webapi-client-filestation` as a Maven dependency :

```xml
<dependency>
  <groupId>net.jacqg</groupId>
  <artifactId>dsm-webapi-client-filestation</artifactId>
  <scope>test</scope>
</dependency>
```

Please note that this project is not deployed into any public Maven repository at this time. The entire project must therefore be deployed to your local / private repository.

## Spring 

This is a Spring based library. It is supposed to be used within Spring based applications.

1. Import class `DsmWebapiClientConfig` in the application configuration
2. Provide implementations of follwing interfaces in the application context

**`DsmUrlProvider`**:  provides the URL of Synology DSM server.

Existing implementation: `PropertiesDsmUrlProvider`

This implementation retrieves the URL based on the following properties: 

- `dsm.webapi.scheme`
- `dsm.webapi.host`
- `dsm.webapi.port`

**`AuthenticationProvider`**: provides login and password

Existing implementation: `PropertiesAuthenticationProvider`

This implementation retrieves connection information based on the following properties:

- `dsm.webapi.username`
- `dsm.webapi.password`
- `dsm.webapi.session`

Existing implementation: `ConsoleAuthenticationProvider`

This implentation asks credentials in the console.

**`TimeZoneProvider`**: provides time zone

Existing implementation: `PropertiesTimeZoneProvider`

Retrieves time zone form the following property:

- `dsm.webapi.timeZone`

Existing implementation: `DefaultTimeZoneProvider`

Retrieves system default time zone.

`PropertySourcesPlaceholderConfigurer` if any  `PropertiesXXXProvider` is used.

**Example configuration**

```java
@Configuration
@Import(DsmWebapiClientConfig.class)
public class MyAppConfig {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new PropertiesAuthenticationProvider();
    }

    @Bean
    public DsmUrlProvider urlProvider() {
        return new PropertiesDsmUrlProvider();
    }

    @Bean
    public TimeZoneProvider timeZoneProvider() {
        return new PropertiesTimeZoneProvider();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    // ...
    // My app's own beans
    // ...
}
```
Finally, inject services needed in application classes: search for interfaces `XXXService` corresponding to implemented APIs.

```java
public class MyApplicationSerivce {

    @Autowired
    FileListService fileListService;

    // ...
    
    public void doStuff() throws Exception {
        // ...
        List<File> list = fileListService.list("/my-share");
        // ...
    }

    // ...
}
```
Integration tests are a good place to look for samples.

# Project structure and currently implemented APIs:

## Core module (`dsm-webapi-client-core`)
Contains all base code required to easily call DSM webapi.

It also implements the following APIs:
- SYNO.API.Info: `ApiInfoService`
- SYNO.API.Auth: `AuthenticationService`

## File station module (`dsm-webapi-client-filestation`)

Implements FileStation APIs:
- SYNO.FileStation.List: `FileListService` and `ShareListService`
- SYNO.FileStation.Info: `FileStationInformationService`
- SYNO.FileStation.Search: `SearchService`

Future development :
- Missing FileStation APIs
- DownloadStation APIs
