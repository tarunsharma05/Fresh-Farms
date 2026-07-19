import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getCustomerById } from '../../service/AdminService';
import { Container, Card, Table } from 'react-bootstrap';

function CustomerDetails() {
  const { customerId } = useParams(); // Get customerId from the route parameters
  const [customer, setCustomer] = useState(null); // State to store customer details
  const [loading, setLoading] = useState(true); // State to store loading status

  // Fetch customer details when component mounts or customerId changes
  useEffect(() => {
    console.log('Fetching customer details for ID:', customerId);
    getCustomerById(customerId)
      .then((response) => {
        console.log('Customer data:', response.data);
        setCustomer(response.data); // Set customer data to state
        setLoading(false); // Set loading to false after data is fetched
      })
      .catch((error) => {
        console.error('Error fetching customer details:', error); // Handle errors
        setLoading(false); // Set loading to false even if there is an error
      });
  }, [customerId]);

  // If data is still loading, show a loading message
  if (loading) {
    return <div>Loading...</div>;
  }

  // Function to group items by name and count the occurrences
  const groupItems = (items) => {
    const itemMap = {};
    items.forEach(item => {
      if (itemMap[item.itemName]) {
        itemMap[item.itemName].quantity++;
        itemMap[item.itemName].totalPrice += item.itemPrice;
      } else {
        itemMap[item.itemName] = {
          itemId: item.itemId,
          itemName: item.itemName,
          quantity: 1,
          totalPrice: item.itemPrice
        };
      }
    });
    return Object.values(itemMap);
  };

  // Group cart items if they exist
  const groupedCartItems = customer.cart && customer.cart.items ? groupItems(customer.cart.items) : [];

  // Group ordered items if they exist
  const orderedItems = customer.orders.flatMap(order => order.orderedItems || []);
  const groupedOrderedItems = orderedItems.length > 0 ? groupItems(orderedItems) : [];

  return (
    <div className='allproducts'>
      <Container>
        <h3 className="text-center my-4">Customer Details</h3>
        <Card className="mb-4 profile-card">
          <Card.Body>
            <Card.Title className='categorymainh4 mb-4'>{customer.userName}</Card.Title>
            <Card.Text>Name: {customer.name}</Card.Text>
            <Card.Text>Phone: {customer.phone}</Card.Text>
            <Card.Text>Email: {customer.email}</Card.Text>
          </Card.Body>
        </Card>

        <h4 className='categorymainh4'>Addresses</h4>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Address</th>
            </tr>
          </thead>
          <tbody>
            {customer.addresses.map(address => (
              <tr key={address.addressId}>
                <td>House no: {address.houseNo}, {address.landmark}, {address.city}-{address.pinCode}, {address.state}, {address.country}</td>
              </tr>
            ))}
          </tbody>
        </Table>

        <h4 className='categorymainh4'>Current Cart</h4>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Item Name</th>
              <th>Quantity</th>
              <th>Price</th>
            </tr>
          </thead>
          <tbody>
            {groupedCartItems.map(item => (
              <tr key={item.itemId}>
                <td>{item.itemName}</td>
                <td>{item.quantity}</td>
                <td><span>&#8377; </span>{item.totalPrice.toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </Table>

        <h4 className='categorymainh4'>Items Purchased</h4>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Item Name</th>
              <th>Quantity</th>
              <th>Price</th>
            </tr>
          </thead>
          <tbody>
            {groupedOrderedItems.map(item => (
              <tr key={item.itemId}>
                <td>{item.itemName}</td>
                <td>{item.quantity}</td>
                <td><span>&#8377; </span>{item.totalPrice.toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </Table>

        <h4 className='categorymainh4'>Order History</h4>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Order Date</th>
              <th>Total Amount</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {customer.orders.map(order => (
              <tr key={order.orderId}>
                <td>{order.orderId}</td>
                <td>{order.orderDate}</td>
                <td><span>&#8377; </span>{order.payment ? order.payment.totalAmount.toFixed(2) : 'N/A'}</td>
                <td>{order.orderStatus}</td>
              </tr>
            ))}
          </tbody>
        </Table>

        <h4 className='categorymainh4'>Payment History</h4>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Payment ID</th>
              <th>Payment Mode</th>
              <th>Payment Date</th>
              <th>Total Amount</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {customer.payments.map(payment => (
              <tr key={payment.paymentId}>
                <td>{payment.paymentId}</td>
                <td>{payment.paymentMode}</td>
                <td>{payment.paymentDate}</td>
                <td><span>&#8377; </span>{payment.totalAmount.toFixed(2)}</td>
                <td>{payment.paymentStatus}</td>
              </tr>
            ))}
          </tbody>
        </Table>

      </Container>
    </div>
  );
}

export default CustomerDetails;
