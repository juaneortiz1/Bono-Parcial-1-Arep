# Calculator HTTP Server

## Description of the App
This application is a simple HTTP server that processes mathematical commands through a RESTful interface. Users can perform various calculations, including single-parameter functions, bubble sort, and operations with two parameters such as addition, subtraction, multiplication, division, maximum, and minimum. The server responds with results in JSON format, making it easy to integrate with web clients.

## Architecture
The application consists of two main components:
1. **HTTP Server**: A Java-based server that listens for incoming requests on specified ports. It parses HTTP requests and routes them to appropriate methods for computation.
2. **Math Operations**: Various mathematical operations are implemented as methods within the application, leveraging Java's built-in Math class as well as custom implementations like bubble sort.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Maven for project management
- A web browser or HTTP client for testing requests

### Installing
1. Clone the repository:
   ```bash
   git clone https://github.com/juaneortiz1/Bono-Parcial-1-Arep.git
   ```
2. Navigate to the project directory:
   ```bash
   cd Bono-Parcial-1-Arep
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the server in one instance of the terminal:
   ```bash
   java -cp target/bono-1.0-SNAPSHOT.jar org.example.Calcreflexback
   ```
   ```bash
   java -cp target/bono-1.0-SNAPSHOT.jar org.example.Calcreflexbackfacade
   ```

### Acceptance Test
To test the application, you can send HTTP GET requests using a web browser or an HTTP client like Postman. Below are some example requests:
- To perform addition: `http://localhost:36000/compreflex?computar=add(5,3)`
- To sort an array: `http://localhost:36000/compreflex?computar=bbl(3,1,2)`

Through the browser or client, you can see the results of the calculations in JSON format. 


### Built With
- Java
- Apache Maven
- HTTP

