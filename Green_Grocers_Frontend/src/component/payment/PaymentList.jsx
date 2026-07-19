import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBan,faCircleCheck  } from '@fortawesome/free-solid-svg-icons';
import { paymentList } from "../../service/PaymentService";
import '../style/Payment.css';
import { FaTimesCircle, FaCheckCircle } from "react-icons/fa";
 
 
const PaymentList = () => {
  // State to hold payments and selected payment mode
  const [payments, setPayments] = useState([]);
  const [selectedMode, setSelectedMode] = useState('All');
  const navigator = useNavigate(); // Hook for navigation
 
  useEffect(() => {
    // Fetch payments when the component mounts
    try {
      paymentList()
        .then((response) => {
          setPayments(response.data);
        })
        .catch((error) => {
          console.error("Error fetching payments:", error);
        });
    } catch (error) {
      console.error("Error fetching payments:", error);
    }
  }, []);
 
  // Function to format time
  const formatTime = (time) => {
    if (!time) return "";
    const timeArray = time.split(":");
    const secondsArray = timeArray[2].split("."); // Split the seconds and milliseconds
    return `${timeArray[0]}:${timeArray[1]}:${secondsArray[0]}`; // Use only the seconds part
  };
 
  // Function to view payment details
  function viewDetails(id) {
    navigator(`/payment/payment-details/${id}`);
  }
 
  // Function to navigate to successful payments
  function navigateToSuccessfulPayments() {
    navigator(`/admin/payment/sucessful`);
  }
 
  // Function to navigate to unsuccessful payments
  function navigateToUnsuccessfulPayments() {
    navigator(`/admin/payment/unsucessful`);
  }
 
  // Function to handle change in payment mode filter
  function handleModeChange(event) {
    setSelectedMode(event.target.value);
  }
 
  // Filter payments based on selected mode
  const filteredPayments = selectedMode === 'All' ? payments : payments.filter(payment => payment.paymentMode === selectedMode);
 
  // Render the component
  return (
    <div className="container paymentcontainer">
      {/* Buttons to view successful and unsuccessful payments */}
      <div className="button-group paymentbutton-group">
        <button className="btn paymentbtn btn-success paymentbtn-success" onClick={navigateToSuccessfulPayments}>View Successful Payments</button>
        <button className="btn btn-danger paymentbtn paymentbtn-danger" onClick={navigateToUnsuccessfulPayments}>View Unsuccessful Payments</button>
      </div>
      {/* Dropdown to filter payments by payment mode */}
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
            <div>
              Payment ID: {payment.paymentId}
            </div>
            <div className="card-body">
              <h5 className="card-title paymentcard-title">
                {payment.paymentStatus}
                {/* Show check mark icon for successful payments and times icon for failed payments */}
                {payment.paymentStatus === 'Payment Successful' && <FaCheckCircle icon={FaCheckCircle} style={{color: "#36ba50"}} />}
                {payment.paymentStatus === 'Payment Failed' && <FaTimesCircle icon={FaTimesCircle} className="text-danger ml-2" />}
              </h5>
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
 
export default PaymentList;