import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { paymentById } from "../../service/PaymentService";
import { FaCheckCircle, FaTimesCircle } from "react-icons/fa"; // Import both icons
import '../style/Payment.css';

export default function PaymentDetails() {
  // Get the paymentId from the route parameters
  const { paymentId } = useParams();
 
  // State to hold the payment details
  const [payment, setPayment] = useState({});
 
  // Fetch payment details when the component mounts or when paymentId changes
  useEffect(() => {
    // Function to fetch payment details and handle errors
    const fetchPaymentDetails = async () => {
      try {
        const response = await paymentById(paymentId);
        setPayment(response.data);
      } catch (error) {
        console.error("Error fetching payment:", error);
      }
    };
 
    fetchPaymentDetails(); // Call the function to fetch payment details
  }, [paymentId]);
 
  // Function to format card number for display
  const formatCardNumber = (cardNumber) => {
    if (!cardNumber) return "";
    const visibleDigits = 5;
    const maskedSection = "XXXX-XXXX-XXXX";
    const visibleSection = cardNumber.slice(-visibleDigits);
    return `${maskedSection} ${visibleSection}`;
  };
 
  // Function to format time for display
  const formatTime = (time) => {
    if (!time) return "";
    const timeArray = time.split(":");
    const secondsArray = timeArray[2].split("."); // Split the seconds and milliseconds
    return `${timeArray[0]}:${timeArray[1]}:${secondsArray[0]}`; // Use only the seconds part
  };
 
  // Render the payment details
  return (
    <div className="payment-details-container mt-4">
      {payment && (
        <div className="card payment-details-card">
          <div className="card-header payment-details-card-header">
            {/* Display payment status with appropriate icon */}
            {payment.paymentStatus}{" "}
            {payment.paymentStatus === "Payment Successful" ? (
              <FaCheckCircle className="text-success paymenttext-success ml-2 paymentml-2" />
            ) : (
              <FaTimesCircle className="text-danger paymenttext-danger ml-2 paymentml-2" />
            )}
          </div>
          <div className="card-body payment-details-card-body">
            <h5 className="card-title payment-details-card-title">
              Payment ID {payment.paymentId}
            </h5>
            <p className="card-text payment-details-card-text">
              Payment Date: {payment.paymentDate}
            </p>
            <p className="card-text payment-details-card-text">
              Payment Time: {formatTime(payment.paymentTime)}
            </p>
            <hr />
            <h5 className="card-title payment-details-card-title">
              Payment Details:
            </h5>
            <table className="table payment-details-table">
              <thead>
                <tr>
                  <th scope="col">Payment Mode</th>
                  {payment.cardNumber && <th scope="col">Card Number</th>}
                  {payment.nameOnCard && <th scope="col">Name On Card</th>}
                  {payment.upiId && <th scope="col">UPI Id</th>}
                  <th scope="col">Item Count</th>
                  <th scope="col">Total Amount</th>
                  <th scope="col">Customer Name</th>
                </tr>
              </thead>
              <tbody>
                <tr key={payment.paymentId}>
                  {/* Display payment details */}
                  <td>{payment.paymentMode}</td>
                  {payment.cardNumber && (
                    <td>{formatCardNumber(payment.cardNumber)}</td>
                  )}
                  {payment.nameOnCard && <td>{payment.nameOnCard}</td>}
                  {payment.upiId && <td>{payment.upiId}</td>}
                  <td>{payment.itemCount}</td>
                  <td>{payment.totalAmount}</td>
                  <td>{payment.customer && payment.customer.name}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}