## ✅ DONE

### 🔧 Project Setup
- Create public GitHub repository for the project [commit: 421fb26]
- Initialize Spring Boot backend project with Maven dependencies (Spring Web, Spring Security, Spring Data JPA, MySQL Driver) [commit: 421fb26]
- Create backend folder structure following layered architecture: model, repository, service, controller [commit: 421fb26]
- Initialize ReactJS frontend project using Create React App [commit: 421fb26]
- Create /docs folder for documentation files [commit: 421fb26]
- Create /mobile folder as placeholder for mobile application [commit: 421fb26]
- Write README.md [commit: 421fb26]

### 🖥️ Backend – Spring Boot
- Implement POST /api/auth/register endpoint with BCrypt password encryption [commit: 6573215]
- Implement POST /api/auth/login endpoint with credential validation [commit: 6573215]
- Implement GET /api/user/me protected endpoint for fetching authenticated user details [commit: 6573215]
- Configure MySQL database connection via application.properties [commit: 6573215]
- Implement password encryption using BCrypt (passwords never stored in plain text) [commit: 6573215]
- Add LogoutRequest DTO and implement POST /api/auth/logout endpoint with standardized response [commit: 5f89763]
- Improve input validation and error handling for all endpoints [commit: 5f89763]
- Ensure consistent API response structure across all endpoints [commit: 5f89763]
- Update class diagram to reflect addition of LogoutRequest DTO [commit: 5f89763]

### 🌐 Web Application – ReactJS
- Implement Register page with form validation and API integration [commit: 6573215]
- Implement Login page with credential submission and token handling [commit: 6573215]
- Implement Dashboard/Profile page as a protected route (redirects to login if not authenticated) [commit: 6573215]
- Implement Logout functionality that clears session and redirects to login [commit: 6573215]

### 📱 Mobile Application – Android Kotlin
- Create Android project structure with Kotlin and necessary dependencies (Retrofit, Gson) [commit: 3c95f67]
- Implement Register screen with form inputs and backend API integration [commit: 3c95f67]
- Implement Login screen with credential validation and session handling [commit: 3c95f67]
- Implement Dashboard/Profile screen as a protected screen (accessible only when logged in) [commit: 3c95f67]
- Implement Logout functionality that clears session and returns to Login screen [commit: 3c95f67]
- Configure RetrofitClient with base URL pointing to Spring Boot backend [commit: 3c95f67]
- Fix network configuration: updated BASE_URL from hardcoded local IP to emulator localhost (10.0.2.2) in RetrofitClient.kt [commit: <add new hash>]
- Update network_security_config.xml to allow cleartext traffic for emulator localhost (10.0.2.2) [commit: <add new hash>]

### 📄 Documentation
- Create partial FRS PDF with ERD, UML diagrams, and Web UI screenshots (Register, Login, Dashboard, Logout) [commit: 6573215]
- Finalize FRS PDF with complete Web and Mobile UI screenshots and diagram revisions [commit: 3c95f67]
- Export updated FRS PDF with revised version and submission date to /docs [commit: <add new hash>]