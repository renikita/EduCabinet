# EduCabinet

EduCabinet is a web application for students and teachers that helps to efficiently organize the learning process. This repository contains a fully prepared demo environment that can be launched with Docker Compose without a local Java or Maven installation.

> The portal runs on Spring Boot (Java 21) and MariaDB. A prebuilt JAR and configuration files are included in the release — you only need Docker Desktop.

[![GitHub release](https://img.shields.io/badge/pre_release-0.1.0_alpha-blue)](https://github.com/renikita/EduCabinet/releases/latest)

## Table of contents

- [Features](#features)
- [What's in the release](#whats-in-the-release)
- [Quick Start (recommended)](#quick-start-recommended)
- [Stop and reset the database](#stop-and-reset-the-database)
- [Environment variables / configuration](#environment-variables--configuration)
- [Running the JAR locally (without Docker)](#running-the-jar-locally-without-docker)
- [Common issues & troubleshooting](#common-issues--troubleshooting)
- [Development & contributions](#development--contributions)
- [Contacts](#contacts)
- [License](#license)

<a name="features"></a>
## Features
- Full demo stack configuration (Spring Boot + MariaDB)
- One-command startup that brings up all services via Docker Compose
- Prebuilt JAR included in the release
- Startup scripts for Windows (PowerShell + CMD)
- Cross-platform operation via Docker

<a name="whats-in-the-release"></a>
## What's in the release
- prebuilt JAR (compiled artifact)
- Dockerfile
- docker-compose.yml
- run.ps1 — PowerShell launcher for Windows
- run.bat — CMD launcher for Windows

<a name="quick-start-recommended"></a>
## Quick Start (recommended)

1. Install and start Docker Desktop (required).
2. Download the latest release → extract the ZIP.
3. In the extracted folder run one of the startup options below.

PowerShell:
```powershell
.\run.ps1
```

Windows CMD:
```bat
run.bat
```

If you prefer manual startup:
```bash
docker-compose up --build
```

After startup the application will be available at:
http://localhost:8000

<a name="stop-and-reset-the-database"></a>
## Stop and reset the database
Stop containers:
```bash
docker-compose down
```

Stop containers and remove volumes (this will reset the database):
```bash
docker-compose down -v
```

<a name="environment-variables--configuration"></a>
## Environment variables / configuration
The docker-compose.yml and application.properties files are configured for the demo. If you need to change DB connection or other parameters:
- DB_USERNAME — database username (default set in docker-compose)
- DB_PASSWORD — database password
- DB_NAME — database name

Change these values in docker-compose.yml or supply them via a .env file if needed.

<a name="running-the-jar-locally-without-docker"></a>
## Running the JAR locally (without Docker)
The release contains a prebuilt JAR that the Dockerfile uses to build the image. If you want to run the application locally without Docker, ensure you have Java 21 installed and a reachable MariaDB instance on port 3306, then run:
```bash
java -jar target/educabinet.jar
```

<a name="common-issues--troubleshooting"></a>
## Common issues & troubleshooting
- Docker won't start / containers fail to run:
  - Ensure Docker Desktop is running and has enough resources (CPU / RAM).
- Port 8000 is already in use:
  - Stop the process using that port or change the port in docker-compose.yml.
- File permission issues on Windows:
  - Run PowerShell as Administrator if you encounter file access errors.

<a name="development--contributions"></a>
## Development & contributions
Contributions are welcome:
- Open pull requests with a clear description of changes
- Open issues with a detailed description of bugs or feature requests
Please follow a clean coding style and include explanations in PR descriptions.

<a name="contacts"></a>
## Contacts
Developer: @renikita

<a name="license"></a>
## License
This project is distributed under the [GNU General Public License v3.0 (GPL-3.0)](https://github.com/renikita/EduCabinet/blob/66fbaa0c97f7b18b52ae5dde76d935c71dbed44e/LICENSE). The full license text is available in the `LICENSE` file.
