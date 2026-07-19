import React, { useEffect, useState } from 'react';
import { productList } from '../../service/ItemServices';
import { useNavigate, useParams } from 'react-router-dom';
import { Container, Row, Col, Card } from 'react-bootstrap';
import '../style/Item.css';

function GetByType() {
    const [products, setProducts] = useState([]);
    const { type } = useParams();

    const navigator = useNavigate();
    useEffect(() => {
        productList(type)
            .then((response) => {
                setProducts(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    }, [type]);

    return (
        <div className="allproducts">
            <Container className='mb-5'>
                <h3 className="my-4 text-center">Products</h3>
                <Row>
                    {products.length === 0 ? (
                        <Col className="text-center">
                            <p className='categoryh1'>No products found</p>
                        </Col>
                    ) : (
                        products.map((product) => (
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
                        ))
                    )}
                </Row>
            </Container>
        </div>
    );
}

export default GetByType;
