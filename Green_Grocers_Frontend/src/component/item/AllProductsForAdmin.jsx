import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Modal } from 'react-bootstrap';
import { allproductList, deleteitems } from '../../service/ItemServices';
import UpdateItems from '../item/UpdateItems';
import AddItems from '../item/AddItems';
import { useNavigate } from 'react-router-dom';
import '../style/Item.css';

function AllProductsForAdmin() {

    const [products, setProducts] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [message, setMessage] = useState('');
    const [showUpdateModal, setShowUpdateModal] = useState(false);
    const [showAddModal, setShowAddModal] = useState(false);
    const [selectedProductId, setSelectedProductId] = useState(null);
    const navigator = useNavigate();


    //handle for delete
    const handleClose = () => setShowModal(false);
    const handleShow = () => setShowModal(true);

    //handle for update item
    const handleUpdateClose = () => setShowUpdateModal(false);
    const handleUpdateShow = (productId) => {
        setSelectedProductId(productId);
        setShowUpdateModal(true);
    };

    //handle for add item
    const handleAddClose = () => setShowAddModal(false);
    const handleAddShow = () => setShowAddModal(true);

    useEffect(() => {
        allproductList()
            .then((response) => {
                setProducts(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    const deleteProduct = (itemId) => {
        deleteitems(itemId)
            .then((response) => {
                setMessage(response.data);
                setProducts(products.filter(product => product.itemId !== itemId));
                handleShow();
            })
            .catch((error) => {
                setMessage('Error deleting item');
                console.log(error);
                handleShow();
            });
    };

    return (
        <>
            <div className="allproducts">
                <Container>
                    <div className="my-4 text-center">
                        <h3>Products</h3>
                    </div>
                    <div className='mb-5 ml-3'>
                        <Button className='adminadditembutton' variant="success" onClick={handleAddShow}>
                            Add Items
                        </Button>
                    </div>

                    <Row>
                        {products.map((product) => (
                            <Col key={product.itemId} xs={6} sm={6} md={4} lg={3} className="mb-5">
                                <Card className='card1 mr-4 mb-5'>
                                    <Card.Img className='custom-card-img'
                                        variant="top"
                                        src={product.imageUrl}
                                        alt={product.itemName}
                                    />
                                    <Card.Body>
                                        <Card.Title  className='text-center mb-3'>{product.itemName}</Card.Title>
                                        <Card.Text className='mb-1'>
                                            <strong>ItemId:</strong> {product.itemId}
                                        </Card.Text>
                                        <Card.Text className='mb-1'>
                                            <strong>Price: </strong> <span>&#8377; </span> {product.itemPrice}
                                        </Card.Text>
                                        <Card.Text className='mb-1'>
                                            <strong>Quantity:</strong> {product.itemQuantity}
                                        </Card.Text>
                                        <Card.Text className='mb-4'>
                                            <strong>Type:</strong> {product.itemType}
                                        </Card.Text>
                                        <Button variant="primary" onClick={() => handleUpdateShow(product.itemId)} className="mr-2">
                                            Update
                                        </Button>
                                        <Button variant="danger" onClick={() => deleteProduct(product.itemId)}>
                                            Delete
                                        </Button>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                </Container>
            </div>

            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Delete Item</Modal.Title>
                </Modal.Header>
                <Modal.Body>{message}</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>

            <UpdateItems
                show={showUpdateModal}
                handleClose={handleUpdateClose}
                productId={selectedProductId}
                onUpdate={() => {
                    allproductList()
                        .then((response) => {
                            setProducts(response.data);
                        })
                        .catch((error) => {
                            console.error(error);
                        });
                }}
            />

            <AddItems
                show={showAddModal}
                handleClose={handleAddClose}
                onAdd={() => {
                    allproductList()
                        .then((response) => {
                            setProducts(response.data);
                        })
                        .catch((error) => {
                            console.error(error);
                        });
                }}
            />
        </>
    );
}

export default AllProductsForAdmin;
