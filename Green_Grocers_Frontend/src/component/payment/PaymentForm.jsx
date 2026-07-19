import React, { useState } from "react";
import { createPayment, createOrder } from "../../service/PaymentService";
import { useLocation, useNavigate } from "react-router-dom";
import SelectAddress from "./SelectAddress.jsx";
import { useUser } from "../context/UserContext.jsx";
import '../style/Payment.css';
import { FaCreditCard, FaGoogleWallet, FaMoneyBillWave, FaCheckCircle, FaTimesCircle } from "react-icons/fa";
const PaymentForm = () => {
  const { customerId } = useUser(); // Retrieve customerId from user context
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const totalAmount = searchParams.get('totalAmount'); // Get total amount from URL query params
  const [paymentMethod, setPaymentMethod] = useState("upi"); // State to store selected payment method
  const [formData, setFormData] = useState({
    cardNumber: "",
    nameOnCard: "",
    expiryDate: "",
    cvv: "",
    upiId: "",
    totalAmount: totalAmount,
  }); // State to store form data
  const [errors, setErrors] = useState({}); // State to store form validation errors
  const [showPaymentForm, setShowPaymentForm] = useState(false); // State to toggle payment form visibility
  const [showSuccessPopup, setShowSuccessPopup] = useState(false); // State to show success popup
  const [showFailurePopup, setShowFailurePopup] = useState(false); // State to show failure popup
  const [selectedAddressId, setSelectedAddressId] = useState(null); // State to store selected address ID
  const navigate = useNavigate(); // React Router hook for navigation
  // Handle input changes and update form data
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  // Validate form data based on selected payment method
  const validateForm = () => {
    let errors = {};
    const cardNumberPattern = /^\d{4}-\d{4}-\d{4}-\d{4}$/;
    const expiryDatePattern = /^(0[1-9]|1[0-2])\/\d{2}$/;
    const cvvPattern = /^[0-9]{3}$/;
    const upiPattern = /@/;
    if (paymentMethod === "card") {
      if (!formData.cardNumber) {
        errors.cardNumber = "Card Number is required";
      } else if (!cardNumberPattern.test(formData.cardNumber)) {
        errors.cardNumber = "Card Number must be in XXXX-XXXX-XXXX-XXXX format";
      }
      if (!formData.nameOnCard) errors.nameOnCard = "Name on Card is required";
      if (!formData.expiryDate) {
        errors.expiryDate = "Expiry Date is required";
      } else if (!expiryDatePattern.test(formData.expiryDate)) {
        errors.expiryDate = "Expiry Date must be in MM/YY format";
      }
      if (!formData.cvv) {
        errors.cvv = "CVV is required";
      } else if (!cvvPattern.test(formData.cvv)) {
        errors.cvv = "CVV must be a 3-digit number";
      }
    }
    if (paymentMethod === "upi") {
      if (!formData.upiId) {
        errors.upiId = "UPI ID is required";
      } else if (!upiPattern.test(formData.upiId)) {
        errors.upiId = "UPI ID must contain '@'";
      }
    }
    return errors;
  };
  // Handle address selection and show payment form
  const handleAddressSelect = (addressId) => {
    setSelectedAddressId(addressId);
    setShowPaymentForm(true);
  };
  // Handle form submission and payment processing
  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validateForm();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }
    setErrors({});
    const paymentData = {
      ...formData,
      paymentMode: paymentMethod,
      addressId: selectedAddressId,
    };
    createPayment(customerId, paymentData)
      .then((response) => {
        if (response.data.paymentStatus === "Payment Successful") {
          createOrderFunc(customerId, response.data.paymentId, selectedAddressId);
          setShowSuccessPopup(true);
          setTimeout(() => {
            setShowSuccessPopup(false);
            navigate(`/order-sucess`);
            window.location.reload();
          }, 3000);
        } else {
          setShowFailurePopup(true);
          setTimeout(() => {
            navigate(`/cart`);
          }, 3000);
        }
      })
      .catch((error) => {
        console.error("Error creating payment", error);
        setShowFailurePopup(true);
      })
      .finally(() => {
        setFormData({
          cardNumber: "",
          nameOnCard: "",
          expiryDate: "",
          cvv: "",
          upiId: "",
          totalAmount: totalAmount,
        });
      });
  };
  // Function to create order after successful payment
  function createOrderFunc(customerId, paymentId, selectedAddressId) {
    createOrder(customerId, paymentId, selectedAddressId)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.error("Error creating order", error);
      });
  }
  return (
    <div className="container showaddresscontainer">
      {!showPaymentForm && (
        <SelectAddress
          setSelectedAddressId={handleAddressSelect}
          customerId={customerId}
        />
      )}
      {showPaymentForm && (
        <div className="payment-form-container">
          <div className="payment-container">
            <h2>Select Mode of Payment</h2>
            <div className="payment-methods">
              <div
                className={`payment-method ${paymentMethod === "card" ? "selected" : ""}`}
                onClick={() => setPaymentMethod("card")}
              >
                <FaCreditCard size={50} />
                <p>Card</p>
              </div>
              <div
                className={`payment-method ${paymentMethod === "upi" ? "selected" : ""}`}
                onClick={() => setPaymentMethod("upi")}
              >
                <FaGoogleWallet size={50} />
                <p>UPI</p>
              </div>
              <div
                className={`payment-method ${paymentMethod === "cod" ? "selected" : ""}`}
                onClick={() => setPaymentMethod("cod")}
              >
                <FaMoneyBillWave size={50} />
                <p>COD</p>
              </div>
            </div>
            <p>Total Amount:  <span>&#8377; </span> {totalAmount}</p>
            <form onSubmit={handleSubmit}>
              {paymentMethod === "card" && (
                <div className="form-group paymentform-group">
                  <label>Card Number</label>
                  <input
                    type="text"
                    name="cardNumber"
                    value={formData.cardNumber}
                    onChange={handleInputChange}
                  />
                  {errors.cardNumber && <p className="error">{errors.cardNumber}</p>}
                  <label>Name on Card</label>
                  <input
                    type="text"
                    name="nameOnCard"
                    value={formData.nameOnCard}
                    onChange={handleInputChange}
                  />
                  {errors.nameOnCard && <p className="error">{errors.nameOnCard}</p>}
                  <label>Expiry Date</label>
                  <input
                    type="text"
                    name="expiryDate"
                    value={formData.expiryDate}
                    onChange={handleInputChange}
                  />
                  {errors.expiryDate && <p className="error">{errors.expiryDate}</p>}
                  <label>CVV</label>
                  <input
                    type="text"
                    name="cvv"
                    value={formData.cvv}
                    onChange={handleInputChange}
                  />
                  {errors.cvv && <p className="error">{errors.cvv}</p>}
                </div>
              )}
              {paymentMethod === "upi" && (
                <div className="form-group paymentform-group">
                  <label>UPI ID</label>
                  <input
                    type="text"
                    name="upiId"
                    value={formData.upiId}
                    onChange={handleInputChange}
                  />
                  {errors.upiId && <p className="error">{errors.upiId}</p>}
                </div>
              )}
              {paymentMethod === "cod" && (
                <div className="form-group paymentform-group">
                  <p>Cash on Delivery</p>
                </div>
              )}
              <button type="submit" className="btn btn-primary paymentformbtn-primary">
                Submit
              </button>
            </form>
          </div>
          {showSuccessPopup && (
            <>
              <div className="overlay paymentoverlay"></div>
              <div className="popup success paymentpopup">
                <FaCheckCircle size={50} color="green" />
                <h2>Payment Successful!</h2>
                <p>Your payment has been processed successfully.</p>
                <button onClick={() => setShowSuccessPopup(false)}>
                  Close
                </button>
              </div>
            </>
          )}
          {showFailurePopup && (
            <>
              <div className="overlay paymentoverlay"></div>
              <div className="popup failure paymentpopup">
                <FaTimesCircle size={50} color="red" />
                <h2>Payment Failed!</h2>
                <p>There was an issue processing your payment. Please try again.</p>
                <button onClick={() => setShowFailurePopup(false)}>
                  Close
                </button>
              </div>
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default PaymentForm;