import React, { useContext, useState } from 'react';
import { Button, Card, Col, Container, Form, Modal, Row, Table } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { CSSTransition, TransitionGroup } from 'react-transition-group';
import { CartContext } from '../context/CartContext';
import '../style/Cart.css';
import CartItem from './CartItem';
 
const Cart = () => {
  // Extracting necessary values and functions from the CartContext
  const {
    cart,
    savedForLater,
    handleRemoveItemFromCart,
    handleClearCart,
    handleAddItemToCart,
    handleSaveForLater,
    handleMoveToCart,
    handleRemoveFromSavedForLater
  } = useContext(CartContext);
 
  // React hook for navigation
  const navigate = useNavigate();
 
  // State variables for modals and promo code
  const [showModal, setShowModal] = useState(false);
  const [promoCode, setPromoCode] = useState('');
  const [discount, setDiscount] = useState(0);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
 
  // State variables for button modals
  const [showSaveForLaterModal, setShowSaveForLaterModal] = useState(false);
  const [showRemoveFromWishlistModal, setShowRemoveFromWishlistModal] = useState(false);
  const [showMoveToCartModal, setShowMoveToCartModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');
 
  // Function to show the modal for confirming cart clearing
  const handleClearCartClick = () => setShowModal(true);
 
  // Function to close the modal
  const handleCloseModal = () => {
    setShowModal(false);
    setShowSaveForLaterModal(false);
    setShowRemoveFromWishlistModal(false);
    setShowMoveToCartModal(false);
  };
 
  // Function to confirm clearing the cart
  const handleConfirmClearCart = () => {
    // Clear the cart using context function
    handleClearCart();
    // Close the modal
    setShowModal(false);
  };
 
  // Function to apply promo code
  const handleApplyPromoCode = () => {
    const totalAmount = cart.items.reduce((total, item) => total + (item.itemPrice * item.quantity), 0);
    if (promoCode === 'GreenCapgemini') {
      setDiscount(totalAmount * 0.1);
      setShowSuccessModal(true);
    } else {
      setDiscount(0);
      setShowErrorModal(true);
    }
  };
 
  // Calculate total amounts for display
  const totalAmount = cart.items.reduce((total, item) => total + (item.itemPrice * item.quantity), 0);
  const discountedTotal = totalAmount - discount;
  const taxes = totalAmount * 0.07; // Example tax calculation
  const shipping = 8.99; // Example shipping cost
  const amountToPay = discountedTotal + taxes + shipping;
 
  // Function to handle payment and navigate to the payment page
  const handlePayment = (amountToPay) => {
    console.log(`Amount to Pay: $${amountToPay}`);
    navigate(`/payments/createpayment?totalAmount=${amountToPay}`);
  };
 
  const handleContextMenu = (event) => {
    event.preventDefault();
    // Custom context menu logic
    console.log("Context menu opened");
  };
 
  // Function to handle saving item for later
  const handleSaveForLaterClick = (item) => {
    handleSaveForLater(item);
    setModalMessage(`${item.itemName} has been saved for later.`);
    setShowSaveForLaterModal(true);
  };
 
  // Function to handle moving item to cart
  const handleMoveToCartClick = (item) => {
    handleMoveToCart(item);
    setModalMessage(`${item.itemName} has been moved to the cart.`);
    setShowMoveToCartModal(true);
  };
 
  // Function to handle removing item from saved for later
  const handleRemoveFromSavedForLaterClick = (itemId) => {
    const item = savedForLater.find(savedItem => savedItem.itemId === itemId);
    handleRemoveFromSavedForLater(itemId);
    setModalMessage(`${item.itemName} has been removed from your wishlist.`);
    setShowRemoveFromWishlistModal(true);
  };
 
  return (
    <div className='cart-container'>
      <Container className="allproducts cart-container mt-4 mb-5">
        <h3 className="text-center mb-4">Your Cart</h3>
        {cart.items.length > 0 ? (
          <Row>
            <Col lg={8} md={12}>
              <TransitionGroup className="cart-item-list">
                {cart.items.map((item) => (
                  <CSSTransition key={item.itemId} timeout={300} classNames="cart-item">
                    <Col xs={12} className="mb-4">
                      <Card className="h-100 cart-item-card" onContextMenu={handleContextMenu}>
                        <Card.Body>
                          <CartItem
                            item={item}
                            removeItemFromCart={handleRemoveItemFromCart}
                            addItemToCart={handleAddItemToCart}
                            saveForLater={() => handleSaveForLaterClick(item)}
                          />
                        </Card.Body>
                      </Card>
                    </Col>
                  </CSSTransition>
                ))}
              </TransitionGroup>
            </Col>
            <Col lg={4} md={12}>
              <Card className="mt-0 bg-light p-3 cart-summary-card text-center">
                <h3>Summary</h3>
                <Table responsive>
                  <tbody>
                    <tr>
                      <td>Total Items:</td>
                      <td>{cart.items.reduce((total, item) => total + item.quantity, 0)}</td>
                    </tr>
                    <tr>
                      <td>Total Amount:</td>
                      <td><span>&#8377; </span>{isNaN(totalAmount) ? '0.00' : totalAmount.toFixed(2)}</td>
                    </tr>
                    <tr>
                      <td>Taxes:</td>
                      <td><span>&#8377; </span>{isNaN(taxes) ? '0.00' : taxes.toFixed(2)}</td>
                    </tr>
                    <tr>
                      <td>Shipping:</td>
                      <td><span>&#8377; </span>{isNaN(shipping) ? '0.00' : shipping.toFixed(2)}</td>
                    </tr>
                    {discount > 0 && (
                      <tr>
                        <td>Discount:</td>
                        <td>-<span>&#8377; </span>{isNaN(discount) ? '0.00' : discount.toFixed(2)}</td>
                      </tr>
                    )}
                    <tr>
                      <td>Amount to Pay:</td>
                      <td><span>&#8377; </span>{isNaN(amountToPay) ? '0.00' : amountToPay.toFixed(2)}</td>
                    </tr>
                  </tbody>
                </Table>
                <Form.Group controlId="promoCode">
                  <Form.Label>Promo Code</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter promo code"
                    value={promoCode}
                    onChange={(e) => setPromoCode(e.target.value)}
                  />
                  <Button variant="primary" className="mt-2" onClick={handleApplyPromoCode}>Apply</Button>
                </Form.Group>
                <Button variant="danger" className="mt-2" onClick={handleClearCartClick}>Clear Cart</Button>
                <Button variant="success" className="mt-2" onClick={() => handlePayment(amountToPay)}>Buy Now</Button>
              </Card>
            </Col>
          </Row>
        ) : (
          <div className="text-center categoryh1">
            <p>Your cart is empty</p>
          </div>
        )}
 
        <Modal show={showModal} onHide={handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>Confirm Clear Cart</Modal.Title>
          </Modal.Header>
          <Modal.Body>Are you sure you want to clear your cart?</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>Cancel</Button>
            <Button variant="danger" onClick={handleConfirmClearCart}>Clear Cart</Button>
          </Modal.Footer>
        </Modal>
 
        <Modal show={showSuccessModal} onHide={() => setShowSuccessModal(false)} centered>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Promo code applied successfully!</Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={() => setShowSuccessModal(false)}>Close</Button>
          </Modal.Footer>
        </Modal>
 
        <Modal show={showErrorModal} onHide={() => setShowErrorModal(false)} centered>
          <Modal.Header closeButton>
            <Modal.Title>Error</Modal.Title>
          </Modal.Header>
          <Modal.Body>Invalid promo code. Please try again.</Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={() => setShowErrorModal(false)}>Close</Button>
          </Modal.Footer>
        </Modal>
 
        <Modal show={showSaveForLaterModal} onHide={handleCloseModal} centered>
          <Modal.Header closeButton>
            <Modal.Title>Saved for Later</Modal.Title>
          </Modal.Header>
          <Modal.Body>{modalMessage}</Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={handleCloseModal}>Close</Button>
          </Modal.Footer>
        </Modal>
 
        <Modal show={showMoveToCartModal} onHide={handleCloseModal} centered>
          <Modal.Header closeButton>
            <Modal.Title>Moved to Cart</Modal.Title>
          </Modal.Header>
          <Modal.Body>{modalMessage}</Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={handleCloseModal}>Close</Button>
          </Modal.Footer>
        </Modal>
 
        <Modal show={showRemoveFromWishlistModal} onHide={handleCloseModal} centered>
          <Modal.Header closeButton>
            <Modal.Title>Removed from Wishlist</Modal.Title>
          </Modal.Header>
          <Modal.Body>{modalMessage}</Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={handleCloseModal}>Close</Button>
          </Modal.Footer>
        </Modal>
      </Container>
 
      {savedForLater.length > 0 && (
        <div className='wishlist'>
          <Container>
            <div className='allproducts'>
              <h3 className="text-center mt-5 pt-4 mb-4">Wish List</h3>
              <Row>
                {savedForLater.map((item, index) => (
                  <Col key={item.itemId} xs={12} md={6} lg={4} className="mb-4">
                    <Card className="h-100 cart-item-card">
                      <Card.Body>
                        <div className="cart-item d-flex align-items-center">
                          <img src={item.imageUrl} alt={item.itemName} className="me-3" width="100" />
                          <div>
                            <h5>{item.itemName}</h5>
                            <p>Price: <span>&#8377; </span>{item.itemPrice.toFixed(2)}</p>
                            <Button variant="link" size="sm" className="mt-0 savelater" onClick={() => handleMoveToCartClick(item)}>
                              Move to Cart
                            </Button>
                            <Button variant="link" size="sm" className="mt-0 ml-0 savelater" onClick={() => handleRemoveFromSavedForLaterClick(item.itemId)}>
                              Remove
                            </Button>
                          </div>
                        </div>
                      </Card.Body>
                    </Card>
                  </Col>
                ))}
              </Row>
            </div>
          </Container>
        </div>
      )}
    </div>
  );
};
 
export default Cart;