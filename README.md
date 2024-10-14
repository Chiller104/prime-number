This is a simple Java application that reads an Excel file and logs all prime numbers found in column B.

## Requirements

- Java 8 or higher
- Maven
- Apache POI library for Excel reading

## How to run

1. Clone the repository:
    ```bash
    git clone -b master https://github.com/Chiller104/prime-number.git
    cd prime-number
    ```

2. Build the project with Maven:
    ```bash
    mvn clean install
    ```

3. Run the application:
    ```bash
    mvn compile exec:java -Dexec.mainClass="com.example.prime_number.App" -Dexec.args="path_to_excel_file.xlsx"
    ```

Replace `path_to_excel_file.xlsx` with the actual path to your Excel file.
