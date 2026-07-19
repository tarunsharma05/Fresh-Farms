import React, { useContext, useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { getItemById, allproductList } from '../../service/ItemServices';
import '../style/Item.css';
import { CartContext } from '../context/CartContext'
import { useUser } from "../context/UserContext.jsx";


function ProductDetails() {
    const { itemid } = useParams();
    const [item, setItem] = useState({});
    const { handleAddItemToCart } = useContext(CartContext);
    const { isAdmin } = useUser();

    const navigator = useNavigate();

    useEffect(() => {
        getItemById(itemid).then((response) => {
            setItem(response.data);
            console.log(response);
        }).catch(error => {
            console.error(error);
        });
    }, [itemid]);

    function handleAddToCart(e) {
        e.preventDefault();
        handleAddItemToCart(item)
    }

    return (
        <>
            <div className="allproducts">
                <Container>
                    <h3 className="my-4 text-center">Product Detail</h3>
                    <Row className="align-items-stretch">
                        <Col xs={6} sm={6} md={4} lg={3} className="mb-5">
                            <Card className="h-100">
                                <Card.Img className='custom-card-img'
                                    variant="top"
                                    src={item.imageUrl}
                                    alt={item.itemName}
                                />
                                <Card.Body>
                                    <Card.Title className='text-center mb-4'>{item.itemName}</Card.Title>
                                    <Card.Text className='mb-1'>
                                        <strong>Price: </strong><span>&#8377; </span> {item.itemPrice}
                                    </Card.Text>
                                    <Card.Text className='mb-1'>
                                        <strong>Quantity:</strong> {item.itemQuantity}
                                    </Card.Text>
                                    <Card.Text className='mb-1'>
                                        <strong>Type:</strong> {item.itemType}
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>

                        <Col xs={12} sm={6} md={8} lg={9} className="mb-5">

                            <Card>
                                <Card.Body className="d-flex flex-column">
                                    <Card.Text className='ml-2'>
                                        {item.description}
                                    </Card.Text>
                                    {!isAdmin &&
                                        <Button variant="primary" className="mt-3 align-self-end" onClick={handleAddToCart}>Add to Cart</Button>
                                    }
                                </Card.Body>
                            </Card>

                            <Col xs={6} sm={6} md={4} lg={3} className="mt-5 text-center d-flex  explorecol">
                                <Card className='card1 mb-5 explorecard' onClick={() => navigator('/items')}>
                                    <Card.Body>
                                        <Card.Title className="mt-2 text-center exploretitle">Explore More</Card.Title>
                                    </Card.Body>
                                </Card>
                            </Col>

                        </Col>


                    </Row>
                </Container>
            </div>


        </>
    );
}

export default ProductDetails;
