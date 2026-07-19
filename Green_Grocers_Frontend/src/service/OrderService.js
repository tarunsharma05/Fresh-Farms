import axios from "axios";

//To get the order by Id
export const getOrderbyId = (id) =>
    axios.get(" https://fresh-farms-hac7.onrender.com/api/orders" + "/" + id);

//To get the customers orders by Id
export const getOrdersByCustomer = (customerId) =>
    axios.get("https://fresh-farms-hac7.onrender.com/api/orders/customerId" + "/" + customerId);
    
//To download the invoice
export const DownloadInvoice = (id) =>
    axios.get("https://fresh-farms-hac7.onrender.com/generate/document" + "/" + id);

