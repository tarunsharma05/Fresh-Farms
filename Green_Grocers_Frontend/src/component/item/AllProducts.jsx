import React from 'react'
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Row, Col, Card, DropdownButton, Dropdown } from 'react-bootstrap';
import { allproductList, sortByName, sortByPrice } from '../../service/ItemServices';
import '../style/Item.css';

function AllProducts() {

    const [products, setProducts] = useState([]);
    const [order, setOrder] = useState('');
    const navigator = useNavigate();

    useEffect(() => {
        allproductList()
            .then((response) => {
                setProducts(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    function handleSort(order) {
        setOrder(order);
    }

    useEffect(() => {
        if (order) {
            if (order === 'price') {
                sortByPrice()
                    .then((response) => {
                        setProducts(response.data);
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            } else if (order === 'name') {
                sortByName()
                    .then((response) => {
                        setProducts(response.data);
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            }
        }
    }, [order]);



    return (

        <div className="allproducts">
            <Container>
                <h3 className="my-4 text-center">Products</h3>
                <div className="d-flex justify-content-end mb-4 mr-4">
                    <DropdownButton id="dropdown-basic-button" title="Sort By">
                        <Dropdown.Item onClick={() => handleSort('price')} >Sort By Price</Dropdown.Item>
                        <Dropdown.Item onClick={() => handleSort('name')}>Sort By Name</Dropdown.Item>
                    </DropdownButton>
                </div>
                <Row>
                    {products.length === 0 ? (
                        <Col className="text-center">
                            <p className='categoryh1'>No products found</p>
                        </Col>
                    ) : (

                        products.map((product) => (
                            <Col key={product.itemId} xs={6} sm={6} md={4} lg={3} >
                                <Card className='card1 mb-5 mr-4' onClick={() => navigator(`/productdetail/${product.itemId}`)}>
                                    <Card.Img className='custom-card-img'
                                        variant="top"
                                        src={product.imageUrl}
                                        alt={product.itemName}
                                    />
                                    <Card.Body>
                                        <Card.Title className='text-center mb-3'>{product.itemName}</Card.Title>
                                        <Card.Text className='mb-1'>
                                            <strong>Price: </strong> <span>&#8377; </span>{product.itemPrice}
                                        </Card.Text>
                                        <Card.Text className='mb-1'>
                                            <strong>Quantity:</strong> {product.itemQuantity}
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))
                    )}
                </Row>
            </Container>
        </div>
    );
}

export default AllProducts