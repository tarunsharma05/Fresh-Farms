import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import "../style/order.css";
import Dropdown from "react-bootstrap/Dropdown";
import { FaCheckCircle, FaTruck, FaCog, FaFilter } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
export default function OrdersPendingAdmin() {
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
    } catch (error) {
      console.error("Error filtering orders by recent:", error);
    }
  };
 
  const handleStatusChange = async (orderIdToUpdate, status) => {
    try {
      // Send PUT request to update order status for specific orderId
      await axios.put(
        `http://localhost:8080/api/orders/${orderIdToUpdate}/status`,
        null,
        {
          params: { status },
        }
      );
      // Refresh orders after update
      fetchOrders();
    } catch (error) {
      console.error("Error updating order status:", error);
    }
  };
 
  const viewDetails = (orderId) => {
    navigator(`/admin/orders-details/${orderId}`);
  };
  return (
    <>
      <div className="container ordercontainer">
        <h2 className="text-center">Orders In Process</h2>
        <button
          className="filter-button btn-success"
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
                order.orderStatus == "Processing"
            )
            .map((order) => (
              <div
                className="allordercard text-center"
                key={
                  order.orderId
                } /*onClick={() => viewDetails(order.orderId)}*/
              >
                <div>
                  <h5> Order ID: {order.orderId}</h5>
                </div>
                <div className="card-body ">
                  <h5 className="card-title ordercard-title">
                    {order.orderStatus}
                    {order.orderStatus === "delivered" && (
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
                        <h6>Total Amount: </h6>
                        <Dropdown className="update-status-dropdown">
                          <Dropdown.Toggle
                            variant="success"
                            id={`dropdown-basic-${order.orderId}`}
                          >
                            {selectedStatusMap[order.orderId] ||
                              "Update Status"}
                          </Dropdown.Toggle>
                          <Dropdown.Menu>
                            <Dropdown.Item
                              onClick={() => {
                                setSelectedStatusMap((prevState) => ({
                                  ...prevState,
                                  [order.orderId]: "Shipped",
                                }));
                                handleStatusChange(order.orderId, "Shipped");
                              }}
                            >
                              Shipped
                            </Dropdown.Item>
                            <Dropdown.Item
                              onClick={() => {
                                setSelectedStatusMap((prevState) => ({
                                  ...prevState,
                                  [order.orderId]: "Processing",
                                }));
                                handleStatusChange(order.orderId, "Processing");
                              }}
                            >
                              Processing
                            </Dropdown.Item>
                            <Dropdown.Item
                              onClick={() => {
                                setSelectedStatusMap((prevState) => ({
                                  ...prevState,
                                  [order.orderId]: "Delivered",
                                }));
                                handleStatusChange(order.orderId, "Delivered");
                              }}
                            >
                              Delivered
                            </Dropdown.Item>
                          </Dropdown.Menu>
                        </Dropdown>
                        <div className="view-details-button">
                          <button
                            type="button"
                            class="order-details-button btn btn-success"
                            onClick={() => viewDetails(order.orderId)}
                          >
                            View Details
                          </button>
                        </div>
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