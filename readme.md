# Member Mailer

A JavaFX application for sending personalized email notifications to organization members, allowing them to verify and
update their contact information.

## Features

- Import member data from CSV files
- Automated, personalized email generation
- Configurable email templates (subject and body) for different member types
- Simple graphical user interface (GUI)
- Email settings via environment variables

## Requirements

- Java 11 or higher
- Maven
- Gmail account (with app-specific password)
- JavaFX SDK (for native packaging)

## Setup

1. Clone t# Member Mailer

A JavaFX application for sending personalized email notifications to organization members, allowing them to verify and
update their contact information.

## Features

- Import member data from CSV files
- Automated, personalized email generation
- Configurable email templates (subject and body) for different member types
- Simple graphical user interface (GUI)
- Email settings via environment variables

## Requirements

- Java 11 or higher
- Maven
- Gmail account (with app-specific password)
- JavaFX SDK (for native packaging)

## Build app
```
jar cfe target/MemberMailer.jar ch.josiaschweizer.MemberMailer -C target/classes .
```
```
jlink --module-path $JAVA_HOME/jmods:/Users/josiaschweizer/javafx-jmods-24.0.1 \
      --add-modules java.base,javafx.controls,javafx.fxml,javafx.graphics,java.logging \
      --output custom-runtime
```
```
jpackage \
  --name MemberMailer \
  --input target \
  --main-jar MemberMailer.jar \
  --main-class ch.josiaschweizer.MemberMailer \
  --type dmg \
  --icon icon.icns \
  --dest out/installer \
  --runtime-image custom-runtime
```
```
  /Applications/MemberMailer.app/Contents/MacOS/MemberMailer
 ```

### Credentials
- email adress: raembus@gmail.com
- apppassword: hpbf hjwt fdyr ggto