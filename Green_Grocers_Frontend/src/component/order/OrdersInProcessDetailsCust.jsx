import React from "react";
import { FaTruck, FaCog } from "react-icons/fa";
import { useParams } from "react-router-dom";
import { getOrderbyId } from "../../service/OrderService";
import { useEffect, useState } from "react";
import '../style/order.css';
export default function OrdersInProcessDetailsCust() {
  const [order, setOrder] = useState("");
  const { orderId } = useParams();
  useEffect(() => {
    if (orderId) {
      getOrderbyId(orderId)
        .then((response) => {
          setOrder(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [orderId]);

  return (
    <div className="order-details-container mt-4">
      {order && (
        <div className="card order-details-card  ">
          <div className="card-header order-details-card-header">
            Order {order.orderStatus}
            {order.orderStatus === "Processing" && (
              <FaCog className="text-warning ml-2" />
            )}
            {order.orderStatus === "Shipped" && (
              <FaTruck className="text-primary ml-2" />
            )}
          </div>
          <div className="card-body order-details-card-body">
            <h5 className="card-title order-details-card-title">
              Order ID# {order.orderId}{" "}
            </h5>
            <p className="card-text">Placed at : {order.orderDate} </p>
            <hr />
            <h5 className="card-title order-details-card-text">
              Items Ordered:{" "}
            </h5>
            {/* Map through order items and display them */}
            <table className="table order-details-table">
              <thead>
                <tr>
                  <th scope="col">Item Name</th>
                  <th scope="col"></th>
                  <th scope="col"></th>{" "}
                  {/* Add an empty header to balance the layout */}
                  <th scope="col">Item Price</th>
                </tr>
              </thead>
              <tbody>
                {order.orderedItems.map((item) => (
                  <tr key={item.itemId}>
                    <td>{item.itemName}</td>
                    <td></td>
                    <td></td> {/* Add an empty cell to balance the layout */}
                    <td>{item.itemPrice}</td>
                  </tr>
                ))}
              </tbody>
            </table>

            <hr />
            <p>
              <strong> Total Bill:</strong> {order.payment.totalAmount}
            </p>
            <hr />
            <h5 className="card-title">Shipping Information:</h5>
            <p>
              <strong>
                Delivered at: {order.address.houseNo}, {order.address.landmark},
                {order.address.city}
              </strong>
            </p>
            <p>
              <strong>
                State: {order.address.state},{order.address.country}
              </strong>{" "}
            </p>
            <p>
              <strong>Zip Code: {order.address.pinCode}</strong>{" "}
            </p>
          </div>
        </div>
      )}
    </div>
  );
}