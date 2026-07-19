
import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { getAllCustomers } from '../../service/AdminService';

function CustomerList() {
  const [customers, setCustomers] = useState([]); // State to hold the list of customers
  const [loading, setLoading] = useState(true); // State to handle loading
  const [error, setError] = useState(null); // State to handle errors

  // Fetch all customers when the component mounts
  useEffect(() => {
    getAllCustomers()
      .then((response) => {
        const customersData = response.data;
        setCustomers(customersData); // Set the fetched customers to state
        setLoading(false); // Set loading to false
        console.log('Fetched customers:', customersData);
      })
      .catch((error) => {
        console.error('Error fetching customers:', error); // Handle errors
        setError('Failed to fetch customers'); // Set error state
        setLoading(false); // Set loading to false
      });
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (customers.length === 0) {
    return <div>No customers found</div>;
  }

  return (
    <div className='allproducts mt-2'>
      <Container>
        <h3 className="my-4">Customer List</h3>
        <Row>
          {customers.map(customer => (
            <Col md={12} sm={6} xs={12} key={customer.customerId} className="mb-2">
              <Link to={`/customer/${customer.customerId}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                <Card className="h-100 profile-card">
                  <Card.Body>
                    <Card.Text>
                      <Card.Title>{customer.userName}</Card.Title>
                      <strong>Name:</strong> {customer.name} &nbsp; &nbsp;
                      <strong>Phone:</strong> {customer.phone} &nbsp; &nbsp;
                      <strong>Email:</strong> {customer.email}
                    </Card.Text>
                  </Card.Body>
                </Card>
              </Link>
            </Col>
          ))}
        </Row>
      </Container>
    </div>
  );
}

export default CustomerList;
