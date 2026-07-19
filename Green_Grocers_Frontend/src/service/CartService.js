 
import axios from 'axios';
 
// Base URL for the API endpoints
const API_URL = 'http://localhost:8080/api';
 
export const fetchItems = async () => {
  try {
    // Make a GET request to fetch items
    const response = await axios.get(`${API_URL}/items`);
    // Return the data from the response
    return response.data;
  } catch (error) {
    // Log error if the request fails
    console.error('Error fetching items:', error);
    // Return an empty array in case of error
    return [];
  }
};
 

export const fetchCart = async (customerId) => {
  try {
    // Make a GET request to fetch the cart for the specified customer
    const response = await axios.get(`${API_URL}/carts/${customerId}`);
    // Return the data from the response
    return response.data;
  } catch (error) {
    // Log error if the request fails
    console.error('Error fetching cart:', error);
    // Return a default empty cart object in case of error
    return { items: [], totalAmount: 0 };
  }
};
 

export const addItemToCart = async (customerId, itemId) => {
  try {
    // Make a POST request to add an item to the cart for the specified customer
    const response = await axios.post(`${API_URL}/carts/additemtocart/${customerId}/${itemId}`);
    // Return the data from the response
    return response.data;
  } catch (error) {
    // Log error if the request fails
    console.error('Error adding item to cart:', error);
    // Return null in case of error
    return null;
  }
};
 

export const removeItemFromCart = async (customerId, itemId) => {
  try {
    // Make a PUT request to remove an item from the cart for the specified customer
    const response = await axios.put(`${API_URL}/carts/removeitemfromcart/${customerId}/${itemId}`);
    // Return the data from the response
    return response.data;
  } catch (error) {
    // Log error if the request fails
    console.error('Error removing item from cart:', error);
    // Return null in case of error
    return null;
  }
};
 

export const clearCart = async (customerId) => {
  try {
    // Make a PUT request to clear the cart for the specified customer
    const response = await axios.put(`${API_URL}/carts/clear-cart/${customerId}`);
    // Return the data from the response
    return response.data;
  } catch (error) {
    // Log error if the request fails
    console.error('Error clearing cart:', error);
    // Return null in case of error
    return null;
  }
};
 
export const updateCart = (customerId,cart) =>{
  axios.put(API_URL+"/carts/update-cart/"+customerId,cart);
}

export const getCart = (customerId) =>{
  axios.get(API_URL+"/carts/"+customerId);
}