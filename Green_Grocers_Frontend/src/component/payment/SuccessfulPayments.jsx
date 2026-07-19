import React, { useState, useEffect } from "react";
import { successfulPaymentList } from "../../service/PaymentService"; // Importing service function to fetch successful payments
import '../style/Payment.css';
import { useNavigate } from 'react-router-dom'; // Importing hook for navigation
import { FaCheckCircle } from "react-icons/fa"; // Importing icon for successful payments
 
const SuccessfulPayments = () => {
  // State to store payments data and selected payment mode filter
  const [payments, setPayments] = useState([]); // State for payments data
  const [selectedMode, setSelectedMode] = useState('All'); // State for selected payment mode filter
  const navigate = useNavigate(); // Navigation hook
 
  // Fetch successful payments data when component mounts
  useEffect(() => {
    // Function to fetch successful payments and handle errors
    const fetchSuccessfulPayments = async () => {
      try {
        const response = await successfulPaymentList(); // Fetch successful payments data
        setPayments(response.data); // Set payments data in state
      } catch (error) {
        console.error("Error fetching successful payments:", error); // Log error if fetching fails
      }
    };
 
    fetchSuccessfulPayments(); // Call the function to fetch successful payments data
  }, []);
 
  // Function to format time
  const formatTime = (time) => {
    if (!time) return ""; // Return empty string if time is not provided
    const timeArray = time.split(":"); // Split time into hours, minutes, and seconds
    const secondsArray = timeArray[2].split("."); // Split the seconds and milliseconds
    return `${timeArray[0]}:${timeArray[1]}:${secondsArray[0]}`; // Return formatted time (hours:minutes:seconds)
  };
 
  // Function to navigate to payment details page
  function viewDetails(id) {
    navigate(`/payment/payment-details/${id}`); // Navigate to payment details page with the specified payment ID
  }
 
  // Function to navigate to view successful payments page
  function navigateToSuccessfulPayments() {
    navigate(`/admin/payment/sucessful`); // Navigate to view successful payments page
  }
 
  // Function to navigate to view unsuccessful payments page
  function navigateToUnsuccessfulPayments() {
    navigate(`/admin/payment/unsucessful`); // Navigate to view unsuccessful payments page
  }
 
  // Function to handle payment mode filter change
  function handleModeChange(event) {
    setSelectedMode(event.target.value); // Set selected payment mode filter based on user selection
  }
 
  // Filter payments based on selected payment mode
  const filteredPayments = selectedMode === 'All' ? payments : payments.filter(p => p.paymentMode === selectedMode);
 
  // Render component with successful payments data
  return (
    <div className="container paymentcontainer">
      {/* View successful and unsuccessful payments buttons */}
      <div className="button-group paymentbutton-group">
        <button className="btn paymentbtn btn-success paymentbtn-success" onClick={navigateToSuccessfulPayments}>View Successful Payments</button>
        <button className="btn btn-danger paymentbtn paymentbtn-danger" onClick={navigateToUnsuccessfulPayments}>View Unsuccessful Payments</button>
      </div>
      {/* Payment mode filter */}
      <div className="filter-section paymentfilter-section">
        <label htmlFor="paymentModeFilter">Filter by Payment Mode:</label>
        <select id="paymentModeFilter" value={selectedMode} onChange={handleModeChange}>
          <option value="All">All</option>
          <option value="card">Card</option>
          <option value="upi">UPI</option>
          <option value="cod">COD</option>
        </select>
      </div>
      {/* Display filtered payments */}
      <div className="card-container paymentcard-container">
        {filteredPayments.map(payment => (
          <div className="allpaymentcard text-center" key={payment.paymentId} onClick={() => viewDetails(payment.paymentId)}>
            <div className="card-body">
              <h5 className="card-title paymentcard-title mb-3">
                {payment.paymentStatus}
                {/* Render acicon for successful payments */}
                {payment.paymentStatus === 'Payment Successful' && <FaCheckCircle className="text-success paymenttext-success" />}
              </h5>
              <div className="mb-3">
              Payment ID: {payment.paymentId}
            </div>
              <p className="card-text">Payment Mode: {payment.paymentMode}</p>
              <p className="card-text">Date: {payment.paymentDate}</p>
              <p className="card-text">Time: {formatTime(payment.paymentTime)}</p>
              <div className="row">
                <div className="col">
                  <p>Total Amount: {payment.totalAmount}</p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
 
export default SuccessfulPayments;