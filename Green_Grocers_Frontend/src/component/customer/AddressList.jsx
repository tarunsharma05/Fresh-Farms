import React, { useEffect, useState } from 'react';
import { getAddressesByCustomerId, deleteAddressById } from '../../service/AdminService';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrash, faPlus, faTimes, faLocationDot } from '@fortawesome/free-solid-svg-icons';
import { Container, Row, Col, Card, Button, Modal } from 'react-bootstrap';
import AddressForm from './AddressForm';
import { useUser } from "../context/UserContext.jsx";

function AddressList() {
    // State to hold the list of addresses
    const [addresses, setAddresses] = useState([]);
    // State to control the visibility of the delete confirmation modal
    const [showModal, setShowModal] = useState(false);
    // State to store the selected address ID for deletion
    const [selectedAddressId, setSelectedAddressId] = useState(null);
    // Placeholder for customer ID (for simplicity)

    const { customerId } = useUser();

    // State to control the visibility of the add/edit address modal
    const [showAddModal, setShowAddModal] = useState(false);
    // State to store the selected address ID for editing
    const [selectedEditAddressId, setSelectedEditAddressId] = useState(null);

    // Handle adding a new address
    const handleAddAddress = () => {
        setSelectedEditAddressId(null); // Ensure this is reset
        setShowAddModal(true);
    };

    // Handle closing the add/edit address modal
    const handleCloseAddModal = () => {
        setShowAddModal(false);
    };

    // Fetch addresses for the customer
    const fetchAddresses = () => {
        getAddressesByCustomerId(customerId).then((response) => {
            setAddresses(response.data);
        }).catch(error => {
            console.error('Error fetching addresses:', error);
        });
    };

    // Fetch addresses when the component mounts
    useEffect(() => {
        fetchAddresses();
    }, []);

    // Handle deleting an address
    const handleDeleteClick = (addressId) => {
        setSelectedAddressId(addressId);
        setShowModal(true);
    };

    // Confirm deletion of the address
    const handleConfirmDelete = () => {
        if (selectedAddressId !== null) {
            deleteAddressById(selectedAddressId).then(() => {
                setAddresses(addresses.filter(address => address.addressId !== selectedAddressId));
                setShowModal(false);
                setSelectedAddressId(null);
            }).catch(error => {
                console.error('Error deleting address:', error);
                setShowModal(false);
                setSelectedAddressId(null);
            });
        }
    };

    // Cancel the deletion process
    const handleCancelDelete = () => {
        setShowModal(false);
        setSelectedAddressId(null);
    };

    // Handle editing an address
    const handleEditClick = (addressId) => {
        setSelectedEditAddressId(addressId);
        setShowAddModal(true);
    };

    return (
        <div className='allproducts mt-4'>
            <h3><center>Addresses</center></h3>
            <Container>
                <Row>
                    {addresses.map((address) => (
                        <Col md={12} sm={12} xs={12} key={address.addressId} className="mb-4">
                            <Card className="h-100 profile-card">
                                <Card.Body>
                                    <div className="d-flex justify-content-between align-items-center">
                                        <div>
                                            <Card.Title>
                                                <FontAwesomeIcon
                                                    icon={faLocationDot}
                                                    className="mr-4"
                                                />
                                                {address.city}
                                            </Card.Title>
                                            <Card.Text>
                                                House no: {address.houseNo}, {address.landmark}, {address.city}-{address.pinCode}, {address.state}, {address.country}
                                            </Card.Text>
                                        </div>
                                        <div className="d-flex">
                                            <Button
                                                variant="outline-primary"
                                                className="mr-2"
                                                onClick={() => handleEditClick(address.addressId)}
                                            >
                                                <FontAwesomeIcon icon={faEdit} />
                                            </Button>
                                            <Button
                                                variant="outline-danger"
                                                onClick={() => handleDeleteClick(address.addressId)}
                                            >
                                                <FontAwesomeIcon icon={faTrash} />
                                            </Button>
                                        </div>
                                    </div>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            </Container>

            {/* Confirmation Modal */}
            <Modal show={showModal} onHide={handleCancelDelete}>
                <Modal.Header>
                    <Modal.Title>Confirm Delete</Modal.Title>
                    <Button variant="success" onClick={handleCancelDelete}>
                        <FontAwesomeIcon icon={faTimes} />
                    </Button>
                </Modal.Header>
                <Modal.Body>Are you sure you want to delete this address?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCancelDelete}>
                        Cancel
                    </Button>
                    <Button variant="danger" onClick={handleConfirmDelete}>
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>

            {/* Add/Edit Address Modal */}
            <Modal show={showAddModal} onHide={handleCloseAddModal}>
                <Modal.Header>
                    <Modal.Title>{selectedEditAddressId ? 'Edit Address' : 'Add New Address'}</Modal.Title>
                    <Button variant="success" onClick={handleCloseAddModal}>
                        <FontAwesomeIcon icon={faTimes} />
                    </Button>
                </Modal.Header>
                <Modal.Body>
                    <AddressForm
                        addressId={selectedEditAddressId}
                        onClose={handleCloseAddModal}
                        customerId={customerId}
                        onUpdate={fetchAddresses}
                    />
                </Modal.Body>
            </Modal>

            {/* Add New Address Button */}
            <div className="text-center mt-4">
                <Button variant="primary" onClick={handleAddAddress}>
                    <FontAwesomeIcon icon={faPlus} className="mr-2" />
                    Add New Address
                </Button>
            </div>
        </div>
    );
}

export default AddressList;
