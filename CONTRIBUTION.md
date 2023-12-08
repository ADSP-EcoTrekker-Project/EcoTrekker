# Contribution

In the following you will find some guidance on how to contribute to some parts of the EcoTrekker application.

## Working on existing subprojects

## Creating a new subproject

When creating a new subproject, simply create a new top level directory in [kebab-kase](https://en.wiktionary.org/wiki/kebab_case).

In the directory, manually create the required directories, as well as a gradle.build file.

```
your-amazing-sub-project
|   gradle.build
└───src
    └───java
    |   └───com
    |       └───ecotrekker
    |           └───your/great/package
    └───test
        └───java
            └───com
                └───ecotrekker
                    └───your/great/package
```

An example gradle.build file for a command line application looks like this:

```groovy
plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    id 'application'
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation(platform('org.junit:junit-bom:5.10.1'))
	testImplementation('org.junit.jupiter:junit-jupiter')

    // This dependency is used by the application.
    implementation 'com.google.guava:guava:32.1.1-jre'
}

// Apply a specific Java toolchain to ease working on different environments.
java {
	sourceCompatibility = '17'
}

jar {
    manifest {
        attributes 'Main-Class': 'com.ecotrekker.your.great.package'
    }
}

application {
    // Define the main class for the application.
    mainClass = 'com.ecotrekker.your.great.package'
}

```

An example gradle.build file for a command line application looks like this:

```groovy

plugins {
    id 'java-library' 
}

repositories {
    mavenCentral() 
}

version = '0.1.0'

dependencies {
    // Use JUnit test framework.
    testImplementation(platform('org.junit:junit-bom:5.10.1'))
	testImplementation('org.junit.jupiter:junit-jupiter')
}

java {
	sourceCompatibility = '17'
}

tasks.named('jar') {
    manifest {
        attributes('Automatic-Module-Name': 'com.ecotrekker.your.great.lib')
    }
}

tasks.named('test') {
    useJUnitPlatform() 
}

```

## Adding a Dockerfile

The docker containers for the EcoTrekker application are built using a docker bake file ([ecotrekker-bake.hcl](./ecotrekker-bake.hcl)).

The bake file uses the the Dockerfiles in the docker/ directory to build the containers. More information is [here](./docker/README.md).

To add a Dockerfile / container add a Dockerfile named `your-great-application.Dockerfile` in the docker folder.

```dockerfile

FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

VOLUME [ "/tmp" ]
# /ecotrekker/build is the directory in the 'builder' image, where the subprojects are built
COPY --from=builder /ecotrekker/build/your-great-project/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

```

To add the new Dockerfile to the bake file, just append:

```javascript

target "your-great-application" {
    dockerfile = "../docker/your-great-application.Dockerfile"
    tags = ["${REPO}/eco-your-great-application:${TAG}"]
    contexts = {
        bake-base-image = "target:builder"
    }
    context = "your-great-application/"
}

group "default" {
    targets = ["rest-api", "co2-calculator"] // Add 'your-great-application' to default targets if it should always be built
}

```