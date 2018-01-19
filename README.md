[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c8fd28af683448d88afcb40ffb6f27f0)](https://www.codacy.com/app/gauthierj/dsm-webapi-client?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gauthierj/dsm-webapi-client&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/gauthierj/dsm-webapi-client.svg?branch=develop)](https://travis-ci.org/gauthierj/dsm-webapi-client)

# dsm-webapi-client

A java based Synology DSM Webapi client.

Latest stable release is **1.0.0-M1**. It allows all basic files operation.

Roadmap :
- **1.0.0-M2**: extract Spring configuration in a separate project and allow to use the framework without Spring at all.
- **1.0.0-FINAL**: missing FileStation APIs (Favorites, thumbnail, MD5, compress / uncompress).
- **2.0.0-FINAL**: DownloadStation APIs.

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
  <groupId>com.github.gauthierj.dsm-webapi-client</groupId>
  <artifactId>dsm-webapi-client-filestation</artifactId>
  <version>1.0.0-M1</version>
</dependency>
```

The project is available in the following public repository:

```xml
<repository>
    <snapshots>
        <enabled>false</enabled>
    </snapshots>
    <id>bintray-gauthierj-maven</id>
    <name>bintray</name>
    <url>http://dl.bintray.com/gauthierj/maven</url>
</repository>
```

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
- SYNO.FileStation.Download: `DownloadService`
- SYNO.FileStation.Upload: `UploadService`
- SYNO.FileStation.CreateFolder: `CreateFolderService`
- SYNO.FileStation.CopyMove: `CopyMoveService`
- SYNO.FileStation.Delete: `DeleteService`
- SYNO.FileStation.DirSize: `DirSizeService`
- SYNO.FileStation.Rename: `RenameService`

Future development :
- Missing FileStation APIs
- DownloadStation APIs
