import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { updateitems, getItemById } from '../../service/ItemServices';
import '../style/Item.css';

function UpdateItems({ show, handleClose, productId, onUpdate }) {
    const [itemName, setItemName] = useState('');
    const [description, setDescription] = useState('');
    const [itemType, setType] = useState('');
    const [itemQuantity, setQuantity] = useState('');
    const [itemPrice, setPrice] = useState('');
    const [imageUrl, setImageUrl] = useState('');

    //updated successfull modal
    const [message, setMessage] = useState('');
    const [showSuccessModal, setShowSuccessModal] = useState(false);
    const handleSuccessClose = () => setShowSuccessModal(false);
    const handleSuccessShow = () => setShowSuccessModal(true);

    useEffect(() => {
        if (productId) {
            getItemById(productId).then((response) => {
                const item = response.data;
                setItemName(item.itemName);
                setDescription(item.description);
                setType(item.itemType);
                setQuantity(item.itemQuantity);
                setPrice(item.itemPrice);
                setImageUrl(item.imageUrl);
                console.log(response);
            }).catch(error => {
                console.error(error);
            });
        }
    }, [productId]);

    const saveProduct = () => {
        const item = { itemName, description, itemQuantity, itemPrice, imageUrl, itemType };
        updateitems(productId, item)
            .then(() => {
                setMessage('Item updated successfully!');
                onUpdate();
                handleSuccessShow();
                handleClose();
            })
            .catch(error => {
                setMessage('Error updating item');
                handleSuccessShow();
                console.error(error);
            });
    };

    return (

        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Update Product</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="formItemName">
                            <Form.Label>Item Name</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter the Item Name"
                                value={itemName}
                                onChange={(e) => setItemName(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="formDescription">
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter the Description"
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="formPrice">
                            <Form.Label>Price</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter the Price"
                                value={itemPrice}
                                onChange={(e) => setPrice(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="formQuantity">
                            <Form.Label>Quantity</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter the Quantity"
                                value={itemQuantity}
                                onChange={(e) => setQuantity(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="formType">
                            <Form.Label>Type</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter the Type"
                                value={itemType}
                                onChange={(e) => setType(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="formImageUrl">
                            <Form.Label>Image URL</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter the Image URL"
                                value={imageUrl}
                                onChange={(e) => setImageUrl(e.target.value)}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={saveProduct}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showSuccessModal} onHide={handleSuccessClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Item Updated</Modal.Title>
                </Modal.Header>
                <Modal.Body>{message}</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleSuccessClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>

        </>
    );
}

export default UpdateItems;
