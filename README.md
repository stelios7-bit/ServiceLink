# 🏡 Service Booking System 

[![Java](https://img.shields.io/badge/Java-17-blue?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green?logo=springboot)](https://spring.io/projects/spring-boot)
[![Hibernate](https://img.shields.io/badge/Hibernate-ORM-orange?logo=hibernate)](https://hibernate.org/)
[![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)](https://maven.apache.org/)

A **Spring Boot** based service booking platform inspired by **UrbanClap (Urban Company)**, where users can book household services (plumbing, cleaning, etc.) and service providers can manage service requests.  
Built with **Spring Boot, MySQL, Hibernate ORM**, and follows clean modular design with multiple APIs.

---

## ✨ Features

### 👤 User Functionalities
- 🔑 Login / Signup with authentication
- 🔎 Explore available services (plumbing, cleaning, etc.)
- 📋 View service providers with:
  - Name
  - Age
  - Profile Picture
  - Rating ⭐
  - Service Request button
- 📝 Request a service by providing:
  - Description
  - Required Time Slot
  - Address
  - Images
- ❌ Cancel or Update service requests
- 🔐 OTP generation once service request is accepted

### 🧑‍🔧 Service Provider Functionalities
- 📝 Separate registration for service providers
- ⭐ View self ratings, services requested, and completed services
- ✅ Accept requests and view user details
- ❌ Deny service requests if time slots overlap (with notification to user)
- 🔒 OTP verification by user after service completion

---

## 🏗️ Tech Stack

- **Backend:** Spring Boot (REST APIs)
- **Database:** MySQL
- **ORM:** Hibernate ORM (JPA)
- **Build Tool:** Maven
- **Language:** Java
- **Security & Auth:** Spring Security (JWT/Session) 

---

## 📊 Database Design 

- **Users Table** → stores user details  
- **ServiceProviders Table** → provider details + linked to User role  
- **Services Table** → list of service categories  
- **Requests Table** → user requests with slot, description, address, and status  
- **Ratings Table** → ratings and feedback  


