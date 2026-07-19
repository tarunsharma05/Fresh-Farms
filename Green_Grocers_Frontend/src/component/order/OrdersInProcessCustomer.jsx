import React from "react";
import { useState, useEffect } from "react";
import { Container, Row, Col, Card, Button, Modal } from "react-bootstrap";
import { FaCheckCircle, FaTruck, FaCog } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getOrdersByCustomer } from "../../service/OrderService";
export default function OrdersInProcessCustomer() {
  const [orders, setOrders] = useState([]);
  const navigator = useNavigate();
  const { customerId } = useParams();
 
  useEffect(() => {
    if (customerId) {
      getOrdersByCustomer(customerId)
        .then((response) => {
          setOrders(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [customerId]);
  const viewDetails = (orderId) => {
    navigator(`/customer/orders-details/inProcess/${orderId}`);
  };
  return (
    <>
      <h2 className="text-center">Orders in Process</h2>
      <Container>
        <Row className="justify-content-center mt-3">
          {orders
            .filter(
              (order) =>
                order.orderStatus == "Shipping" ||
                order.orderStatus == "Processing"
            )
            .map((order) => (
              <Col md={12} sm={12} xs={12} key={order.orderId} className="mb-2">
                <Card
                  className="mb-0 ordercustomercard"
                  onClick={() => viewDetails(order.orderId)}
                >
                  <Card.Body className="mt-0">
                    <div>
                      <div>
                        <Card.Title className="mt-0 text-center">
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
                        </Card.Title>
                        <div className="order-details d-flex justify-content-between">
                          <Card.Text>
                            {" "}
                            <h6>Order Id: {order.orderId}</h6>{" "}
                          </Card.Text>
                          <Card.Text>
                            {" "}
                            <h6>Placed Date: {order.orderDate}</h6>{" "}
                          </Card.Text>
                          <Card.Text>
                            <h6>Total Amount: {order.payment.totalAmount}</h6>
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