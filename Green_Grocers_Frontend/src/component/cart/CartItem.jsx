// src/components/CartItem.jsx
import React, { useState } from 'react';
import { Button, Image } from 'react-bootstrap';
import { CartContext } from '../context/CartContext';
// import ProductDetailsModal from './ProductDetailsModal';
 
const CartItem = ({ item, removeItemFromCart, addItemToCart, saveForLater }) => {
  const { handleViewItem } = React.useContext(CartContext);
 
  const handleShowDetails = () => {
    setShowDetails(true);
    handleViewItem(item); // Track the viewed item
  };
 
  return (
    <div className="cart-item d-flex align-items-center">
      <Image src={item.imageUrl} rounded width="100" className="me-3" onClick={handleShowDetails} />
      <div>
        <h5>{item.itemName}</h5>
        <p>Price: <span>&#8377; </span>{item.itemPrice}</p>
        <p>Quantity: {item.quantity}</p>
        <div className="item-actions mt-2">
          <Button variant="secondary" size="sm" className="me-2" onClick={() => removeItemFromCart(item.itemId)}>-</Button>
          <span className="quantity">{item.quantity}</span>
          <Button variant="secondary" size="sm" className="ms-2" onClick={() => addItemToCart(item)}>+</Button>
          <Button variant="link" size="sm" className="ms-2 savelater" onClick={() => saveForLater(item)}>Save for Later</Button>
        </div>
      </div>
    </div>
  );
};
 
export default CartItem;
 