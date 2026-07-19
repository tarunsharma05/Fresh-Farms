import axios from 'axios';

const REST_API_URL = "http://localhost:8080/api/payments";
const ADDRESS_API_URL = "http://localhost:8080/api/addresses"; // Base URL for address API

// Fetch a payment by its ID
export const paymentById = (paymentId) => {
  return axios.get(REST_API_URL + '/' + paymentId)
    .then(response => response)
    .catch(error => {
      console.error('Error fetching payment by ID:', error);
    });
};

// Fetch a list of all payments
export const paymentList = () => {
  return axios.get(REST_API_URL)
    .then(response => response)
    .catch(error => {
      console.error('Error fetching payment list:', error);
    });
};

// Fetch a list of successful payments
export const successfulPaymentList = () => {
  return axios.get(REST_API_URL + '/successful')
    .then(response => response)
    .catch(error => {
      console.error('Error fetching successful payment list:', error);

    });
};

// Fetch a list of unsuccessful payments
export const unsuccessfulPaymentList = () => {
  return axios.get(REST_API_URL + '/unsuccessful')
    .then(response => response)
    .catch(error => {
      console.error('Error fetching unsuccessful payment list:', error);

    });
};

// Create a new payment for a customer
export const createPayment = (customerId, paymentData) => {
  return axios.post(REST_API_URL + `/${customerId}`, paymentData)
    .then(response => response)
    .catch(error => {
      console.error('Error creating payment:', error);

    });
};

// Create a new order
export const createOrder = (customerId, paymentId, addressId) => {
  return axios.post("http://localhost:8080/api/orders/createorder/" + customerId + "/" + paymentId + "/" + addressId)
    .then(response => response)
    .catch(error => {
      console.error('Error creating order:', error);

    });
};

// Fetch payments by customer ID
export const getPaymentsByCustomerId = (customerId) => {
  return axios.get(REST_API_URL + "/customer/" + customerId)
    .then(response => response)
    .catch(error => {
      console.error('Error fetching payments by customer ID:', error);

    });
};

// Fetch addresses by customer ID
export const getAddressesByCustomerId = (customerId) => {
  return axios.get(ADDRESS_API_URL + "/customer/" + customerId)
    .then(response => response)
    .catch(error => {
      console.error('Error fetching addresses by customer ID:', error);

    });
};

// Fetch customer by ID
export const getCustomerById = (customerId) => {
  return axios.get("http://localhost:8080/api/customers/" + customerId)
    .then(response => response)
    .catch(error => {
      console.error('Error fetching customer by ID:', error);

    });
};
