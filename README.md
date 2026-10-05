# Music Finder Network

A Java client–server application for searching songs and artist information using TCP Socket programming.

The system follows a centralized client–server architecture:
- JavaFX Client handles user interaction and media presentation.
- Java TCP Server handles business logic, API integration, caching and database access.
- PostgreSQL stores user accounts, playlists and search history.

## Features

### Client
- Register and login
- Search songs
- Search Vietnamese artists
- View song and artist information
- Play media from supported URLs
- Manage personal playlists
- View search history

### Server
- TCP Socket communication
- Multi-client processing with ExecutorService
- Authentication
- External music API integration
- Fuzzy search
- Search cache
- Search statistics
- Error and timeout handling

## Architecture

JavaFX Client
        |
        | TCP Socket
        v
Java TCP Server
        |
        +---- Authentication Service
        +---- Search Service
        +---- Playlist Service
        +---- Cache
        +---- External Music API
        +---- PostgreSQL Database

## Technologies

- Java 21
- JavaFX
- TCP Socket
- Maven
- Mysql
- JDBC
- Jackson
- Docker
- Git / GitHub

## Project Structure

```text
music-finder-network/
├── client/
├── server/
├── shared/
├── database/
├── pom.xml
└── README.md
