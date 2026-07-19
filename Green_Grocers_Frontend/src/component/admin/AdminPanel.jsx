import React, { useEffect, useState } from 'react';
import { allproductList, getAllCustomers } from '../../service/AdminService';
import { Container, Row, Col, Card } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUsers, faShoppingCart, faBox, faBoxes, faMoneyCheckAlt, faTruckLoading } from '@fortawesome/free-solid-svg-icons';
import { paymentList } from '../../service/PaymentService';
import { useNavigate } from 'react-router-dom';

function AdminPanel() {
  // State variables to store data
  const [customers, setCustomers] = useState([]);
  const [items, setItems] = useState([]);
  const [payments, setPayments] = useState([]);
  const [totalOrders, setTotalOrders] = useState(0);
  const [undeliveredOrders, setUndeliveredOrders] = useState(0);
  const [totalRevenue, setTotalRevenue] = useState(0);
  const [mostOrderedItem, setMostOrderedItem] = useState('');

  const navigator = useNavigate();

  // useEffect to fetch data on component mount
  useEffect(() => {
    // Fetch customers data
    getAllCustomers()
      .then((response) => {
        const customersData = response.data;
        setCustomers(customersData);
        console.log('Customers data:', customersData);  // Log customer data
        calculateStatistics(customersData);
      })
      .catch((error) => {
        console.error('Error fetching customers:', error);
      });

    // Fetch products data
    allproductList()
      .then((response) => {
        setItems(response.data);
      })
      .catch((error) => {
        console.error('Error fetching items:', error);
      });

    // Fetch payments data
    paymentList()
      .then((response) => {
        setPayments(response.data);
      })
      .catch((error) => {
        console.error('Error fetching payments:', error);
      });
  }, []);

  // Function to calculate statistics
  const calculateStatistics = (customers) => {
    let ordersCount = 0;
    let undeliveredCount = 0;
    let revenue = 0;
    let itemCount = {};

    // Loop through each customer and their orders
    customers.forEach((customer) => {
      console.log('Processing customer:', customer);  // Log each customer
      customer.orders.forEach((order) => {
        console.log('Processing order:', order);  // Log each order
        if (!order.canceled) {
          ordersCount += 1;
          if (order.payment) {
            revenue += order.payment.totalAmount;
          }

          // Check for processing and shipped statuses
          if (order.orderStatus === 'Processing' || order.orderStatus === 'Shipped') {
            undeliveredCount += 1;
          }

          // Count ordered items
          order.orderedItems.forEach((item) => {
            if (itemCount[item.itemName]) {
              itemCount[item.itemName] += 1;
            } else {
              itemCount[item.itemName] = 1;
            }
          });
        }
      });
    });

    // Update state with calculated values
    setTotalOrders(ordersCount);
    setUndeliveredOrders(undeliveredCount);
    setTotalRevenue(revenue);

    // Find the most ordered item
    const mostOrdered = Object.entries(itemCount).reduce(
      (a, b) => (b[1] > a[1] ? b : a),
      ['', 0]
    );
    setMostOrderedItem(mostOrdered[0]);
  };

  // Navigation handlers
  const handleCustomers = (e) => {
    e.preventDefault();
    navigator(`/customers`);
  };

  const handleOrders = (e) => {
    e.preventDefault();
    navigator(`/admin/orders`);
  };

  const handlePayments = (e) => {
    e.preventDefault();
    navigator(`/admin/payment`);
  };

  const handleUndeliveredOrders = (e) => {
    e.preventDefault();
    navigator(`/admin/orders-pending`);
  };

  const handleItems = (e) => {
    e.preventDefault();
    navigator(`/allitemsadmin`);
  };

  return (
    <div className='adminpaneldiv'>
      <Container>
        <h1 className="text-center my-4 mt-5">Admin Panel</h1>
        <Row className="mb-4 mt-5 justify-content-center">
          <Col md={3} onClick={handleCustomers}>
            <Card className="text-center profile-card mt-2">
              <Card.Body>
                <Card.Title><FontAwesomeIcon icon={faUsers} /> Active Users</Card.Title>
                <Card.Text>{customers.length}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3} onClick={handleOrders}>
            <Card className="text-center profile-card mt-2">
              <Card.Body>
                <Card.Title><FontAwesomeIcon icon={faShoppingCart} /> Total Orders</Card.Title>
                <Card.Text>{totalOrders}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3} onClick={handleItems}>
            <Card className="text-center profile-card mt-2">
              <Card.Body>
                <Card.Title><FontAwesomeIcon icon={faBoxes} /> Total items</Card.Title>
                <Card.Text>{items.length}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3} onClick={handlePayments}>
            <Card className="text-center profile-card mt-2">
              <Card.Body>
                <Card.Title><FontAwesomeIcon icon={faMoneyCheckAlt} /> Total Payments</Card.Title>
                <Card.Text>{payments.length}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3} onClick={handleUndeliveredOrders}>
            <Card className="text-center profile-card mt-2">
              <Card.Body>
                <Card.Title><FontAwesomeIcon icon={faTruckLoading} /> Undelivered Orders</Card.Title>
                <Card.Text>{undeliveredOrders}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3}>
            <Card className="text-center profile-card mt-2">
              <Card.Body>
                <Card.Title><span>&#8377; </span>Total Revenue</Card.Title>
                <Card.Text><span>&#8377; </span>{totalRevenue.toFixed(2)}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3}>
            <Card className="text-center profile-card mt-2">
              <Card.Body>
                <Card.Title><FontAwesomeIcon icon={faBox} /> Most Ordered Item</Card.Title>
                <Card.Text>{mostOrderedItem}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default AdminPanel;
