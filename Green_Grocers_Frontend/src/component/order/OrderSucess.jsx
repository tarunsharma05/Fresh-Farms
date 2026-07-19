import React from "react";
import { useNavigate } from "react-router-dom";
import TaskAltIcon from "@mui/icons-material/TaskAlt";
import "../style/order.css";
 
export default function OrderSuccess() {
  const navigator = useNavigate();
 
  function handleClick(e) {
    e.preventDefault();
    
    navigator("/");
    window.location.reload(); 
  }
 
  return (
    <div className="OrderSuccess-container text-center">
      <div className="OrderSuccess-content">
        <div className="OrderSuccess-icon-container">
          {" "}
          {/* Add a container */}
          <TaskAltIcon
            className="OrderSuccess-icon"
            style={{ fontSize: "12rem" }}
          />{" "}
          {/* Adjust icon size here */}
        </div>
        <h2 className="OrderSuccess-title">Order Success</h2>
        <p className="OrderSuccess-message">
          Thank you for choosing us! We appreciate your Order.
        </p>
        <p className="OrderSuccess-message-small">Have A Great Day</p>
        <button className="OrderSuccess-button" onClick={handleClick}>
          Go to Home
        </button>
      </div>
    </div>
  );
}