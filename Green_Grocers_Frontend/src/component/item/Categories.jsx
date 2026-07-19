import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Row, Col, Card } from 'react-bootstrap';
import '../style/Item.css';

function Categories() {
    const navigator = useNavigate();

    return (
        <>
            <div className='category justify-content-center'>
                <Container>
                    <h3 className='categoryh1 text-center'>Shop By Categories</h3>
                    <Row className='justify-content-center '>
                        <Col xs={6} sm={6} md={4} lg={3}>
                            <Card className='card1 mr-4' onClick={() => navigator('/items/fruit')}>
                                <Card.Img 
                                    className='custom-typecard-img'
                                    variant="top"
                                    src='src/assets/images/fruits2.jpg'
                                    alt='Fruits'
                                />

                                <Card.Body>
                                    <Card.Title className='categorymainh4 text-center'>Fruits</Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>

                        <Col xs={6} sm={6} md={4} lg={3}>
                            <Card className='card1 mr-4' onClick={() => navigator('/items/vegetable')}>
                                <Card.Img 
                                    className='custom-typecard-img'
                                    variant="top"
                                    src='src/assets/images/veges.jpg'
                                    alt='Vegetables'
                                />
                                <Card.Body>
                                    <Card.Title className='categorymainh4 text-center'>Vegetables</Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>

                        <Col xs={6} sm={6} md={4} lg={3}>
                            <Card className='card1 mr-4' onClick={() => navigator('/items/juice')}>
                                <Card.Img 
                                    className='custom-typecard-img'
                                    variant="top"
                                    src='src/assets/images/fruit juice.jpg'
                                    alt='Fruit Juices'
                                />
                                <Card.Body>
                                    <Card.Title className='categorymainh4 text-center'>Fruit Juices</Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                </Container>
            </div>
        </>
    );
}

export default Categories;
