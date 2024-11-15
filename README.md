
# Spring Boot CIB Offer Scraper

A Spring Boot application designed to scrape offers from the CIB (Commercial International Bank) website.
The app efficiently collects, processes, and presents data from the CIB offers page for facilitating search capabilities use.

## Features
- Scrapes offer details from the CIB website.
- Extracts relevant information like offer titles, descriptions, expiry dates, and terms.
- Configurable scraping intervals.
- Data storage and retrieval using a database.
- REST API to access the scraped data.

## Requirements
- **Java 17+**
- **Maven 3+**
- **Spring Boot 2.7+**
- **MySQL DB**
- **Selenium**

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/cib-offer-scraper.git
cd cib-offer-scraper
```

### 2. Configure Application Properties
Edit `src/main/resources/application.properties` to configure the application (specially DB name, your username and password for your db connection):
```properties
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/scraper
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

### 3. Build the Application
Use Maven to package the application:
```bash
    mvn clean install
```

### 4. Run the Application
```bash
    java -jar target/cib-offer-scraper-0.0.1-SNAPSHOT.jar
```


## How It Works
1. The application startup open browser using selenium and extract offers from site.
2. The extracted data added to DB directly (Per page).

## Technologies Used
- **Spring Boot**: For application development.
- **selenium**: For web scraping and parsing HTML.
- **MySQL**: As the database for storing offer details.
- **REST API**: For data access and management.

## Contributing
1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact
For questions or support, please contact [mohamed.salama848@gmail.com].
