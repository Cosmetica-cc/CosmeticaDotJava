# CosmeticaDotJava

A Java library to interface with the Cosmetica web API.

## Importing the library

CosmeticaDotJava is available through jitpack for maven, gradle, and any other build tool that can download from a maven server. 

### Gradle

Add jitpack to repositories (**NOT** in the publishing section) as follows:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add in your dependencies block as follows:

```gradle
dependencies {
    implementation 'com.github.Cosmetica-cc:CosmeticaDotJava:1.0.1'
}
```

You'll likely want to distribute it as part of your project as well. To do this, you can shade the library, or Jar-In-Jar if that's supported.

**Bundling on Fabric via Jar-in-Jar:**

```gradle
dependencies {
    include 'com.github.Cosmetica-cc:CosmeticaDotJava:1.0.1'
}
```

### Maven

Add jitpack to your repositories as follows:

```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Add the CosmeticaDotJava dependency:

```xml
<dependency>
    <groupId>com.github.Cosmetica-cc</groupId>
    <artifactId>CosmeticaDotJava</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Getting Started

To get started, you'll first need to initialise an instance of the `CosmeticaAPI` interface. The most common methods to do this are
1. To create an unauthenticated instance, This has much more limited functionality (e.g. you cannot upload capes or view region specific effects if not signed in).
2. Create instance authenticated with minecraft. This has full functionality.

### Creating an unauthenticated instance.

This one is pretty simple. You don't need any parameters, and the only errors that could be thrown is if there is an I/O exception or an internal server error.

```java
CosmeticaAPI cosmetica = CosmeticaAPI.newUnauthenticatedInstance();
```

### Creating an authenticated instance.

This one is more complex. You need the user's UUID, username, and their minecraft authentication token. If you're making a minecraft mod, these should be pretty simple to obtain from the game. Otherwise you're going to need to do some of the work for that yourself.

```java
CosmeticaAPI cosmetica = CosmeticaAPI.fromMinecraftToken(minecraftAuthToken, username, uuid);
```
