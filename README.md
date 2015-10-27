# dsm-webapi-client
A java based Synology DSM Webapi client.

Warning : WORK IN PROGRESS

# Build

Simply run: `mvn clean install`

Note that integration tests are not run by default, and requires a profile: `mvn clean install -Pit`

# Integration Tests

Tests requires a Synology DSM server running with some existing account, share and folder structure.

# Use

This is a Spring based library. It is supposed to be used within Spring based applications.
To do so:
- Import class `DsmWebapiClientConfig` in the application configuration
- Provide following beans in the application configuration:
..- An implementation of `DsmUrlProvider` (existing implementations: `PropertiesDsmUrlProvider`)
..- An implementation of `AuthenticationProvider` (existing implementations: `PropertiesAuthenticationProvider`, `ConsoleAuthenticationProvider`)
..- An implementation of `TimeZoneProvider` (existing implementations: `PropertiesTimeZoneProvider`, `DefaultTimeZoneProvider`)
..- A `PropertySourcesPlaceholderConfigurer` if any  `PropertiesXXXProfider` is used.

Finally, inject services needed in application classes: search for interfaces `XXXService` corresponding to implemented APIs.

# Currently implemented API:

- SYNO.API.Info
- SYNO.API.Auth
- SYNO.FileStation.List
- SYNO.FileStation.Info
- SYNO.FileStation.Search
