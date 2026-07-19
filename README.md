# GreenGrocers

## Overview

The Green Grocers is a web application built using React and React-Bootstrap. It allows admins to view and manage customer details, including personal information, addresses, cart items, order history, and payment history.

## Features

- **Customer List**: View a list of all customers with their names, phone numbers, and emails.
- **Customer Details**: View detailed information for each customer, including:
  - Personal information (name, phone, email)
  - Addresses
  - Current cart items
  - Order history
  - Payment history
- **Interactive UI**: Cards with shadow effects and hover animations for a modern and user-friendly interface.

## Tech Stack

- **Frontend**:
  - React
  - React-Bootstrap
  - CSS
- **Backend**:
  - SpringBoot (assumed to be already implemented)
- **Routing**:
  - React Router

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Akash-4634/GreenGrocers.git

2. Navigate to the project directory:
   ```sh
   cd GreenGrocers

3. Install dependencies:
   ```sh
   npm install

4. Start the development server:
   ```sh
   npm start

Open your browser and navigate to http://localhost:3000.



## Usage
Customer List
The main page displays a list of all customers in a card layout. Each card shows the customer's name, phone number, and email. Clicking on a card navigates to the customer's detailed information page.

## Customer Details
The customer details page displays comprehensive information about a selected customer:

Personal Information: Name, phone, and email.
Addresses: A list of all addresses associated with the customer.
Current Cart: Items currently in the customer's cart, with quantities and total prices for duplicate items.
Order History: A list of past orders, including order IDs, types, dates, total amounts, and statuses.
Payment History: A list of payments made by the customer, including payment IDs, modes, dates, amounts, and statuses.
Customization
Styling
You can customize the look and feel of the application by modifying the CSS in CustomerList.css. Adjust the card styles, hover effects, and other UI elements to match your design preferences.

## API Integration
Ensure that your backend API endpoints are correctly configured in AdminService.js to fetch the required customer data. Modify the service functions if your API structure differs from the assumed format.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure your code follows the existing code style and conventions.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.






