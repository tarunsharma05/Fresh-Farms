import axios from "axios";

//To get the order by Id
export const getOrderbyId = (id) =>
    axios.get(" http://localhost:8080/api/orders" + "/" + id);

//To get the customers orders by Id
export const getOrdersByCustomer = (customerId) =>
    axios.get("http://localhost:8080/api/orders/customerId" + "/" + customerId);
    
//To download the invoice
export const DownloadInvoice = (id) =>
    axios.get("http://localhost:8080/generate/document" + "/" + id);

