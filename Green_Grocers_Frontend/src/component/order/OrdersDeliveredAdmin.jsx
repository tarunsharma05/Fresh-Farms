import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import "../style/order.css";
import { FaCheckCircle, FaTruck, FaCog, FaFilter } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
export default function OrdersDeliveredAdmin() {
  const [orders, setOrders] = useState([]);
  const [selectedStatusMap, setSelectedStatusMap] = useState({});
  const navigator = useNavigate();
 
  useEffect(() => {
    fetchOrders();
  }, []);
 
  const fetchOrders = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/orders/all");
      const initialSelectedStatusMap = {};
      response.data.forEach((order) => {
        initialSelectedStatusMap[order.orderId] = "";
      });
      setSelectedStatusMap(initialSelectedStatusMap);
      setOrders(response.data);
    } catch (error) {
      console.error("Error fetching orders:", error);
    }
  };
 
  const filterOrdersByRecent = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/orders/sortedByDate"
      );
      setOrders(response.data);
      console.log(orders);
    } catch (error) {
      console.error("Error filtering orders by recent:", error);
    }
  };
  const viewDetails = (orderId) => {
    navigator(`/admin/orders-details/${orderId}`);
  };
  return (
    <>
      <div className="container ordercontainer">
        <h2 className="text-center">Orders Delivered</h2>
        <button
          className="order-filter-button btn-success"
          onClick={filterOrdersByRecent}
        >
          {/* <FaFilter className="mr-1" /> */}
          Filter Orders By Recent Date
        </button>
        <hr />
        <div className="card-container ordercard-container">
          {orders
            .filter(
              (order) =>
                order.orderStatus == "Shipped" ||
                order.orderStatus == "Processing" ||
                order.orderStatus == "Delivered"
            )
            .map((order) => (
              <div
                className="allordercard text-center"
                key={order.orderId}
                onClick={() => viewDetails(order.orderId)}
              >
                <div>
                  <h5> Order ID: {order.orderId}</h5>
                </div>
                <div className="card-body">
                  <h5 className="card-title ordercard-title">
                    {order.orderStatus}
                    {order.orderStatus === "Delivered" && (
                      <FaCheckCircle className="text-success ml-2" />
                    )}
                    {order.orderStatus === "Processing" && (
                      <FaCog className="text-warning ml-2" />
                    )}
                    {order.orderStatus === "Shipped" && (
                      <FaTruck className="text-primary ml-2" />
                    )}
                  </h5>
                  <p className="card-text">
                    <h6>Placed Date: {order.orderDate}</h6>
                  </p>
 
                  <div className="row">
                    <div className="col">
                      <p>
                        <h6>Total Amount:{order.payment.totalAmount} </h6>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            ))}
        </div>
      </div>
    </>
  );
}