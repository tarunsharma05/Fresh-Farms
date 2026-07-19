import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import '../style/order.css';
import { getOrdersByCustomer } from "../../service/OrderService";
import { Container, Row, Col, Card } from "react-bootstrap";
import { FaCheckCircle, FaFilter } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
 
export default function OrdersDeliveredCustomer() {
  const [orders, setOrders] = useState([]);
  const navigator = useNavigate();
 
  const { customerId } = useParams();
  useEffect(() => {
    if (customerId) {
      getOrdersByCustomer(customerId)
        .then((response) => {
          setOrders(response.data);
          console.log(orders);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [customerId]);
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
  const viewDetails = (orderId) => {
    navigator(`/customer/orders-details/delivered/${orderId}`);
  };
  return (
    <>
      <Container>
        <h2 className="text-center">Order History</h2>
        <button
          className="order-filter-button btn-success"
          onClick={filterOrdersByRecent}
        >
          <FaFilter className="mr-1" />
          Filter Orders By Recent Date
        </button>
        <hr />
        <Row className="justify-content-center mt-3">
          {orders
            .filter((order) => order.orderStatus === "Delivered")
            .map((order) => (
              <Col md={12} sm={12} xs={12} key={order.orderId} className="mb-2">
                <Card
                  className="mb-0 ordercustomercard"
                  onClick={() => viewDetails(order.orderId)}
                >
                  <Card.Body className="mt-0">
                    <div>
                      <div>
                        {/* Order status displayed at the top center */}
                        <Card.Title className="mt-0 text-center">
                          {order.orderStatus}
                          {order.orderStatus === "Delivered" && (
                            <FaCheckCircle className="text-success ml-2" />
                          )}
                        </Card.Title>
                        <div className="order-details d-flex justify-content-between">
                          <Card.Text>
                            {" "}
                            <h6 className="ordercustomerh6">
                              Order Id: {order.orderId}
                            </h6>{" "}
                          </Card.Text>
                          <Card.Text>
                            {" "}
                            <h6 className="ordercustomerh6">
                              Placed Date: {order.orderDate}
                            </h6>{" "}
                          </Card.Text>
                          <Card.Text>
                            <h6 className="ordercustomerh6">
                              Total Amount:{order.payment.totalAmount}
                            </h6>
                          </Card.Text>
                        </div>
                      </div>
                    </div>
                  </Card.Body>
                </Card>
              </Col>
            ))}
        </Row>
      </Container>
    </>
  );
}