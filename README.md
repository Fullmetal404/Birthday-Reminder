# Birthday Reminder App

## Overview
The **Birthday Reminder App** is a simple Java application that helps manage contacts and reminds you of birthdays. It allows users to add, view, sort, and remove contacts from a list. The app displays the age of the contact and shows a reminder for birthdays that fall on the current date.

## Features
- **Add Contact**: Easily add a contact with their name and date of birth.
- **Sort Contacts**: Sort contacts by:
  - Name
  - Age
  - Date of Birth (DOB)
- **Remove Contact**: Delete contacts from the list.
- **Birthday Reminder**: The app will notify you if any of the contacts have a birthday today.
- **Search Contacts**: Find contacts by typing a search term that matches the contact's information.

## Technologies Used
- **Java**: The core language used for the app.
- **Swing**: For creating the graphical user interface (GUI).
- **LocalDate & DateTimeFormatter**: For handling dates, formatting, and validating date inputs.
- **File I/O**: Contacts are saved to and loaded from a file called `Contacts.dat`.

## Requirements
- **JDK 8 or later**: The app requires Java Development Kit version 8 or later to run.
- **Operating System**: This app is compatible with any system that can run Java.

## Getting Started

### Option 1: Run the JAR file (Recommended)

1. **Download the JAR file**.
2. **Run the JAR file**:
   Open a terminal/command prompt and navigate to the folder where the JAR file is located. Run the following command:
   ```bash
   java -jar Birthday_Reminder_Application.jar
