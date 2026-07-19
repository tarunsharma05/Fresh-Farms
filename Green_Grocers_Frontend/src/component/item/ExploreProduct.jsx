import React from 'react'
import { useEffect, useState } from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { allproductList } from '../../service/ItemServices';
import '../style/Item.css';

function ExploreProduct() {
    const [products, setProducts] = useState([]);
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

    return (
        <>
            <div className="allproducts">
                <Container>
                    <h3 className="my-4 text-center">Products</h3>
                    <Row>
                        {products.slice(0, 6).map((product) => (
                            <Col key={product.itemId} xs={6} sm={6} md={4} lg={3} className="mb-5">
                                <Card className='card1 mr-4' onClick={() => navigator(`/productdetail/${product.itemId}`)}>
                                    <Card.Img className='custom-card-img'
                                        variant="top"
                                        src={product.imageUrl}
                                        alt={product.itemName}
                                    />
                                    <Card.Body>
                                        <Card.Title className='text-center mb-3'>{product.itemName}</Card.Title>
                                        <Card.Text className='mb-1'>
                                            <strong>Price: </strong><span>&#8377; </span> {product.itemPrice}
                                        </Card.Text>
                                        <Card.Text className='mb-1'>
                                            <strong>Quantity:</strong> {product.itemQuantity}
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                        
                        <Col xs={6} sm={6} md={4} lg={3} className="mb-5 text-center d-flex flex-column justify-content-center explorecol">
                            <Card className='card1 explorecard' onClick={() => navigator('/items')}>
                                <Card.Body>
                                    <Card.Title className="mt-2 text-center exploretitle">Explore More</Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                </Container>
            </div>


        </>
    )
}

export default ExploreProduct