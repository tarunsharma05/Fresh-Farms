import React from 'react'
import { Container, Row, Col } from 'react-bootstrap';
import '../style/Item.css';
function About() {
    return (
        <>
        <div className='about pt-3' id='aboutid'>
            <Container >
                <h2 className='categoryh1'>About Us</h2>
                    <Row className="justify-content-center">
                        <Col xs={12} md={10} lg={8}>
                            <p className="lead">
                                Founded in 2024, Green Grocer has come a long way from its beginnings.
                                <br /> <br />
                                Our mission is to provide the highest quality fresh produce to our customers while promoting sustainable farming practices and supporting local farmers.
                                <br /><br />
                                We believe in the power of fresh food to transform lives and communities. By offering a wide variety of fruits and vegetables, we aim to make healthy eating convenient and accessible for everyone.
                            </p>
                        </Col>
                    </Row>
                </Container>
                </div>
        </>
    )
}

export default About