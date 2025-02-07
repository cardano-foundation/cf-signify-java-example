# cf-signify-java-example

## Overview
This project demonstrates how to use the [cf-signify-java](https://github.com/cardano-foundation/cf-signify-java) library, which is designed for "signing at the edge" within the KERI (Key Event Receipt Infrastructure) ecosystem. It provides examples of key generation, event signing, and identity management.

## Prerequisites
- **Java 21**: Ensure you have Java 21 installed. You can verify your Java version by running:
  ```bash
  java -version
  ```
- **Gradle**: This project uses Gradle as the build tool.
- **Running instance of KERIA**

## Setup

### Temporary Workaround for Library Dependencies
Currently, there's an issue with the mavenCentral repository version. As a temporary workaround:

1. Clone and build the Signify Java library locally:
```bash
git clone https://github.com/cardano-foundation/cf-signify-java
cd cf-signify-java
# Ensure version is 1.0-SNAPSHOT in build.gradle
./gradlew publishToMavenLocal
```

2. In this example project's `build.gradle`, we use mavenLocal():
```gradle
repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url "https://oss.sonatype.org/content/repositories/snapshots"
    }
}

dependencies {
    implementation 'org.cardanofoundation:signify-java:1.0-abfbc0a-SNAPSHOT' 
    // Note: 'abfbc0a' should be replaced with the commit hash of your local build
}
```

## Building and Running

1. Build the project:
```bash
./gradlew build
```

2. Run the application:
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## API Examples

Here are some example curl commands to interact with the API:

### Create an Identifier
```bash
curl -X POST "http://localhost:8080/api/example/identifiers/alice" \
     -H "Content-Type: application/json" \
     -d '{
           "transferable": true,
           "wits": [],
           "toad": 0,
           "icount": 1,
           "ncount": 1,
           "isith": "1",
           "nsith": "1"
         }'
```

### Get an Identifier
```bash
curl -X GET "http://localhost:8080/api/example/identifiers/alice"
```

### Sign a Message
```bash
curl -X POST "http://localhost:8080/api/example/identifiers/alice/sign" \
     -H "Content-Type: application/json" \
     -d "Hello, World!"
```

### Verify a Signature
```bash
curl -X POST "http://localhost:8080/api/example/identifiers/alice/verify" \
     -H "Content-Type: application/json" \
     -d '{
           "message": "Hello, World!",
           "signature": "<signature_from_sign_endpoint>"
         }'
```

## Note
This is an example implementation and should be modified according to your specific needs and security requirements.

## License
[License information here]