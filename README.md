# foodsphere-food-ordering-system

## Overview
The **FoodSphere Food Ordering System** is a role-based online food ordering application developed using Modern Spring Boot. This system is designed to provide a seamless user experience for ordering food online while incorporating essential functionalities for both users and administrators.

## Key Technologies
- **Spring Boot**: The core framework for building the application.
- **Spring Security**: Used for implementing secure authentication with JWT (JSON Web Token).
- **Spring Data JPA**: Provides easy integration with MySQL for data management.
- **MySQL**: The relational database used for storing user and restaurant data.
- **Postman**: Utilized for testing RESTful APIs during development.

## Features
### User Functionality
- **User Registration**: Users can register for an account by providing necessary details.
- **Login**: Secure login functionality is provided using Spring Security and JWT authentication.
- **Food Ordering**: Users can browse available food items, add them to their cart, and place orders effortlessly.
- **Cart Management**: Users can view and manage items in their cart before finalizing the order.
- **Order History Tracking**: Users can view their past orders and track the status of current orders.

### Restaurant Management
- **Restaurant Creation**: Users can create their own restaurant profiles, providing details such as name, location, and menu items.
- **Image Upload**: Restaurant owners can upload images to enhance their profiles and showcase their offerings.
- **Ingredient Management**: Allows users to add ingredients to food items for customization.

### Admin Functionality
- **Order Monitoring**: Admins have the ability to monitor all orders placed within the system.
- **Restaurant Management**: Admins can oversee restaurant profiles and make adjustments as necessary.
- **Operational Control**: Admins can manage the status of orders and have the ability to open or close restaurants based on operational requirements.

### Payment Integration
- **Secure Online Payment Gateway**: The backend integrates a secure payment gateway(stripe payment gateway), facilitating safe and reliable transactions for users.

### Class diagram
![@localhost](https://github.com/user-attachments/assets/09b5942b-b234-4568-84c3-535a22be83f2)

