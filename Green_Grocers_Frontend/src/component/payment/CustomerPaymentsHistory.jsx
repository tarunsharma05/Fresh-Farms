import React, { useState, useEffect } from "react";
import { getPaymentsByCustomerId } from "../../service/PaymentService";
import '../style/Payment.css';
import { useParams, useNavigate } from "react-router-dom";
import { FaCheckCircle, FaTimesCircle } from "react-icons/fa";
import { Container, Row, Col, Card } from "react-bootstrap";
 
const CustomerPaymentsHistory = () => {
  const [payments, setPayments] = useState([]);
  const { customerId } = useParams();
  const navigate = useNavigate();
 
  useEffect(() => {
    getPaymentsByCustomerId(customerId)
      .then(response => {
        setPayments(response.data);
      })
      .catch(error => {
        console.error("Error fetching payments:", error);
      });
  }, [customerId]);
 
  function viewDetails(id) {
    navigate(`/payment/payment-details/${id}`);
  }
 
  return (
    <div className="allproducts mt-4">
      <Container>
        <h3 className="text-center">Payment History</h3>
      <Row className="justify-content-center mt-3">
        {payments.map(payment => (
          <Col md={8} sm={6} xs={6} key={payment.paymentId} className="mb-2">
            <Card className="mb-0 paymentcustomercard" onClick={() => viewDetails(payment.paymentId)}>
              <Card.Body className="mt-0">
                <div>
                  <Card.Title className="mt-0">
                    {/* Display payment status with appropriate icon */}
                    {payment.paymentStatus}
                    {payment.paymentStatus.toLowerCase() === 'payment successful' && <FaCheckCircle className="text-success ml-2" />}
                    {payment.paymentStatus.toLowerCase() === 'payment failed' && <FaTimesCircle className="text-danger ml-2" />}
                  </Card.Title>
                  <div className="payment-details d-flex justify-content-between">
                    {/* Display payment details */}
                    <Card.Text><h6 className="paymentcustomerh6">Payment ID: {payment.paymentId}</h6></Card.Text>
                    <Card.Text><h6 className="paymentcustomerh6">Payment Mode: {payment.paymentMode}</h6></Card.Text>
                    <Card.Text><h6 className="paymentcustomerh6">Total Amount: {payment.totalAmount}</h6></Card.Text>
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
 
export default CustomerPaymentsHistory;


