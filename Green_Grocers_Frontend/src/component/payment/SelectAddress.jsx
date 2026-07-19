import React, { useState, useEffect } from "react";
import { getAddressesByCustomerId } from "../../service/PaymentService";
import { Container, Row, Col, Card } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLocationDot } from '@fortawesome/free-solid-svg-icons';
import '../style/Payment.css';
 
const SelectAddress = ({ setSelectedAddressId, customerId }) => {
  // State to hold addresses and selected address ID
  const [addresses, setAddresses] = useState([]);
  const [selectedAddressId, setSelectedAddressIdLocal] = useState(null);
 
  // Fetch addresses when the component mounts or when customerId changes
  useEffect(() => {
    // Function to fetch addresses and handle errors
    const fetchAddresses = async () => {
      try {
        const response = await getAddressesByCustomerId(customerId);
        setAddresses(response.data);
      } catch (error) {
        console.error("Error fetching addresses:", error);
      }
    };
 
    fetchAddresses(); // Call the function to fetch addresses
  }, [customerId]);
 
  // Function to handle selection of an address
  function selectAddress(addressId) {
    setSelectedAddressIdLocal(addressId); // Set selected address ID locally
    setSelectedAddressId(addressId); // Pass selected address ID to parent component
  }
 
  // Render the address list
  return (
    <div className="container paymentaddresscontainer allproducts mt-4">
      <h3 className="text-center mb-4">Select Address</h3>
      <Container>
        <Row>
          {addresses.map((address) => (
            <Col md={12} sm={12} xs={12} key={address.addressId} className="mb-4">
              <Card
                className={`h-100 profile-card ${selectedAddressId === address.addressId ? "selected" : ""}`}
                onClick={() => selectAddress(address.addressId)}
              >
                <Card.Body>
                  <div className="d-flex justify-content-between align-items-center">
                    <div>
                      <Card.Title>
                        <FontAwesomeIcon icon={faLocationDot} className="mr-4" />
                        {address.city}
                      </Card.Title>
                      <Card.Text>
                        House no: {address.houseNo}, {address.landmark}, {address.city}-{address.pinCode}, {address.state}, {address.country}
                      </Card.Text>
                    </div>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </Container>
    </div>
  );
};
 
export default SelectAddress;