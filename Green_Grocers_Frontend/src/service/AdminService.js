import axios from "axios";

// Define base URLs for different services
const REST_API_BASE_URL_CUSTOMER = "http://localhost:8080/api/customers";
const REST_API_BASE_URL_CART = "http://localhost:8080/api/carts";
const REST_API_BASE_URL_ADDRESS = "http://localhost:8080/api/addresses";
const REST_API_BASE_URL_ITEM = "http://localhost:8080/api/items";

// Fetch all customers
export const getAllCustomers = () => {
    return axios.get(REST_API_BASE_URL_CUSTOMER)
        .catch(error => {
            console.error('Error fetching all customers:', error);

        });
};

// Fetch customer details by ID
export const getCustomerById = (customerId) => {
    return axios.get(`${REST_API_BASE_URL_CUSTOMER}/${customerId}`)
        .catch(error => {
            console.error(`Error fetching customer with ID ${customerId}:`, error);

        });
};

// Fetch cart details by customer ID
export const getCartByCustomerId = (customerId) => {
    return axios.get(`${REST_API_BASE_URL_CART}/${customerId}`)
        .catch(error => {
            console.error(`Error fetching cart for customer ID ${customerId}:`, error);

        });
};

// Search customers by a search term
export const searchCustomers = (searchTerm) => {
    const params = { searchTerm: searchTerm };
    return axios.get(`${REST_API_BASE_URL_CUSTOMER}/search`, { params })
        .catch(error => {
            console.error(`Error searching customers with term "${searchTerm}":`, error);

        });
};

// Fetch addresses by customer ID
export const getAddressesByCustomerId = (customerId) => {
    return axios.get(`${REST_API_BASE_URL_ADDRESS}/customer/${customerId}`)
        .catch(error => {
            console.error(`Error fetching addresses for customer ID ${customerId}:`, error);

        });
};

// Delete address by ID
export const deleteAddressById = (addressId) => {
    return axios.delete(`${REST_API_BASE_URL_ADDRESS}/${addressId}`)
        .catch(error => {
            console.error(`Error deleting address with ID ${addressId}:`, error);

        });
};

// Fetch address details by address ID
export const getAddressesById = (addressId) => {
    return axios.get(`${REST_API_BASE_URL_ADDRESS}/${addressId}`)
        .catch(error => {
            console.error(`Error fetching address with ID ${addressId}:`, error);

        });
};

// Add a new address for a customer
export const addAddress = (customerId, formData) => {
    return axios.post(`${REST_API_BASE_URL_ADDRESS}/${customerId}`, formData)
        .catch(error => {
            console.error(`Error adding address for customer ID ${customerId}:`, error);

        });
};

// Update address details by address ID
export const updateAddress = (addressId, formData) => {
    return axios.put(`${REST_API_BASE_URL_ADDRESS}/${addressId}`, formData)
        .catch(error => {
            console.error(`Error updating address with ID ${addressId}:`, error);

        });
};

// Update customer details by customer ID
export const updateCustomerDetails = (customerId, formData) => {
    return axios.put(`${REST_API_BASE_URL_CUSTOMER}/${customerId}`, formData)
        .catch(error => {
            console.error(`Error updating customer details for ID ${customerId}:`, error);

        });
};

// Delete a customer by ID
export const deleteUser = (customerId) => {
    return axios.delete(`${REST_API_BASE_URL_CUSTOMER}/${customerId}`)
        .catch(error => {
            console.error(`Error deleting customer with ID ${customerId}:`, error);

        });
};

// Search products by name or type
export const searchProducts = (searchTerm) => {
    return axios.get(`${REST_API_BASE_URL_ITEM}/itembynameortype/${searchTerm}`)
        .catch(error => {
            console.error(`Error searching products with term "${searchTerm}":`, error);

        });
};

// Fetch product list by type
export const productList = (type) => {
    return axios.get(`${REST_API_BASE_URL_ITEM}/itembynameortype/${type}`)
        .catch(error => {
            console.error(`Error fetching product list for type "${type}":`, error);

        });
};

// Fetch all products
export const allproductList = () => {
    return axios.get(REST_API_BASE_URL_ITEM)
        .catch(error => {
            console.error('Error fetching all products:', error);

        });
};
