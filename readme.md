# Member Mailer

A Java application for sending email notifications to members with their contact information for verification purposes.

## Overview

Member Mailer automates the process of requesting contact information verification from organization members. The application reads member data from a CSV file and sends personalized emails, allowing members to easily respond if any information needs to be updated.

## Features

- CSV data import and processing
- Automated email generation with personalized content
- Simple command-line interface
- Configurable email settings via environment variables

## Requirements

- Java 11 or higher
- Maven
- Gmail account (for sending emails)
- Application-specific password for Gmail

## Setup

1. Clone this repository
2. Create a `.env` file in the project root with:
3. Place your member CSV file in the project root directory

## Usage

Build and run the application with Maven:

```bash
mvn clean package
java -jar target/member-mailer.jar
```

## CSV Format

The application expects CSV files with the following columns:
- Member ID (column 0)
- Email address (column 1)
- Street address (column 2)
- City (column 3)
- Postal code (column 4)
- Phone number (column 5)
- First name (column 6)
- Last name (column 7)

## Configuration
Email settings can be customized by modifying the SMTP properties in the MemberMailer.java file. The default configuration is set up for Gmail.

## Security
- Credentials are stored in environment variables loaded via dotenv
- The application uses SMTP with TLS for secure email transmission
- CSV files containing member data should be kept secure