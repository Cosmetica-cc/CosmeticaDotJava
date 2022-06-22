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
    implementation 'com.github.Cosmetica-cc:CosmeticaDotJava:1.1.0'
}
```

You'll likely want to distribute it as part of your project as well. To do this, you can shade the library, or Jar-In-Jar if that's supported.

**Bundling on Fabric via Jar-in-Jar:**

```gradle
dependencies {
    include 'com.github.Cosmetica-cc:CosmeticaDotJava:1.1.0'
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
    <version>1.1.0</version>
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

### What is `ServerResponse<T>`?

All API web requests from `CosmeticaAPI` return an instance of `ServerResponse` wrapped around the type they are returning.
`ServerResponse` as a wrapper is similar to `Optional`, but instead of purely storing the presence or absence of data, it stores either the response data or a runtime exception.

The exceptions you are most likely to receive are `CosmeticaAPIException` (which represents an error response from the api),
`UncheckedIOException` and occasionally `FatalServerErrorException`.

There are a number of ways to handle these responses, 3 of which are detailed here:

1. Just unwrap the response

This is simple enough. Just put `.get()` after receiving your response, and it will either return the response data or throw an exception depending on which is stored in the object. 

2. Use a fallback value

You can provide a fallback value through the use of `.or(fallback value)`.

3. Success and Error Callbacks

`ServerResponse` also provides methods which take callbacks in the case of success / error.
- `.ifSuccessful(...)` takes a single `Consumer` of whatever type the ServerResponse contains in the case of success and only runs if the response contains no exception
- `.ifError(...)` takes a single `Consumer<RuntimeException>` and only runs if the response contains an exception
- `.ifSuccessfulOrElse(...)` takes two consumers: a `Consumer` of whatever type the ServerResponse contains in the case of success, and a `Consumer<RuntimeException>`. The corresponding callback runs dependent on whether an exception is present or not.

### Examples

Here is a short example of a command line program for getting the cape worn by a user. You can check out more examples in the `src/cc/cosmetica/test` package.

```java
public static void main(String[] args) {
	// initialise an instance
	CosmeticaAPI cosmetica = CosmeticaAPI.newUnauthenticatedInstance();
	
	// command line input
    System.out.println("Whose cape do you wish to investigate?");
	String username = new Scanner(System.in).nextLine();
	
	// Make request and handle result
	cosmetica.getUserInfo(null, username).ifSuccessfulOrElse(info -> {
		Optional<Cape> optionalCape = info.getCape();
		
		if (optionalCape.isPresent()){
			Cape cape = optionalCape.get();

			if (cape.getOrigin().equals("Cosmetica")) {
				System.out.println(username + " has the cape \"" + cape.getName() + "\" from cosmetica!");
			} else {
				System.out.println(username + " has a " + cape.getOrigin() + " cape.");
			}
		} else {
			System.out.println(username + " has no cape :(");
		}
	}, err -> {
		System.err.println("There was an error retrieving that user's info!");
		err.printStackTrace();
	});
}
```
