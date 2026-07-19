import React, { useState, useEffect } from "react";
import { unsuccessfulPaymentList } from "../../service/PaymentService"; // Importing the function to fetch unsuccessful payments
import '../style/Payment.css';
import { useNavigate } from 'react-router-dom';
import { FaTimesCircle } from "react-icons/fa";
 
const UnsuccessfulPayments = () => {
  const [payments, setPayments] = useState([]); // State to hold the payments data
  const [selectedMode, setSelectedMode] = useState('All'); // State for the selected payment mode filter
  const navigate = useNavigate(); // Hook for navigation
 
  useEffect(() => {
    try {
      // Fetching unsuccessful payments when the component mounts
      unsuccessfulPaymentList()
        .then((response) => {
          console.log("Unsuccessful payments fetched:", response.data); // Logging successful fetch
          setPayments(response.data); // Updating the payments state with the fetched data
        })
        .catch((error) => {
          console.error("Error fetching unsuccessful payments:", error); // Logging fetch error
        });
    } catch (error) {
      console.error("Error fetching unsuccessful payments:", error); // Logging catch block error
    }
  }, []);
 
  // Function to format the time
  const formatTime = (time) => {
    if (!time) return "";
    const timeArray = time.split(":");
    const secondsArray = timeArray[2].split("."); // Split the seconds and milliseconds
    return `${timeArray[0]}:${timeArray[1]}:${secondsArray[0]}`; // Use only the seconds part
  };
 
// Function to navigate to payment details page
function viewDetails(id) {
  navigate(`/payment/payment-details/${id}`);// Navigate to payment details page with the specified payment ID
}
 
// Function to navigate to view successful payments page
function navigateToSuccessfulPayments() {
  navigate(`/admin/payment/sucessful`); // Navigate to view successful payments page
}
 
// Function to navigate to view unsuccessful payments page
function navigateToUnsuccessfulPayments() {
  navigate(`/admin/payment/unsucessful`); // Navigate to view unsuccessful payments page
}
 
  // Event handler for mode change
  function handleModeChange(event) {
    setSelectedMode(event.target.value);
  }
 
  // Filtering payments based on selected payment mode
  const filteredPayments = selectedMode === 'All' ? payments : payments.filter(payments => payments.paymentMode === selectedMode);
 
  return (
    <div className="container paymentcontainer">
      {/* Button group for navigation */}
      <div className="button-group paymentbutton-group">
        <button className="btn paymentbtn btn-success paymentbtn-success" onClick={navigateToSuccessfulPayments}>View Successful Payments</button>
        <button className="btn btn-danger paymentbtn paymentbtn-danger" onClick={navigateToUnsuccessfulPayments}>View Unsuccessful Payments</button>
      </div>
      {/* Filter section for payment mode */}
      <div className="filter-section paymentfilter-section">
        <label htmlFor="paymentModeFilter">Filter by Payment Mode:</label>
        <select id="paymentModeFilter" value={selectedMode} onChange={handleModeChange}>
          <option value="All">All</option>
          <option value="card">Card</option>
          <option value="upi">UPI</option>
          <option value="cod">COD</option>
        </select>
      </div>
      {/* Container for displaying payment cards */}
      <div className="card-container paymentcard-container">
        {/* Mapping over filtered payments and displaying payment cards */}
        {filteredPayments.map(payments => (
          <div className="allpaymentcard text-center" key={payments.paymentId} onClick={() => viewDetails(payments.paymentId)}>
            <div className="card-body">
              <h5 className="card-title paymentcard-title mb-3">
                {payments.paymentStatus}
                {payments.paymentStatus === 'Payment Failed'&&<FaTimesCircle className="text-danger" />}
              </h5>
              <div className="mb-3">
              Payment ID: {payments.paymentId}
            </div>
              <p className="card-text">Payment Mode: {payments.paymentMode}</p>
              <p className="card-text">Date: {payments.paymentDate}</p>
              <p className="card-text">Time: {formatTime(payments.paymentTime)}</p>
              <div className="row">
                <div className="col">
                  <p>Total Amount: {payments.totalAmount}</p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
 
export default UnsuccessfulPayments;