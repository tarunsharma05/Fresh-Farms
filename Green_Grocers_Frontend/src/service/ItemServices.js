import axios from "axios";
 
const REST_API_BASE_URL = "http://localhost:8080/api/items";
 
// Fetch a list of products based on type
export const productList = (type) => {
  return axios.get(REST_API_BASE_URL + '/itembynameortype/' + type);
};
 
// Fetch the list of all products
export const allproductList = () => {
  return axios.get(REST_API_BASE_URL);
};
 
// add new Item
export const additems = (item) => {
  return axios.post(REST_API_BASE_URL, item);
};
 
// Update an existing item based on itemId
export const updateitems = (itemId, item) => {
  return axios.put(REST_API_BASE_URL + '/updateitem' + '/' + itemId , item);
};
 
// Delete an item based on itemId
export const deleteitems = (itemId) => {
  return axios.delete(REST_API_BASE_URL + '/deleteitem' + '/' + itemId);
};
 
// Fetch an item based on itemId
export const getItemById = (itemId) => {
  return axios.get(REST_API_BASE_URL + '/itemid' + '/' + itemId);
};
 
// Fetch items sorted by price
export const sortByPrice = () => {
  return axios.get(REST_API_BASE_URL + '/sortbyprice');
};
 
// Fetch items sorted by name
export const sortByName = () => {
  return axios.get(REST_API_BASE_URL + '/sortbyname');
};
 