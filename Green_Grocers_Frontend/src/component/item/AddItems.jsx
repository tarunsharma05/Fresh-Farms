import React, { useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import { additems } from '../../service/ItemServices';
import '../style/Item.css';
 
function AddItems({ show, handleClose, onAdd }) {
    const [itemName, setItemName] = useState('');
    const [description, setDescription] = useState('');
    const [itemType, setType] = useState('');
    const [itemQuantity, setQuantity] = useState('');
    const [itemPrice, setPrice] = useState('');
    const [imageUrl, setImageUrl] = useState('');
 
    const [message, setMessage] = useState('');
    const [showSuccessModal, setShowSuccessModal] = useState(false);
    const [error, setError] = useState('');
 
    const handleSuccessClose = () => setShowSuccessModal(false);
    const handleSuccessShow = () => setShowSuccessModal(true);
 
    function saveProduct() {
       
        // Validation
        if (isNaN(itemPrice) || itemPrice <= 0) {
            setError('Price must be a positive number');
        }
 
        const item = { itemName, description, itemQuantity, itemPrice, imageUrl, itemType };
        additems(item).then((response) => {
            setMessage('Item added successfully!');
            onAdd();
            handleSuccessShow();
            handleClose();
            setItemName('');
            setDescription('');
            setType('');
            setQuantity('');
            setPrice('');
            setImageUrl('');
        }).catch(error => {
            setMessage('Fill all fields properly');
            handleSuccessShow();
            console.log(error);
        });
    }
 
    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Items</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
 
                        <Form.Group className='mb-2'>
                            <Form.Label>Item Name</Form.Label>
                            <Form.Control type='text'
                                placeholder='Enter the Item Name'
                                value={itemName}
                                onChange={(e) => setItemName(e.target.value)}
                            />
                        </Form.Group>
 
                        <Form.Group className='mb-2'>
                            <Form.Label>Description</Form.Label>
                            <Form.Control type='text'
                                placeholder='Enter the Description'
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                            />
                        </Form.Group>
 
                        <Form.Group className='mb-2'>
                            <Form.Label>Price</Form.Label>
                            <Form.Control type='text'
                                placeholder='Enter the Price'
                                value={itemPrice}
                                onChange={(e) => setPrice(e.target.value)}
                            />
                        </Form.Group>
                        {error && <div className="text-danger mb-2">{error}</div>}
 
                        <Form.Group className='mb-2'>
                            <Form.Label>Quantity</Form.Label>
                            <Form.Control type='text'
                                placeholder='Enter the Quantity (Example- 1 kg or 2 piece)'
                                value={itemQuantity}
                                onChange={(e) => setQuantity(e.target.value)}
                            />
                        </Form.Group>
 
                        <Form.Group className='mb-2'>
                            <Form.Label>Type</Form.Label>
                            <Form.Control type='text'
                                placeholder='Enter the Type'
                                value={itemType}
                                onChange={(e) => setType(e.target.value)}
                            />
                        </Form.Group>
 
                        <Form.Group className='mb-2'>
                            <Form.Label>Image URL</Form.Label>
                            <Form.Control type='text'
                                placeholder='Enter the Image URL'
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
                        Add Item
                    </Button>
                </Modal.Footer>
            </Modal>
 
            <Modal show={showSuccessModal} onHide={handleSuccessClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Item Added</Modal.Title>
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
 
export default AddItems;