import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserCircle, faAddressCard, faClipboardList, faMoneyBillAlt, faUserLock, faShippingFast, faTimes } from '@fortawesome/free-solid-svg-icons';
import '../style/Profile.css';
import { getCustomerById } from '../../service/AdminService';
import UserProfile from './UserProfile';
import { useUser } from "../context/UserContext.jsx";
 
function ProfilePage() {
    const navigate = useNavigate();
    const { customerId } = useUser();
    const [customer, setCustomer] = useState({});
    const [showModal, setShowModal] = useState(false);
 
    // Fetch customer data by ID on component mount
    useEffect(() => {
        getCustomerById(customerId)
            .then((response) => {
                setCustomer(response.data);
                console.log("Customer data fetched successfully:", response.data);
            })
            .catch((error) => {
                // console.error("Error fetching customer data:", error);
                handleError(error)
            });
    }, [customerId]);
 
    // Handle click to open edit profile modal
    const handleEditClick = () => {
        setShowModal(true);
    };
 
    // Handle close modal
    const handleCloseModal = () => {
        setShowModal(false);
    };
 
    // Update customer state with updated data
    const handleUpdate = (updatedCustomer) => {
        setCustomer(updatedCustomer);
        console.log("Customer data updated:", updatedCustomer);
    };
 
    return (
        <div className='profile'>
        <Container className="mt-5 ">
            <Row className="justify-content-center">
                {/* Profile Card */}
                <Col lg={5} md={6} sm={8} className="mb-4">
                    <Card className="profile-card" onClick={handleEditClick}>
                        <Card.Body className="d-flex align-items-center ">
                            <FontAwesomeIcon
                                icon={faUserCircle}
                                size="6x"
                                className="mr-4"
                            />
                            <div>
                                <h5 className='mb-3'>Hello👋 {customer.name}</h5>
                                <p>{customer.email}</p>
                                <p>{customer.phone}</p>
                            </div>
                        </Card.Body>
                    </Card>
                </Col>
                {/* Navigation Cards */}
                <Col lg={7} md={6} sm={8}>
                    <Row className="justify-content-center">
                        <Col md={6} xs={12} className="mb-3">
                            <Card className="text-center profile-nav-card profile-card" onClick={() => navigate('/address')}>
                                <Card.Body>
                                    <Card.Title >
                                        <FontAwesomeIcon
                                            icon={faAddressCard}
                                            className="mr-3"
                                        />
                                        Addresses
                                    </Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col md={6} xs={12} className="mb-3">
                            <Card className="text-center profile-nav-card profile-card" onClick={() => navigate(`/customer/orders-inProcess/${customerId}`)}>
                                <Card.Body>
                                    <Card.Title>
                                        <FontAwesomeIcon
                                            icon={faShippingFast}
                                            className="mr-3"
                                        />
                                        Track Order
                                    </Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col md={6} xs={12} className="mb-3">
                            <Card className="text-center profile-card" onClick={() => navigate(`/customer/orders-delivered/${customerId}`)}>
                                <Card.Body>
                                    <Card.Title>
                                        <FontAwesomeIcon
                                            icon={faClipboardList}
                                            className="mr-3"
                                        />
                                        Order History
                                    </Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col md={6} xs={12} className="mb-3">
                            <Card className="text-center profile-nav-card profile-card" onClick={() => navigate(`/customer/payment/${customerId}`)}>
                                <Card.Body>
                                    <Card.Title>
                                        <FontAwesomeIcon
                                            icon={faMoneyBillAlt}
                                            className="mr-3"
                                        />
                                        Payment History
                                    </Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col md={6} xs={12} className="mb-3">
                            <Card className="text-center profile-nav-card profile-card" onClick={() => navigate(`/delete-user/${btoa(customerId)}`)}>
                                <Card.Body>
                                    <Card.Title>
                                        <FontAwesomeIcon
                                            icon={faUserLock}
                                            className="mr-3"
                                        />
                                        Account Privacy
                                    </Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                </Col>
            </Row>
 
            {/* Edit Profile Modal */}
            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header>
                    <Modal.Title>Edit Profile</Modal.Title>
                    <Button variant="" onClick={handleCloseModal}>
                        <FontAwesomeIcon icon={faTimes} />
                    </Button>
                </Modal.Header>
                <Modal.Body>
                    <UserProfile
                        customerId={customerId}
                        onClose={handleCloseModal}
                        onUpdate={handleUpdate}
                    />
                </Modal.Body>
            </Modal>
        </Container>
        </div>
    );
}
 
export default ProfilePage;