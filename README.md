# Asteroid Path

This project provides a RESTful API to interact with NASA's Near Earth Object Web Service (NeoWs). It retrieves data about asteroids and their paths, and caches the responses to improve performance.

## Introduction

This project fetches data from NASA's NeoWs API to provide information about asteroids and their paths. It uses Spring Boot for the backend and includes a caching mechanism to store frequent requests, improving the application's performance.

## Features

- Fetch detailed information about asteroids from NASA's NeoWs API.
- Cache responses to reduce API call latency and limit the number of requests to NASA's servers.
- Handle errors gracefully and provide meaningful error messages.

## Technologies Used

- Java
- Spring Boot
- Spring Cache (with ConcurrentMapCacheManager)
- Gson
- Maven

## Getting Started

### Prerequisites

- Java 17
- Maven
- [Optional] A NASA API key. You can get one from [NASA API Portal](https://api.nasa.gov/).

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/gabrielefifi2/asteroid-path.git
    cd asteroid-path
    ```

2. [Optional] Update the `application.properties` file with your NASA API key:

    ```properties
    nasa.neo-lookup.api.base-url=https://api.nasa.gov/neo/rest/v1/neo
    nasa.neo-lookup.api.api-key=YOUR_NASA_API_KEY
    ```

3. Build the project with Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    mvn spring-boot:run
    ```

## Configuration

Check and edit configurations of the application by the `src/main/resources/application.properties` file.
